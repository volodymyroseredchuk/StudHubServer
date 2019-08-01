package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Invitation;

public interface InvitationService {

    Invitation findById(Integer invitationId);

    Invitation save(Invitation invitation);

    void deleteById(Integer invitationId);
}
