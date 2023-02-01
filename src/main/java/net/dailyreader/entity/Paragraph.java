package net.dailyreader.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "paragraph", schema = "dailyreader")

public class Paragraph {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "para_id")
    private int paraId;

    private String paragraph;

    @Column(name = "paragraph_num")
    private String paragraphNum;

    @Column(name = "news_id")
    private int newsId;

    public int getParaId() {
        return paraId;
    }

    public void setParaId(int paraId) {
        this.paraId = paraId;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getParagraphNum() {
        return paragraphNum;
    }

    public void setParagraphNum(String paragraphNum) {
        this.paragraphNum = paragraphNum;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }


}
