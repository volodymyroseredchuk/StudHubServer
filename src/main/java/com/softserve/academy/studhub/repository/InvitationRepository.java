package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

    List<Invitation> findAllByUserUsernameAndTeamId(String username, Integer teamId);
}
