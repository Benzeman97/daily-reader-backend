package net.dailyreader.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "media", schema = "dailyreader")
public class Media {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "media_id")
    private int mediaId;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "media_num")
    private String mediaNum;

    @Column(name = "media_link")
    private String mediaLink;

    @Column(name = "post_owner")
    private String postOwner;

    @Column(name = "media_title")
    private String mediaTitle;

    @Column(name = "pic_link")
    private String picLink;

    @Column(name = "pic_desc")
    private String picDesc;

    @Column(name = "pub_link")
    private String pubLink;

    @Column(name = "pub_date")
    private String pubDate;

    @Column(name = "owner_link")
    private String ownerLink;

    private long videoId;

    @Column(name = "is_img_licensed")
    private String isImgLicensed;
    @Column(name = "is_img_vertical")
    private String isImgVertical;

    @Column(name = "img_height")
    private String imgHeight;

    @Column(name = "img_width")
    private String imgWidth;

    @Column(name = "news_id")
    private int newsId;

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaNum() {
        return mediaNum;
    }

    public void setMediaNum(String mediaNum) {
        this.mediaNum = mediaNum;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public String getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(String postOwner) {
        this.postOwner = postOwner;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public String getPicDesc() {
        return picDesc;
    }

    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc;
    }

    public String getPubLink() {
        return pubLink;
    }

    public void setPubLink(String pubLink) {
        this.pubLink = pubLink;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getOwnerLink() {
        return ownerLink;
    }

    public void setOwnerLink(String ownerLink) {
        this.ownerLink = ownerLink;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getIsImgLicensed() {
        return isImgLicensed;
    }

    public void setIsImgLicensed(String isImgLicensed) {
        this.isImgLicensed = isImgLicensed;
    }

    public String getIsImgVertical() {
        return isImgVertical;
    }

    public void setIsImgVertical(String isImgVertical) {
        this.isImgVertical = isImgVertical;
    }

    public String getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(String imgHeight) {
        this.imgHeight = imgHeight;
    }

    public String getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(String imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }


}
