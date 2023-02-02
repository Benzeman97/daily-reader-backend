package net.dailyreader.service;

import net.dailyreader.dto.response.*;

public interface NewsService {

    NewsListResponse getNewsList(int page);
    MainNewsResponse getMainNews();
    TrendingNewsResponse getTrendingNews();

    RelatedNewsResponse getRelatedNews(String type);

    NewsResponse getNews(int newsId);

    ParagraphResponse getParagraphs(int newsId);

    MediaResponse getMedias(int newsId);
}
