package net.dailyreader.dto.response;

import lombok.*;
import net.dailyreader.model.TrendingNews;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TrendingNewsResponse implements Serializable {

    private List<TrendingNews> trendingNews;
}
