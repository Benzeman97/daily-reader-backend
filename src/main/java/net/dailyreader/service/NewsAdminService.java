package net.dailyreader.service;

import net.dailyreader.dto.request.NewsAdminRequest;
import net.dailyreader.dto.response.MediaAdminResponse;
import net.dailyreader.dto.response.NewsAdminResponse;
import net.dailyreader.dto.response.NewsShortResponse;
import net.dailyreader.dto.response.ParagraphAdminResponse;
import net.dailyreader.entity.Media;
import net.dailyreader.entity.Paragraph;

public interface NewsAdminService {

    NewsAdminResponse saveNews(NewsAdminRequest request);
    NewsShortResponse findNews(String title);
    ParagraphAdminResponse findParagraph(int newsId,String paraNum);

    Paragraph saveParagraph(NewsAdminRequest request,int id);

    Media saveMedia(NewsAdminRequest request,int id);

    void deleteParagraph(int newsId,String paraNum);

    void deleteMedia(int newsId,String mediaType,String mediaNum);

    MediaAdminResponse findMedia(int newsId,String mediaType,String mediaNum);

    void deleteNewsById(int id);

}
