package net.dailyreader.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsAdminRequest implements Serializable {

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
    private String paragraph;
    private String paragraphNum;
    private String mediaType;
    private String mediaNum;
    private String mediaLink;
    private String postOwner;
    private String mediaTitle;
    private String picLink;
    private String picDesc;
    private String pubLink;
    private String pubDate;
    private String ownerLink;
    private long videoId;
    private String isImgLicensed;
    private String isImgVertical;
    private String imgHeight;
    private String imgWidth;
    private String isPublished;
}
