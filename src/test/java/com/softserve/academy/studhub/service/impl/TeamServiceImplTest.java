package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Team;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.TeamRepository;
import com.softserve.academy.studhub.service.TeamService;
import com.softserve.academy.studhub.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserService userService;

    private TeamService teamService;

    @Before
    public void init() {
        teamService = new TeamServiceImpl(teamRepository, userService);
    }

    @Test
    public void save() {

        Team team = new Team();
        team.setTitle("team");
        team.setUser(new User());
        team.setUserList(new ArrayList<>());

        Principal principal = () -> "Jarvizz";

        when(teamRepository.saveAndFlush(team)).thenReturn(team);

        Team resultTeam = teamService.save(team, principal);

        Assert.assertEquals(resultTeam.getTitle(), team.getTitle());
    }

    @Test
    public void update() {

        Team team = new Team();
        team.setTitle("team");
        team.setUser(new User());
        team.setUserList(new ArrayList<>());

        Team updatedTeam = new Team();
        team.setTitle("updatedTeam");
        team.setUser(new User());
        team.setUserList(new ArrayList<>());

        when(teamRepository.saveAndFlush(team)).thenReturn(updatedTeam);
        when(teamRepository.findById(any())).thenReturn(Optional.of(team));

        Team resultTeam = teamService.update(1, team);

        Assert.assertEquals(updatedTeam.getTitle(), resultTeam.getTitle());
    }

    @Test
    public void findById() {

        Integer teamId = 1;

        Team team = new Team();
        team.setId(teamId);

        when(teamRepository.findById(any())).thenReturn(Optional.of(team));

        Assert.assertEquals(team, teamService.findById(teamId));
    }

    @Test(expected = NotFoundException.class)
    public void findByInvalidId() {
        teamService.findById(null);
    }

    @Test
    public void isTeamPublicTrue() {

        Integer teamId = 1;

        Team team = new Team();
        team.setId(teamId);
        team.setIsPublic(true);

        when(teamRepository.findById(any())).thenReturn(Optional.of(team));

        Assert.assertTrue(teamService.isTeamPublic(teamId));
    }

    @Test
    public void isTeamPublicFalse() {

        Integer teamId = 1;

        Team team = new Team();
        team.setId(teamId);
        team.setIsPublic(false);

        when(teamRepository.findById(any())).thenReturn(Optional.of(team));

        Assert.assertFalse(teamService.isTeamPublic(teamId));
    }

    @Test
    public void hasAccessForUserAsCreator() {

        Integer teamId = 1;
        String username = "Jarvizz";

        User creator = new User();
        creator.setUsername(username);

        Team team = new Team();
        team.setUser(creator);

        when(teamRepository.findById(any())).thenReturn(Optional.of(team));

        Assert.assertTrue(teamService.hasAccessForUser(teamId, username));
    }

    @Test
    public void hasAccessForUserAsMember() {

        Integer teamId = 1;
        String username = "Jarvizz";

        User creator = new User();
        creator.setUsername("Creator");

        User member = new User();
        member.setUsername(username);

        Team team = new Team();
        team.setUser(creator);
        team.setUserList(new ArrayList<User>(){{
            add(member);
        }});

        when(teamRepository.findById(any())).thenReturn(Optional.of(team));

        Assert.assertTrue(teamService.hasAccessForUser(teamId, username));
    }

    @Test
    public void hasAccessForUserAsAnonym() {

        Integer teamId = 1;
        String username = "Jarvizz";

        User creator = new User();
        creator.setUsername("Creator");

        User member = new User();
        member.setUsername("Member");

        Team team = new Team();
        team.setUser(creator);
        team.setUserList(new ArrayList<User>(){{
            add(member);
        }});

        when(teamRepository.findById(any())).thenReturn(Optional.of(team));

        Assert.assertFalse(teamService.hasAccessForUser(teamId, username));
    }

}