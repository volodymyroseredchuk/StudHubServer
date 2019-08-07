package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Team;
import com.softserve.academy.studhub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface TeamService {

    Team save(Team team, Principal principal);

    Team update(Integer teamId, Team team);

    Team findById(Integer teamId);

    void delete(Integer teamId);

    Page<Team> findAll(Pageable pageable);

    boolean isTeamPublic(Integer teamId);

    boolean hasAccessForUser(Integer teamId, String username);

    List<Team> findAllByIsPublicTrueAndUserUsernameOrderByCreationDateDesc(String username);

    List<Team> findAllByIsPublicFalseAndUserUsernameOrderByCreationDateDesc(String username);
}
