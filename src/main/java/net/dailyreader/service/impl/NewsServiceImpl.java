package net.dailyreader.service.impl;

import net.dailyreader.dao.NewsDao;
import net.dailyreader.dto.response.MainNewsResponse;
import net.dailyreader.entity.News;
import net.dailyreader.dto.response.NewsListResponse;
import net.dailyreader.exception.DataNotFoundException;
import net.dailyreader.model.NewsBody;
import net.dailyreader.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    final private static Logger LOGGER = LogManager.getLogger(NewsServiceImpl.class);

    private NewsDao newsDao;

    public NewsServiceImpl(NewsDao newsDao){
        this.newsDao=newsDao;
    }

    @Override
    public NewsListResponse getNewsList(int page) {

        String type="nsfw";
        String is_affiliated="no";
        String is_main_news="no";

        int itemPerPage = 16;

        int start=page;

        if(start==1)
            start=0;
        else
            start= (page-1) * itemPerPage;

         List<News> newsList = newsDao.getNewsList(type,is_affiliated,is_main_news,start,itemPerPage)
                 .orElse(new ArrayList<>());

         List<NewsBody> newsBodyList = newsList.stream().map(n->new NewsBody(n.getNewsId(),n.getTitle(),n.getSubTitle(),
                 n.getPreviewImg(),getDate(n.getPostedDateTime()),n.getAuthor(),n.getNewsType())).collect(Collectors.toList());

         LOGGER.info(String.format("news list is returned with page %d",page));

         long numOfNews = newsDao.countNews();

        return new NewsListResponse(newsBodyList,numOfNews);
    }

    @Override
    public MainNewsResponse getMainNews() {
        String type="nsfw";
        String is_affiliated="no";
        String is_main_news="yes";

        News news = newsDao.getMainNews(type,is_affiliated,is_main_news)
                .orElse(null);

        if(Objects.isNull(news))
            throw new DataNotFoundException("no main news available");

        LOGGER.info(String.format("main news is returned with newsId %d",news.getNewsId()));

        return new MainNewsResponse(news.getNewsId(),news.getTitle(),news.getSubTitle(),news.getPreviewImg(),getDate(news.getPostedDateTime()),
                news.getAuthor(),news.getNewsType());
    }

    public String getDate(LocalDateTime dateTime){
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonth().getValue();
        int year = dateTime.getYear();

        return getDay(day).concat("-").concat(getMonth(month)).concat("-").concat(String.valueOf(year));
    }

    private String getDay(int day){
        String init = "0";

        if(day<10)
         return init.concat(String.valueOf(day));
        return String.valueOf(day);
    }

    private String getMonth(int month){
        switch (month){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }

        return "Jan";
    }
}
