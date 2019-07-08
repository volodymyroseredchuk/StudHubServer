package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.*;
import com.softserve.academy.studhub.entity.Proposal;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Team;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.service.IQuestionService;
import com.softserve.academy.studhub.service.TeamService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final IQuestionService questionService;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<TeamPaginatedDTO> getAllTeams(Pageable pageable) {

        Page<Team> teamPage = teamService.findAll(pageable);

        List<TeamForListDTO> teamForListDTOS = teamPage.getContent().stream()
            .map(team -> modelMapper.map(team, TeamForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new TeamPaginatedDTO(teamForListDTOS, teamPage.getTotalElements()));
    }

    @GetMapping("/{teamId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
            "(isAuthenticated() and " +
            "@teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<TeamDTO> getTeam(@PathVariable Integer teamId) {

        Team team = teamService.findById(teamId);

        return ResponseEntity.ok().body(modelMapper.map(team, TeamDTO.class));
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamDTO teamDTO, Principal principal) {

        teamDTO.setUser(modelMapper.map(userService.findByUsername(principal.getName()),
                UserForListDTO.class));
        Team team = teamService.save(modelMapper.map(teamDTO, Team.class), principal);

        return ResponseEntity.ok().body(modelMapper.map(team, TeamDTO.class));
    }

    @PutMapping("/{teamId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
            "(isAuthenticated() and @teamServiceImpl.findById(#teamId).getUser().username == principal.username)")
    public ResponseEntity<TeamDTO> editTeam(@PathVariable Integer teamId, @RequestBody TeamDTO teamDTO) {

        Team team = teamService.update(teamId, modelMapper.map(teamDTO, Team.class));

        return ResponseEntity.ok().body(modelMapper.map(team, TeamDTO.class));
    }

    @DeleteMapping("/{teamId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or " +
        "@teamServiceImpl.findById(#teamId).getUser().getUsername()== principal.username")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId) {

        teamService.delete(teamId);

        return ResponseEntity.ok().body(new MessageResponse("deleted!"));
    }

    @GetMapping("/{teamId}/questions")
    @PreAuthorize("permitAll()")
    public ResponseEntity<QuestionPaginatedDTO> getAllQuestionsByTeamId(@PathVariable Integer teamId, Pageable pageable) {

        Page<Question> questionPage = questionService.findAllByTeamId(teamId, pageable);

        List<QuestionForListDTO> questionDTOS = questionPage.getContent().stream()
                .map(question -> modelMapper.map(question, QuestionForListDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionDTOS, questionPage.getTotalElements()));
    }
}
