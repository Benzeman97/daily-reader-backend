package net.dailyreader.service;

import net.dailyreader.dto.response.*;
import net.dailyreader.model.NewsSearch;

import java.util.List;

public interface NewsService {

    NewsListResponse getNewsList(int page);
    NewsListResponse getNewsListByType(String type,int page);
    MainNewsResponse getMainNews();
    TrendingNewsResponse getTrendingNews();
    RelatedNewsResponse getRelatedNews(String type);
    NewsResponse getNews(int newsId);
    ParagraphResponse getParagraphs(int newsId);
    MediaResponse getMedias(int newsId);

    List<NewsSearch> getNewsBySearch(String name);
}
