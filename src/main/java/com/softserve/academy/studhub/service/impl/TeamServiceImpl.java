package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.dto.UserForListDTO;
import com.softserve.academy.studhub.entity.Team;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TeamRepository;
import com.softserve.academy.studhub.service.TeamService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;

    private final UserService userService;

    @Override
    public Team save(Team team, Principal principal) {

        team.setUser(userService.findByUsername(principal.getName()));
        team.setCreationDate(LocalDateTime.now());

        return teamRepository.saveAndFlush(team);
    }

    @Override
    public Team update(Integer teamId, Team team) {

        Team updatable = findById(teamId);

        updatable.setTitle(team.getTitle());
        updatable.setUser(team.getUser());
        updatable.setUserList(team.getUserList());
        updatable.setModifiedDate(LocalDateTime.now());

        return teamRepository.saveAndFlush(updatable);
    }

    @Override
    public Team findById(Integer teamId) {

        return teamRepository.findById(teamId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.TEAM_NOT_FOUND_BY_ID + teamId));
    }

    @Override
    public void delete(Integer teamId) {
        teamRepository.deleteById(teamId);
    }

    @Override
    public Page<Team> findAll(Pageable pageable) {

        return teamRepository.findAllByIsPublicTrueOrderByCreationDateDesc(pageable);
    }

    @Override
    public boolean isTeamPublic(Integer teamId) {

        Team team = findById(teamId);

        return team.getIsPublic();
    }

    @Override
    public boolean hasAccessForUser(Integer teamId, String username) {

        Team team = findById(teamId);

        if (team.getUser().getUsername().equals(username)) {
            return true;
        }

        for (User user : team.getUserList()) {

            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Team> findAllByIsPublicTrueAndUserUsernameOrderByCreationDateDesc(String username) {

        return getAllTeamsByAccessAndUsername(teamRepository.findAllByIsPublicTrueOrderByCreationDateDesc(), username);
    }

    @Override
    public List<Team> findAllByIsPublicFalseAndUserUsernameOrderByCreationDateDesc(String username) {

        return getAllTeamsByAccessAndUsername(teamRepository.findAllByIsPublicFalseOrderByCreationDateDesc(), username);
    }

    private List<Team> getAllTeamsByAccessAndUsername(List<Team> teamList, String username){
        List<Team> teamPublicListByUserUsername = new ArrayList<>();

        User user = userService.findByUsername(username);

        for (Team team: teamList) {
            if(team.getUserList().contains(user)){
                teamPublicListByUserUsername.add(team);
            }
        }

        return teamPublicListByUserUsername;
    }
}
