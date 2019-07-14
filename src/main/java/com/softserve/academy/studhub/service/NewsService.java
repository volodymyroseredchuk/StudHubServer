package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {

    News save(News  news);

    News update(Integer newsId, News news);

    News findById(Integer newsId);

    String deleteById(Integer newsId);

    Page<News> findAllSortedByAge(Pageable pageable);

    Boolean existByUrl(String url);

    void parseAndSave ();

}
