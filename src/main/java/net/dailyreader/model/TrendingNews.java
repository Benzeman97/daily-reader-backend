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
public class TrendingNews implements Serializable {

    private int newsId;
    private String title;
    private String previewImg;
}
