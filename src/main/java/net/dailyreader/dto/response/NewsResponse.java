package net.dailyreader.dto.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewsResponse implements Serializable {

    private int newsId;
    private String title;
    private String subTitle;
    private String previewImg;
    private String imgUrl;
    private String orgImg;
    private String imgOwn;
    private String author;
    private String newsType;
    private double views;
    private String isLicensed;
    private String isAffiliated;
    private String postedDate;
    private String modifiedDateTime;
    private String authorLink;
    private String orgArticle;
    private String orgArticleLink;

}
