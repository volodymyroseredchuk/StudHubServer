package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.InvitationRepository;
import com.softserve.academy.studhub.entity.Invitation;
import com.softserve.academy.studhub.service.InvitationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    @Override
    public Invitation findById(Integer invitationId) {
        return invitationRepository.findById(invitationId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.INVITATION_NOT_FOUNT_BY_ID + invitationId));
    }

    @Override
    public Invitation save(Invitation invitation) {
        return invitationRepository.saveAndFlush(invitation);
    }

    @Override
    public void deleteById(Integer invitationId) {

        findById(invitationId);
        invitationRepository.deleteById(invitationId);
    }

}
