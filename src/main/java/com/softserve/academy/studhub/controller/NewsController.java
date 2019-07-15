package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.NewsDTO;
import com.softserve.academy.studhub.dto.NewsForListDTO;
import com.softserve.academy.studhub.dto.NewsPaginatedDTO;
import com.softserve.academy.studhub.entity.News;
import com.softserve.academy.studhub.service.NewsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    private ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<NewsPaginatedDTO> getAllNews(Pageable pageable) {

        Page<News> newsPage = newsService.findAllSortedByAge(pageable);

        List<NewsForListDTO> newsForListDTOS = newsPage.getContent().stream()
                .map(news -> modelMapper.map(news, NewsForListDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new NewsPaginatedDTO(newsForListDTOS, newsPage.getTotalElements()));
    }


    @GetMapping("/{newsId}")
    @PreAuthorize("permitAll()")
    public News getNewsById(@PathVariable Integer newsId) {

        return newsService.findById(newsId);
    }

    // create, update, delete methods - in case admin page will be done.

    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<NewsDTO> createNews(@Valid @RequestBody NewsDTO newsDTO) {

        News result = newsService.save(modelMapper.map(newsDTO, News.class));
        return ResponseEntity.ok(modelMapper.map(result, NewsDTO.class));
    }

    @PutMapping("/{newsId}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<NewsDTO> editNews(@PathVariable Integer newsId, @RequestBody NewsDTO newsDto) {

        News result = newsService.update(newsId, modelMapper.map(newsDto, News.class));
        return ResponseEntity.ok(modelMapper.map(result, NewsDTO.class));
    }

    @DeleteMapping("/{newsId}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MODERATOR')")
    public ResponseEntity<String> deleteNews(@PathVariable Integer newsId) {

        return ResponseEntity.ok(newsService.deleteById(newsId));
    }

}
