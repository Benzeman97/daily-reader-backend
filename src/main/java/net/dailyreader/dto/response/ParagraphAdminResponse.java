package net.dailyreader.dto.response;


import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ParagraphAdminResponse implements Serializable {

    private int paraId;
    private String paragraph;
    private String paragraphNum;
}
