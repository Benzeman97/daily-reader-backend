package net.dailyreader.dto.response;

import lombok.*;
import net.dailyreader.model.NewsBody;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewsListResponse implements Serializable {

     private List<NewsBody> newsList;
     private long numOfItems;

}
