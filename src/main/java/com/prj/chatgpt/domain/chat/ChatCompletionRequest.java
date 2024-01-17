package com.prj.chatgpt.domain.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest implements Serializable {

    /** Default model definition */
    private String model = Model.GPT_3_5_TURBO.getCode();

    /** Question description */
    private List<Message> messages;

    /***
     * Control temperature [randomness]; between 0 and 2. Higher values (like 0.8) will make the output more random,
     * while lower values (like 0.2) will make the output more focused and make sure.
     */
    private double temperature = 0.2;

    /***
     * Diversity control; an alternative to using temperature sampling is called core sampling,
     * where the model considers the results of tokens with top_p probability mass.
     * Therefore, 0.1 means that only tokens containing the top 10% of probability mass are considered.
     */
    @JsonProperty("top_p")
    private Double topP = 1d;
    /** Number of completions generated for each prompt */
    private Integer n = 1;

    /** Whether it is streaming output: jumps words for result comes out. */
    private boolean stream = false;

    /** Stop output flag */
    private List<String> stop;

    /** Output string limit: 0 ~ 4096 */
    @JsonProperty("max_tokens")
    private Integer maxTokens = 2048;

    /** Frequency Penalty: Reduces the likelihood that the model will repeat the same sentence */
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty = 0;

    /** Existence Penalty: Enhance the likelihood of the model talking about new topics */
    @JsonProperty("presence_penalty")
    private double presencePenalty = 0;

    /***
     * Generate multiple call results and only display the best:
     * This will consume more of your api token.
     */
    @JsonProperty("logit_bias")
    private Map logitBias;

    /** Call flag: avoid repeated calls */
    private String user;

    @Getter
    @AllArgsConstructor
    public enum Model {
        /** gpt-3.5-turbo */
        GPT_3_5_TURBO("gpt-3.5-turbo"),
        /** GPT4.0 */
        GPT_4("gpt-4"),
        /** GPT4.0: Very long context*/
        GPT_4_32K("gpt-4-32k"),
        ;
        private String code;
    }
}
