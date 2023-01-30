package net.dailyreader.service;

import net.dailyreader.dto.request.NewsAdminRequest;
import net.dailyreader.dto.response.MediaAdminResponse;
import net.dailyreader.dto.response.NewsAdminResponse;
import net.dailyreader.dto.response.NewsShortResponse;
import net.dailyreader.dto.response.ParagraphAdminResponse;

public interface NewsAdminService {

    NewsAdminResponse saveNews(NewsAdminRequest request);

    NewsShortResponse findNews(String title);
    ParagraphAdminResponse findParagraph(int newsId,String paraNum);
    MediaAdminResponse findMedia(int newsId,String mediaType,String mediaNum);

}
