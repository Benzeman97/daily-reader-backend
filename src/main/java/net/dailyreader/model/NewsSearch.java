package net.dailyreader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsSearch {

    private int newsId;
    private String title;
    private String subTitle;
    private String previewImg;
    private String newsType;

}
