package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.News;
import com.softserve.academy.studhub.entity.Feed;
import com.softserve.academy.studhub.service.FeedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/news")
public class FeedController {

    private FeedService feedService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Feed>> getAllFeeds(Principal principal) {

        List<Feed> feedlist = feedService.findAllFeedsSortedByName();

        return ResponseEntity.ok(feedlist);
    }

    @GetMapping("/userfeeds")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Feed>> getUserFeeds(Principal principal) {

        List<Feed> feedlist = feedService.getUserFeeds(principal.getName());

        return ResponseEntity.ok(feedlist);
    }

    @GetMapping("/{feedId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<News>> getNewsByFeedId(@PathVariable Integer feedId) {

        List<News> result = feedService.findNewsByFeedId(feedId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{feedId}/follow")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> followChannel(@PathVariable Integer feedId, Principal principal) {
        String username = principal.getName();
        String result = feedService.followChannel(feedId, username);
        return ResponseEntity.ok(result);
    }
}
