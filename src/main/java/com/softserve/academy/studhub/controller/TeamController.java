package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.dto.*;
import com.softserve.academy.studhub.entity.Team;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.entity.Invitation;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.InvitationService;
import com.softserve.academy.studhub.service.TeamService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final InvitationService invitationService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<TeamPaginatedDTO> getAllTeams(Pageable pageable) {

        Page<Team> teamPage = teamService.findAll(pageable);

        List<TeamForListDTO> teamForListDTOS = teamPage.getContent().stream()
                .map(team -> modelMapper.map(team, TeamForListDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new TeamPaginatedDTO(teamForListDTOS, teamPage.getTotalElements()));
    }

    @GetMapping("/{teamId}")
    @PreAuthorize("@teamServiceImpl.isTeamPublic(#teamId) or " +
            "hasAuthority('READ_ANY_TEAM_PRIVILEGE') or " +
            "(isAuthenticated() and " +
            "@teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<TeamDTO> getTeam(@PathVariable Integer teamId) {

        Team team = teamService.findById(teamId);

        return ResponseEntity.ok(modelMapper.map(team, TeamDTO.class));
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamDTO teamDTO, Principal principal) {

        teamDTO.setUser(modelMapper.map(userService.findByUsername(principal.getName()),
                UserForListDTO.class));
        Team team = teamService.save(modelMapper.map(teamDTO, Team.class), principal);

        return ResponseEntity.ok(modelMapper.map(team, TeamDTO.class));
    }

    @PutMapping("/{teamId}")
    @PreAuthorize("hasAuthority('WRITE_ANY_TEAM_PRIVILEGE') or " +
            "(isAuthenticated() and @teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<TeamDTO> editTeam(@PathVariable Integer teamId, @RequestBody TeamDTO teamDTO) {

        Team team = teamService.update(teamId, modelMapper.map(teamDTO, Team.class));

        return ResponseEntity.ok(modelMapper.map(team, TeamDTO.class));
    }


    @DeleteMapping("/{teamId}")
    @PreAuthorize("hasAuthority('DELETE_ANY_TEAM_PRIVILEGE') or " +
            "@teamServiceImpl.findById(#teamId).getUser().getUsername() == principal.username")
    public ResponseEntity<MessageResponse> deleteTeam(@PathVariable Integer teamId) {

        teamService.delete(teamId);

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.TEAM_DELETED_SUCCESSFULLY));
    }

    @PutMapping("/{teamId}/invite/{userId}")
    @PreAuthorize("hasAuthority('WRITE_ANY_TEAM_PRIVILEGE') or " +
            "(isAuthenticated() and @teamServiceImpl.hasAccessForUser(#teamId, principal.username))")
    public ResponseEntity<TeamDTO> inviteMember(@PathVariable Integer teamId,
                                                @PathVariable Integer userId) {

        Team team = teamService.findById(teamId);
        User user = userService.findById(userId);

        Invitation invitation = Invitation.builder().team(team).user(user).build();
        invitationService.save(invitation);
        emailService.sendInvitation(invitation);

        return ResponseEntity.ok(modelMapper.map(team, TeamDTO.class));
    }

    @GetMapping("/{teamId}/invitations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getInvitationsOfUserByTeam(@PathVariable Integer teamId,
                                                        Principal principal) {

        List<Invitation> invitations = invitationService
                .findAllByUserUsernameAndTeamId(principal.getName(), teamId);

        return ResponseEntity.ok(invitations);
    }

    @PutMapping("/{teamId}/invitations/{invitationId}")
    @PreAuthorize("isAuthenticated() and (@invitationServiceImpl.findById(#invitationId)" +
                    ".getUser().getUsername() == principal.username)")
    public ResponseEntity<TeamDTO> acceptInvitation(@PathVariable Integer teamId,
                                                    @PathVariable Integer invitationId,
                                                    @RequestBody TeamDTO teamDTO) {

        Team team = teamService.update(teamId, modelMapper.map(teamDTO, Team.class));
        invitationService.deleteById(invitationId);

        return ResponseEntity.ok(modelMapper.map(team, TeamDTO.class));
    }

    @DeleteMapping("/{teamId}/invitations/{invitationId}")
    @PreAuthorize("hasAuthority('WRITE_ANY_TEAM_PRIVILEGE') or " +
            "(isAuthenticated() and " +
            "(@invitationServiceImpl.findById(#invitationId)" +
                    ".getUser().getUsername() == principal.username or " +
            "@teamServiceImpl.findById(#teamId).getUser().getUsername() == principal.username))")
    public ResponseEntity<MessageResponse> deleteInvitation(@PathVariable Integer teamId,
                                                            @PathVariable Integer invitationId) {

        invitationService.deleteById(invitationId);

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.INVITATION_DELETED_SUCCESSFULLY));
    }

    @GetMapping("/public/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<TeamForListDTO>> getAllPublicTeamsByUserUsername(@PathVariable String username) {

        return ResponseEntity.ok(teamService.findAllByIsPublicTrueAndUserUsernameOrderByCreationDateDesc(username).
                stream().map(team -> modelMapper.map(team, TeamForListDTO.class)).
                collect(Collectors.toList()));
    }

    @GetMapping("/private/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<TeamForListDTO>> getAllPrivateTeamsByUserUsername(@PathVariable String username) {

        return ResponseEntity.ok(teamService.findAllByIsPublicFalseAndUserUsernameOrderByCreationDateDesc(username).
                stream().map(team -> modelMapper.map(team, TeamForListDTO.class)).
                collect(Collectors.toList()));
    }
}
