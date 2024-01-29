package com.prj.chatgpt.domain.edits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditRequest implements Serializable {
    @Getter
    @AllArgsConstructor
    public enum Model{
        /*
            According to official website(https://platform.openai.com/docs/deprecations),
            this two edit model should be replace to gpt-4:
            SHUTDOWN DATE	MODEL / SYSTEM  	    RECOMMENDED REPLACEMENT
            2024-01-04	    text-davinci-edit-001	gpt-4
            2024-01-04	    code-davinci-edit-001	gpt-4
            2024-01-04	    /v1/edits	            /v1/chat/completions
         */
        /** text-davinci-edit-001 */
//        TEXT_DAVINCI_EDIT_001("text-davinci-edit-001"),
        TEXT_DAVINCI_EDIT_001("gpt-4"),
        /** code-davinci-edit-001 */
//        CODE_DAVINCI_EDIT_001("code-davinci-edit-001"),
        CODE_DAVINCI_EDIT_001("gpt-4"),
        ;
        private String code;
    }

    /** Model */
    @NonNull
    private String model = Model.CODE_DAVINCI_EDIT_001.getCode();

    /** Input */
    @NonNull
    private String input;

    /** Modify description */
    @NonNull
    private String instruction;

    /** Control temperature [randomness]: between 0 and 2.
     * Higher values (like 0.8) will make the output more random,
     * while lower values (like 0.2) will make the output more focused and deterministic
     */
    @Builder.Default
    private double temperature = 0.2;

    /** Diversity control: an alternative to using temperature sampling is called core sampling,
     * where the model considers the results of tokens with top_p probability mass.
     * Therefore, 0.1 means that only tokens containing the top 10% of probability mass are considered.
     */
    @JsonProperty("top_p")
    private Double topP = 1d;

    /** Number of completions generated for each prompt */
    private Integer n = 1;
}
