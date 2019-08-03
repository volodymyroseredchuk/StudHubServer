package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Invitation;

import java.util.List;

public interface InvitationService {

    List<Invitation> findAllByUserUsernameAndTeamId(String username, Integer teamId);

    Invitation findById(Integer invitationId);

    Invitation save(Invitation invitation);

    void deleteById(Integer invitationId);
}
