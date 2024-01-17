package com.prj.chatgpt.domain.qa;

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
public class QACompletionRequest implements Serializable {

    @Getter
    @AllArgsConstructor
    public enum Model {
        /**
         * The model `text-davinci-003` has been deprecated, learn more here:
         * https://platform.openai.com/docs/deprecations
         TEXT_DAVINCI_003("text-davinci-003"),
         TEXT_DAVINCI_002("text-davinci-002"),
         DAVINCI("davinci"),
        */
        TEXT_DAVINCI_003("gpt-3.5-turbo-instruct"),
        TEXT_DAVINCI_002("gpt-3.5-turbo-instruct"),
        DAVINCI("davinci"),
        ;
        private String code;
    }

    /** Default model definition */
    @NonNull
    @Builder.Default
    private String model = Model.TEXT_DAVINCI_003.getCode();
    /** Question description */
    @NonNull
    private String prompt;
    private String suffix;

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

    /**
     * Whether echo input: In some applications, echo is often used to indicate whether input
     * (for example, a user's query or command) is returned or "echoed" in the output.
     * If echo is set to true, the input may be included in the response or result;
     * if set to false, the input will not appear in the response.
     */
    @Builder.Default
    private boolean echo = false;

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
    @JsonProperty("best_of")
    @Builder.Default
    private Integer bestOf = 1;
    private Integer logprobs;
    @JsonProperty("logit_bias")
    private Map logitBias;

    /** Call flag: avoid repeated calls */
    private String user;

}
