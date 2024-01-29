package com.prj.chatgpt.domain.whisper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranscriptionsRequest {
    /** Model: WHISPER_1 */
    @Builder.Default
    private String model = WhisperEnum.Model.WHISPER_1.getCode();
    /** Prompt description */
    private String prompt;
    /** Output(response) format */
    @JsonProperty("response_format")
    @Builder.Default
    private String responseFormat = WhisperEnum.ResponseFormat.JSON.getCode();
    /** Control temperature [randomness]; between 0 and 2. Higher values (like 0.8) will make the output more random,
     * while lower values (like 0.2) will make the output more focused and deterministic */
    private double temperature = 0.2;
    /**
     * Audio language: ISO-639-1
     */
    private String language;
}
