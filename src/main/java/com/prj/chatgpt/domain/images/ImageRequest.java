package com.prj.chatgpt.domain.images;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @description Images request
 */
@Slf4j
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest extends ImageEnum implements Serializable {
    /** Question description */
    @NonNull
    private String prompt;
    /** Number of completions generated for each prompt */
    @Builder.Default
    private Integer n = 1;
    /** Size of image */
    @Builder.Default
    private String size = Size.size_1024.getCode();
    /** Picture formatting method: URL、B64_JSON */
    @JsonProperty("response_format")
    @Builder.Default
    private String responseFormat = ResponseFormat.URL.getCode();
    @Setter
    private String user;
}
