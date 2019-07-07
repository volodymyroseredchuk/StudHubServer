package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Team;
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

        return teamRepository.findAllByOrderByCreationDateDesc(pageable);
    }
}
