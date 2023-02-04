package net.dailyreader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsBody implements Serializable {

    private int newsId;
    private String title;
    private String subTitle;
    private String previewImg;
    private String date;
    private String author;
    private String newsType;

}
