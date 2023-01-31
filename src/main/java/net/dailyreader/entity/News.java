package net.dailyreader.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news", schema = "dailyreader")
@Getter
@Setter
public class News {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "news_id")
    private int newsId;

    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "preview_img")
    private String previewImg;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "org_img")
    private String orgImg;

    @Column(name = "img_own")
    private String imgOwn;

    private String author;

    @Column(name = "news_type")
    private String newsType;

    private double views;

    @Column(name = "is_licensed")
    private String isLicensed;

    @Column(name = "is_affiliated")
    private String isAffiliated;

    @Column(name = "is_published")
    private String isPublished;

    @Column(name = "posted_date_time")
    private LocalDateTime postedDateTime;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @OneToMany(targetEntity = Paragraph.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id",referencedColumnName = "news_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Paragraph> paragraphs;

    @OneToMany(targetEntity = Media.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id",referencedColumnName = "news_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Media> mediaList;


    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOrgImg() {
        return orgImg;
    }

    public void setOrgImg(String orgImg) {
        this.orgImg = orgImg;
    }

    public String getImgOwn() {
        return imgOwn;
    }

    public void setImgOwn(String imgOwn) {
        this.imgOwn = imgOwn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public Double getViews() {
        return views;
    }

    public void setViews(Double views) {
        this.views = views;
    }

    public String getIsLicensed() {
        return isLicensed;
    }

    public void setIsLicensed(String isLicensed) {
        this.isLicensed = isLicensed;
    }

    public String getIsAffiliated() {
        return isAffiliated;
    }

    public void setIsAffiliated(String isAffiliated) {
        this.isAffiliated = isAffiliated;
    }

    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

    public LocalDateTime getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(LocalDateTime postedDateTime) {
        this.postedDateTime = postedDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

}