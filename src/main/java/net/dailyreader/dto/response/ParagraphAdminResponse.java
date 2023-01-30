package net.dailyreader.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParagraphAdminResponse implements Serializable {

    private int paraId;
    private String paragraph;
    private String paragraphNum;
}
