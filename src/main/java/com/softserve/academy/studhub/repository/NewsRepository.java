package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {

    Page<News> findAllByOrderByCreationDateDesc(Pageable pageable);
}
