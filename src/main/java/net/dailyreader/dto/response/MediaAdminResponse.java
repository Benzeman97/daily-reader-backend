package net.dailyreader.dto.response;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MediaAdminResponse implements Serializable {

    private int mediaId;
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
}
