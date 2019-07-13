package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.entity.News;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.NewsRepository;
import com.softserve.academy.studhub.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private NewsRepository newsRepository;

    @Override
    public News save(News news) {

        News resultNews = newsRepository.saveAndFlush(news);
        return resultNews;
    }

    @Override
    public News update(Integer newsId, News news) {
        News updatable = findById(newsId);
        updatable.setTitle(news.getTitle());
        updatable.setBody(news.getBody());
        updatable.setSourceUrl(news.getSourceUrl());

        return newsRepository.saveAndFlush(updatable);
    }

    @Override
    public News findById(Integer newsId) {
        return newsRepository.findById(newsId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.QUESTION_NOTFOUND + newsId));
    }

    @Override
    public String deleteById(Integer newsId) {
        News newsToDelete = findById(newsId);
        newsRepository.deleteById(newsId);
        return SuccessMessage.QUESTION_DELETED_SUCCESSFULLY;
    }

    @Override
    public Page<News> findAllSortedByAge(Pageable pageable) {
        return newsRepository.findAllByOrderByCreationDateDesc(pageable);
    }
}
