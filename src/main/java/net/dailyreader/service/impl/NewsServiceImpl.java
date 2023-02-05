package net.dailyreader.service.impl;

import net.dailyreader.dao.MediaDao;
import net.dailyreader.dao.NewsDao;
import net.dailyreader.dao.ParagraphDao;
import net.dailyreader.dto.response.*;
import net.dailyreader.entity.Media;
import net.dailyreader.entity.News;
import net.dailyreader.entity.Paragraph;
import net.dailyreader.exception.DataNotFoundException;
import net.dailyreader.model.NewsBody;
import net.dailyreader.model.NewsSearch;
import net.dailyreader.model.TrendingNews;
import net.dailyreader.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    final private static Logger LOGGER = LogManager.getLogger(NewsServiceImpl.class);

    public static final String MAIN_NEWS_MODEL_KEY = "MAIN_NEWS_MODEL";
//    public static final String TRENDING_NEWS_MODEL_KEY = "TRENDING_NEWS_MODEL";
    private NewsDao newsDao;
    private ParagraphDao paragraphDao;
    private MediaDao mediaDao;

    public NewsServiceImpl(NewsDao newsDao,ParagraphDao paragraphDao,MediaDao mediaDao){
        this.newsDao=newsDao;
        this.paragraphDao=paragraphDao;
        this.mediaDao=mediaDao;
    }

    @Override
    @Cacheable(key="{#page,#root.methodName}",value = "READER")
    public NewsListResponse getNewsList(int page) {

        String is_main_news="no";
        String is_published="yes";

        int itemPerPage = 16;

        int start=page;

        if(start==1)
            start=0;
        else
            start= (page-1) * itemPerPage;

         List<News> newsList = newsDao.getNewsList(is_main_news,is_published,start,itemPerPage)
                 .orElse(new ArrayList<>());

         List<NewsBody> newsBodyList = newsList.stream().map(n->new NewsBody(n.getNewsId(),n.getTitle(),n.getSubTitle(),
                 n.getPreviewImg(),getDate(n.getPostedDateTime()),n.getAuthor(),n.getNewsType())).collect(Collectors.toList());

         LOGGER.info(String.format("news list is returned with page %d",page));

         long numOfNews = newsDao.countNews(is_published);

        return new NewsListResponse(newsBodyList,numOfNews);
    }

    @Override
    @Cacheable(key="{#type,#page,#root.methodName}",value = "READER")
    public NewsListResponse getNewsListByType(String type, int page) {

        String is_published="yes";

        int itemPerPage = 16;

        int start=page;

        if(start==1)
            start=0;
        else
            start= (page-1) * itemPerPage;

        List<News> newsList = newsDao.getNewsListByType(type,is_published,start,itemPerPage)
                .orElse(new ArrayList<>());

        List<NewsBody> newsBodyList = newsList.stream().map(n->new NewsBody(n.getNewsId(),n.getTitle(),n.getSubTitle(),
                n.getPreviewImg(),getDate(n.getPostedDateTime()),n.getAuthor(),n.getNewsType())).collect(Collectors.toList());

        LOGGER.info(String.format("news list is returned with type %s and page %d",type,page));

        long numOfNews = newsDao.countNewsByType(type,is_published);

        return new NewsListResponse(newsBodyList,numOfNews);

    }

    @Override
    @Cacheable(key="#root.target.MAIN_NEWS_MODEL_KEY",value = "READER")
    public MainNewsResponse getMainNews() {


        String is_affiliated="no";
        String is_main_news="yes";
        String is_published="yes";

        News news = newsDao.getMainNews(is_affiliated,is_main_news,is_published)
                .orElse(null);

        if(Objects.isNull(news))
            throw new DataNotFoundException("no main news available");

        LOGGER.info(String.format("main news is returned with newsId %d",news.getNewsId()));

        return new MainNewsResponse(news.getNewsId(),news.getTitle(),news.getSubTitle(),news.getPreviewImg(),getDate(news.getPostedDateTime()),
                news.getAuthor(),news.getNewsType());
    }

    @Override
    @Cacheable(key="{#id,#root.methodName}",value = "READER")
    public TrendingNewsResponse getTrendingNews(int id) {

        String is_affiliated="no";
        String is_published="yes";

        List<News> newsList = newsDao.getTrendingNewsList(is_affiliated,is_published)
                .orElse(new ArrayList<>());

        List<TrendingNews> trendingNewsList = newsList.stream().filter(n->isCheck(n.getNewsId(),id)).map(news->new TrendingNews(news.getNewsId(),news.getTitle(),news.getPreviewImg()))
                .collect(Collectors.toList());

        LOGGER.info(String.format("trending news list is returned"));

        return new TrendingNewsResponse(trendingNewsList);
    }

    @Override
    @Cacheable(key="{#id,#type,#root.methodName}",value = "READER")
    public RelatedNewsResponse getRelatedNews(int id,String type) {

        String is_published="yes";

        List<News> newsList = newsDao.getRelatedNews(type,is_published)
                .orElse(new ArrayList<>());

        List<NewsBody> newsBodyList = newsList.stream().filter(n->isCheck(n.getNewsId(),id)).map(n->new NewsBody(n.getNewsId(),n.getTitle(),n.getSubTitle(),
                n.getPreviewImg(),getDate(n.getPostedDateTime()),n.getAuthor(),n.getNewsType())).collect(Collectors.toList());

        LOGGER.info(String.format("news list is returned with type %s",type));

        return new RelatedNewsResponse(newsBodyList);
    }

    private boolean isCheck(int id,int newsId){
        return (id!=newsId) ? true : false;
    }

    @Override
    @Cacheable(key="{#newsId,#root.methodName}",value = "READER")
    public NewsResponse getNews(int newsId) {

        News news = newsDao.findNewsById(newsId)
                .orElseThrow(()->{
                    LOGGER.error(String.format("news is not found with id %d",newsId));
                    throw new DataNotFoundException(String.format("news is not found with id %d",newsId));
                });

        LOGGER.info(String.format("news is found with id %d",newsId));

        return new NewsResponse(news.getNewsId(),news.getTitle(),news.getSubTitle(),news.getPreviewImg(),news.getImgUrl(),
                news.getOrgImg(),news.getImgOwn(),news.getAuthor(),news.getNewsType(),news.getViews(),news.getIsLicensed(),news.getIsAffiliated(),
                getDate(news.getPostedDateTime()),getDateTime(news.getUpdatedDateTime()),news.getAuthorLink());
    }

    @Override
    @Cacheable(key="{#newsId,#root.methodName}",value = "READER")
    public ParagraphResponse getParagraphs(int newsId) {

              List<Paragraph> paragraphs = paragraphDao.findNewsById(newsId)
                       .orElse(new ArrayList<>());


        ParagraphResponse paragraphResponse = new ParagraphResponse();

        paragraphs.forEach(p->getParagraphResponse(p,paragraphResponse));

        LOGGER.info(String.format("paragraphs are returned with newsId %d",newsId));

        return paragraphResponse;
    }

    private ParagraphResponse getParagraphResponse(Paragraph paragraph,ParagraphResponse paragraphResponse){

        if(paragraph.getParagraphNum().equalsIgnoreCase("01")){
            paragraphResponse.setParagraph_01(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_01(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("02")){
            paragraphResponse.setParagraph_02(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_02(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("03")){
            paragraphResponse.setParagraph_03(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_03(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("04")){
            paragraphResponse.setParagraph_04(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_04(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("05")){
            paragraphResponse.setParagraph_05(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_05(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("06")){
            paragraphResponse.setParagraph_06(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_06(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("07")){
            paragraphResponse.setParagraph_07(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_07(paragraph.getParagraph());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("08")){
            paragraphResponse.setParagraph_08(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_08(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("09")){
            paragraphResponse.setParagraph_09(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_09(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("10")){
            paragraphResponse.setParagraph_10(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_10(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("11")){
            paragraphResponse.setParagraph_11(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_11(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("12")){
            paragraphResponse.setParagraph_12(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_12(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("13")){
            paragraphResponse.setParagraph_13(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_13(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("14")){
            paragraphResponse.setParagraph_14(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_14(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("15")){
            paragraphResponse.setParagraph_15(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_15(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("16")){
            paragraphResponse.setParagraph_16(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_16(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("17")){
            paragraphResponse.setParagraph_17(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_17(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("18")){
            paragraphResponse.setParagraph_18(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_18(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("19")){
            paragraphResponse.setParagraph_19(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_19(paragraph.getParagraphNum());
        }

        if(paragraph.getParagraphNum().equalsIgnoreCase("20")){
            paragraphResponse.setParagraph_20(paragraph.getParagraph());
            paragraphResponse.setParagraphNum_20(paragraph.getParagraphNum());
        }
        return paragraphResponse;
    }

    @Override
    @Cacheable(key="{#newsId,#root.methodName}",value = "READER")
    public MediaResponse getMedias(int newsId) {

          List<Media> mediaList =  mediaDao.findNewsById(newsId)
                   .orElse(new ArrayList<>());

          MediaResponse mediaResponse = new MediaResponse();

           mediaList.forEach(media->setMediaResponse(media,mediaResponse));

        LOGGER.info(String.format("media list is returned with newsId %d",newsId));

        return mediaResponse;
    }

    @Override
    @Cacheable(key="{#name,#root.methodName}",value = "READER")
    public List<NewsSearch> getNewsBySearch(String name) {

        String is_published="yes";

        List<News> newsList = newsDao.getNewsBySearch(name,is_published)
                .orElse(new ArrayList<>());

        LOGGER.info(String.format("news list is returned by search with "+name));

        return newsList.stream().map(n->new NewsSearch(n.getNewsId(),n.getTitle(),n.getSubTitle(),n.getPreviewImg(),n.getNewsType()))
                .collect(Collectors.toList());
    }

    private MediaResponse setMediaResponse(Media media,MediaResponse mediaResponse){

        if(media.getMediaNum().equalsIgnoreCase("01")){
            mediaResponse.setMediaType_01(media.getMediaType());
            mediaResponse.setMediaNum_01(media.getMediaNum());
            mediaResponse.setMediaLink_01(media.getMediaLink());
            mediaResponse.setPostOwner_01(media.getPostOwner());
            mediaResponse.setMediaTitle_01(media.getMediaTitle());
            mediaResponse.setPicLink_01(media.getPicLink());
            mediaResponse.setPicDesc_01(media.getPicDesc());
            mediaResponse.setPubLink_01(media.getPubLink());
            mediaResponse.setPubDate_01(media.getPubDate());
            mediaResponse.setOwnerLink_01(media.getOwnerLink());
            mediaResponse.setVideoId_01(media.getVideoId());
            mediaResponse.setIsImgLicensed_01(media.getIsImgLicensed());
            mediaResponse.setIsImgVertical_01(media.getIsImgVertical());
            mediaResponse.setImgHeight_01(media.getImgHeight());
            mediaResponse.setImgWidth_01(media.getImgWidth());
        }

        if(media.getMediaNum().equalsIgnoreCase("02")){
            mediaResponse.setMediaType_02(media.getMediaType());
            mediaResponse.setMediaNum_02(media.getMediaNum());
            mediaResponse.setMediaLink_02(media.getMediaLink());
            mediaResponse.setPostOwner_02(media.getPostOwner());
            mediaResponse.setMediaTitle_02(media.getMediaTitle());
            mediaResponse.setPicLink_02(media.getPicLink());
            mediaResponse.setPicDesc_02(media.getPicDesc());
            mediaResponse.setPubLink_02(media.getPubLink());
            mediaResponse.setPubDate_02(media.getPubDate());
            mediaResponse.setOwnerLink_02(media.getOwnerLink());
            mediaResponse.setVideoId_02(media.getVideoId());
            mediaResponse.setIsImgLicensed_02(media.getIsImgLicensed());
            mediaResponse.setIsImgVertical_02(media.getIsImgVertical());
            mediaResponse.setImgHeight_02(media.getImgHeight());
            mediaResponse.setImgWidth_02(media.getImgWidth());
        }

        if(media.getMediaNum().equalsIgnoreCase("03")){
            mediaResponse.setMediaType_03(media.getMediaType());
            mediaResponse.setMediaNum_03(media.getMediaNum());
            mediaResponse.setMediaLink_03(media.getMediaLink());
            mediaResponse.setPostOwner_03(media.getPostOwner());
            mediaResponse.setMediaTitle_03(media.getMediaTitle());
            mediaResponse.setPicLink_03(media.getPicLink());
            mediaResponse.setPicDesc_03(media.getPicDesc());
            mediaResponse.setPubLink_03(media.getPubLink());
            mediaResponse.setPubDate_03(media.getPubDate());
            mediaResponse.setOwnerLink_03(media.getOwnerLink());
            mediaResponse.setVideoId_03(media.getVideoId());
            mediaResponse.setIsImgLicensed_03(media.getIsImgLicensed());
            mediaResponse.setIsImgVertical_03(media.getIsImgVertical());
            mediaResponse.setImgHeight_03(media.getImgHeight());
            mediaResponse.setImgWidth_03(media.getImgWidth());
        }

        if(media.getMediaNum().equalsIgnoreCase("04")){
            mediaResponse.setMediaType_04(media.getMediaType());
            mediaResponse.setMediaNum_04(media.getMediaNum());
            mediaResponse.setMediaLink_04(media.getMediaLink());
            mediaResponse.setPostOwner_04(media.getPostOwner());
            mediaResponse.setMediaTitle_04(media.getMediaTitle());
            mediaResponse.setPicLink_04(media.getPicLink());
            mediaResponse.setPicDesc_04(media.getPicDesc());
            mediaResponse.setPubLink_04(media.getPubLink());
            mediaResponse.setPubDate_04(media.getPubDate());
            mediaResponse.setOwnerLink_04(media.getOwnerLink());
            mediaResponse.setVideoId_04(media.getVideoId());
            mediaResponse.setIsImgLicensed_04(media.getIsImgLicensed());
            mediaResponse.setIsImgVertical_04(media.getIsImgVertical());
            mediaResponse.setImgHeight_04(media.getImgHeight());
            mediaResponse.setImgWidth_04(media.getImgWidth());
        }

        if(media.getMediaNum().equalsIgnoreCase("05")){
            mediaResponse.setMediaType_05(media.getMediaType());
            mediaResponse.setMediaNum_05(media.getMediaNum());
            mediaResponse.setMediaLink_05(media.getMediaLink());
            mediaResponse.setPostOwner_05(media.getPostOwner());
            mediaResponse.setMediaTitle_05(media.getMediaTitle());
            mediaResponse.setPicLink_05(media.getPicLink());
            mediaResponse.setPicDesc_05(media.getPicDesc());
            mediaResponse.setPubLink_05(media.getPubLink());
            mediaResponse.setPubDate_05(media.getPubDate());
            mediaResponse.setOwnerLink_05(media.getOwnerLink());
            mediaResponse.setVideoId_05(media.getVideoId());
            mediaResponse.setIsImgLicensed_05(media.getIsImgLicensed());
            mediaResponse.setIsImgVertical_05(media.getIsImgVertical());
            mediaResponse.setImgHeight_05(media.getImgHeight());
            mediaResponse.setImgWidth_05(media.getImgWidth());
        }

        if(media.getMediaNum().equalsIgnoreCase("06")){
            mediaResponse.setMediaType_06(media.getMediaType());
            mediaResponse.setMediaNum_06(media.getMediaNum());
            mediaResponse.setMediaLink_06(media.getMediaLink());
            mediaResponse.setPostOwner_06(media.getPostOwner());
            mediaResponse.setMediaTitle_06(media.getMediaTitle());
            mediaResponse.setPicLink_06(media.getPicLink());
            mediaResponse.setPicDesc_06(media.getPicDesc());
            mediaResponse.setPubLink_06(media.getPubLink());
            mediaResponse.setPubDate_06(media.getPubDate());
            mediaResponse.setOwnerLink_06(media.getOwnerLink());
            mediaResponse.setVideoId_06(media.getVideoId());
            mediaResponse.setIsImgLicensed_06(media.getIsImgLicensed());
            mediaResponse.setIsImgVertical_06(media.getIsImgVertical());
            mediaResponse.setImgHeight_06(media.getImgHeight());
            mediaResponse.setImgWidth_06(media.getImgWidth());
        }

        return mediaResponse;
    }

    public String getDate(LocalDateTime dateTime){
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonth().getValue();
        int year = dateTime.getYear();

        return getDay(day).concat("-").concat(getMonth(month)).concat("-").concat(String.valueOf(year));
    }

    public String getDateTime(LocalDateTime dateTime){
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonth().getValue();
        int year = dateTime.getYear();
        int hour = dateTime.getHour();
        int min = dateTime.getMinute();

        return getDay(day).concat("-").concat(getMonth(month)).concat("-").concat(String.valueOf(year))
                .concat("-").concat(getHour(hour)).concat("-").concat(getMinute(min));
    }

    private String getHour(int hour){
        String init="0";

        if(hour<10)
            return init.concat(String.valueOf(hour));
        return String.valueOf(hour);
    }

    private String getMinute(int min){
        String init="0";

        if(min<10)
            return init.concat(String.valueOf(min));
        return String.valueOf(min);
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
