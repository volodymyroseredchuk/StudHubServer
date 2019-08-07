package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.News;
import com.softserve.academy.studhub.entity.Feed;

import java.util.List;

public interface FeedService {

    Feed save(Feed feed);

    Feed findById(Integer feedId);

    String deleteById(Integer feedId);

    List<Feed> findAllFeedsSortedByName();

    List<News> findNewsByFeedId(Integer feedId);

    Boolean existByLink(String url);

    List<Feed> getUserFeeds(String userName);

    String followChannel(Integer feedId, String username);

}
