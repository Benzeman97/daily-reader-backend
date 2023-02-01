package net.dailyreader.dto.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewsAdminResponse implements Serializable {

    private int newsId;
    private String title;
    private String isPublished;
    private boolean status;
}
