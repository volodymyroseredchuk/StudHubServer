package com.softserve.academy.studhub.service.impl;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;

@AllArgsConstructor
@Service
public class ContentParser {

    private HashSet<String> links;

    private Document connect(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public String parseTitle(String url) {
        Document document = connect(url);
        String title = document.title();
        return title;
    }

    public String parseBody(String url) {
        Document document = connect(url);
        Elements content = document.getElementsByClass("post-content");
        String body = content.html();
        //System.out.println(body);
        return body;
    }

    public HashSet<String> parseLinks(String url) {

        Document document = connect(url);
        Elements otherLinks = document.select("a[href^=\"https://ain.ua/2019/07/14/\"]");

        for (Element page : otherLinks) {
            String absLink = page.attr("abs:href");
            if (!links.contains(absLink)) {
                links.add(absLink);
            }
        }
        return links;
    }

}
