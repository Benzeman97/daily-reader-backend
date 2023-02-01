package net.dailyreader.service;

import net.dailyreader.dto.response.MainNewsResponse;
import net.dailyreader.dto.response.NewsListResponse;

public interface NewsService {

    NewsListResponse getNewsList(int page);
    MainNewsResponse getMainNews();
}
