package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Integer> {

    Boolean existsByLink(String url);

    List<Feed> findAllByOrderByNameAsc();
}
