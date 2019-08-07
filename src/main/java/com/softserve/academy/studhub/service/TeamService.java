package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface TeamService {

    Team save(Team team, Principal principal);

    Team update(Integer teamId, Team team);

    Team findById(Integer teamId);

    void delete(Integer teamId);

    Team deleteMemberFromTeam(Integer teamId, Integer userId);

    Team joinTeam(Integer teamId, Integer userId);

    Page<Team> findAll(Pageable pageable);

    boolean isTeamPublic(Integer teamId);

    boolean hasAccessForUser(Integer teamId, String username);
}
