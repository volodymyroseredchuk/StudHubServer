package com.softserve.academy.studhub.service.impl;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.dto.News;
import com.softserve.academy.studhub.entity.Feed;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.FeedRepository;
import com.softserve.academy.studhub.service.FeedService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FeedServiceImpl implements FeedService {

    private FeedRepository feedRepository;

    private UserService userService;

    @Override
    public Feed save(Feed feed) {

        Feed resultFeed = feedRepository.saveAndFlush(feed);
        return resultFeed;
    }

    @Override
    public List<Feed> findAllFeedsSortedByName() {

        return feedRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Feed findById(Integer feedId) {
        return feedRepository.findById(feedId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.FEED_NOTFOUND + feedId));
    }

    public List<News> findNewsByFeedId(Integer feedId) {
        Feed feed = findById(feedId);
        List<News> newsList = readRss(feed.getLink());
        return newsList;
    }

    @Override
    public String deleteById(Integer feedId) {
        findById(feedId);
        feedRepository.deleteById(feedId);
        return SuccessMessage.FEED_DELETED_SUCCESSFULLY;
    }


    @Override
    public Boolean existByLink(String url) {
        return feedRepository.existsByLink(url);
    }

    @Override
    public List<Feed> getUserFeeds(String userName) {

        User user = userService.findByUsername(userName);
        List<Feed> feedsList = user.getFeeds();

        return feedsList;
    }


    private List<News> readRss(String url) {
        URL feedSource = null;
        List<News> newsList = new ArrayList<>();
        try {
            feedSource = new URL(url);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));
            for (SyndEntry entry : feed.getEntries()) {
                News news = new News();
                news.setTitle(entry.getTitle());
                news.setBody(entry.getDescription().getValue());
                news.setSourceUrl(entry.getLink());
                news.setCreationDate(entry.getPublishedDate());
                newsList.add(news);
            }
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    public String followChannel(Integer feedId, String username) {
        String response = null;
        User user = userService.findByUsername(username);
        Feed feed = findById(feedId);
        List<Feed> feedList = user.getFeeds();
        if (feedList.contains(feed)) {
            feedList.remove(feed);
            response = SuccessMessage.FEED_DELETED_SUCCESSFULLY;
        } else {
            feedList.add(feed);
            response = SuccessMessage.FEED_ADDED_SUCCESSFULLY;
        }
        user.setFeeds(feedList);
        userService.update(user);
        return response;
    }

}
