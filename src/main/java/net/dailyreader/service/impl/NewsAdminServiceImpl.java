package net.dailyreader.service.impl;

import net.dailyreader.dao.MediaDao;
import net.dailyreader.dao.NewsDao;
import net.dailyreader.dao.ParagraphDao;
import net.dailyreader.dto.request.NewsAdminRequest;
import net.dailyreader.dto.response.MediaAdminResponse;
import net.dailyreader.dto.response.NewsAdminResponse;
import net.dailyreader.dto.response.NewsShortResponse;
import net.dailyreader.dto.response.ParagraphAdminResponse;
import net.dailyreader.entity.Media;
import net.dailyreader.entity.News;
import net.dailyreader.entity.Paragraph;
import net.dailyreader.exception.DataNotFoundException;
import net.dailyreader.service.NewsAdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class NewsAdminServiceImpl implements NewsAdminService {

    final private static Logger LOGGER = LogManager.getLogger(NewsAdminServiceImpl.class);

    private NewsDao newsDao;
    private ParagraphDao paragraphDao;

    private MediaDao mediaDao;

    public NewsAdminServiceImpl(NewsDao newsDao,ParagraphDao paragraphDao,MediaDao mediaDao){
        this.newsDao=newsDao;
        this.paragraphDao=paragraphDao;
        this.mediaDao=mediaDao;
    }


    @Override
    public NewsAdminResponse saveNews(NewsAdminRequest request) {

        News news = newsDao.findNewsByTitle(request.getTitle().toLowerCase())
                .orElse(new News());

        news = newsDao.save(setNews(news,request));
        LOGGER.info(String.format("news has been saved"));

       return new NewsAdminResponse(news.getNewsId(),news.getTitle(),news.getIsPublished(),true);
    }

    @Override
    public NewsShortResponse findNews(String title) {
        News news = newsDao.findNewsByTitle(title.toLowerCase())
                .orElse(null);

        if(Objects.isNull(news)){
         int id = newsDao.findLastUpdatedNewsId();
         LOGGER.info(String.format("new newsId is returned with %d",id));
            return new NewsShortResponse(id,title,"","","",
                    "","","","",0.0,"","",
                    "");
        }
        LOGGER.info(String.format("news is found with title %s",title));
        return new NewsShortResponse(news.getNewsId(),news.getTitle(), news.getSubTitle(),news.getPreviewImg(),news.getImgUrl(),
                news.getOrgImg(),news.getImgOwn(),news.getAuthor(),news.getNewsType(),news.getViews(),news.getIsLicensed(),news.getIsAffiliated(),
                news.getIsPublished());

    }

    @Override
    public ParagraphAdminResponse findParagraph(int newsId, String paraNum) {
        Paragraph paragraph = paragraphDao.findParagraph(newsId,paraNum)
                .orElseThrow(()->{
                    LOGGER.error(String.format("paragraph is not found with newsId %d and paraNum %s",newsId,paraNum));
                    throw new DataNotFoundException(String.format("paragraph is not found with newsId %d and paraNum %s",newsId,paraNum));
                });

        LOGGER.info(String.format("paragraph is  found with newsId %d and mediaNum %s",newsId,paraNum));

        return new ParagraphAdminResponse(paragraph.getParaId(),paragraph.getParagraph(),paragraph.getParagraphNum());
    }

    @Override
    public MediaAdminResponse findMedia(int newsId, String mediaType, String mediaNum) {
        Media media = mediaDao.findMedia(newsId,mediaType.toLowerCase(),mediaNum)
                .orElseThrow(()->{
                    LOGGER.error(String.format("media is not found with newsId %d and mediaNum %s",newsId,mediaNum));
                    throw new DataNotFoundException(String.format("media is not found with newsId %d and mediaNum %s",newsId,mediaNum));
                });

        LOGGER.info(String.format("media is  found with newsId  %d and mediaNum %s",newsId,mediaNum));

        return new MediaAdminResponse(media.getMediaId(),media.getMediaType(),media.getMediaNum(),media.getMediaLink(),
                media.getPostOwner(),media.getMediaTitle(),media.getPicLink(),media.getPicDesc(),media.getPubLink(),media.getPubDate(),
                media.getOwnerLink(),media.getVideoId(),media.getIsImgLicensed(),media.getIsImgVertical(),media.getImgHeight(),media.getImgWidth());
    }

    @Override
    public void deleteNewsById(int id) {
         News news = newsDao.findById(id)
                 .orElseThrow(()->{
                    LOGGER.error(String.format("news is not found with newsId %d",id));
                    throw new DataNotFoundException(String.format("news is not found with newsId %d",id));
                 });
         newsDao.delete(news);;
         LOGGER.info(String.format("news is deleted with newsid %d",id));
    }

    private News setNews(News news,NewsAdminRequest request){

        if(news.getSubTitle().trim().isEmpty())
            news.setPostedDateTime(LocalDateTime.now());

        news.setTitle(request.getTitle());
        news.setSubTitle(request.getSubTitle());
        news.setPreviewImg(request.getPreviewImg());
        news.setImgUrl(request.getImgUrl());
        news.setOrgImg(request.getOrgImg());
        news.setImgOwn(request.getImgOwn());
        news.setAuthor(request.getAuthor());
        news.setNewsType(request.getNewsType());
        news.setViews(request.getViews());
        news.setIsLicensed(request.getIsLicensed());
        news.setIsAffiliated(request.getIsAffiliated());
        news.setIsPublished(request.getIsPublished());
        news.setUpdatedDateTime(LocalDateTime.now());

        Paragraph paragraph=paragraphDao.findParagraph(request.getNewsId(),request.getParagraphNum()).orElse(new Paragraph());
        paragraph.setParagraph(request.getParagraph());
        paragraph.setParagraphNum(request.getParagraphNum());

        List<Paragraph> paragraphs = new ArrayList<>(Arrays.asList(paragraph));

        news.setParagraphs(paragraphs);

        Media media = mediaDao.findMedia(request.getNewsId(),request.getMediaType().toLowerCase(),request.getMediaNum()).orElse(new Media());
        media.setMediaType(request.getMediaType().toLowerCase());
        media.setMediaNum(request.getMediaNum());
        media.setMediaLink(request.getMediaLink());
        media.setPostOwner(request.getPostOwner());
        media.setMediaTitle(request.getMediaTitle());
        media.setPicLink(request.getPicLink());
        media.setPicDesc(request.getPicDesc());
        media.setPubLink(request.getPubLink());
        media.setPubDate(request.getPubDate());
        media.setOwnerLink(request.getOwnerLink());
        media.setVideoId(request.getVideoId());
        media.setIsImgLicensed(request.getIsImgLicensed());
        media.setIsImgVertical(request.getIsImgVertical());
        media.setImgHeight(request.getImgHeight());
        media.setImgWidth(request.getImgWidth());

        List<Media> mediaList = new ArrayList<>(Arrays.asList(media));

        news.setMediaList(mediaList);

        return news;
    }
}
