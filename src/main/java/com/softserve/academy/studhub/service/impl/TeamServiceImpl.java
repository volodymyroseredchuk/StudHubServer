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

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;

    @Override
    public Team save(Team team, Principal principal) {

        User creator = userService.findByUsername(principal.getName());
        team.setUser(creator);
        team.getUserList().add(creator);
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
    public Team deleteMemberFromTeam(Integer teamId, Integer userId) {

        Team team = findById(teamId);
        User user = userService.findById(userId);

        team.getUserList().remove(user);

        return update(teamId, team);
    }

    @Override
    public Team joinTeam(Integer teamId, Integer userId) {

        Team team = findById(teamId);
        User user = userService.findById(userId);

        team.getUserList().add(user);

        return update(teamId, team);
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
}
