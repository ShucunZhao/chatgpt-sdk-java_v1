package com.prj.chatgpt.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prj.chatgpt.domain.billing.BillingUsage;
import com.prj.chatgpt.domain.billing.Subscription;
import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.edits.EditRequest;
import com.prj.chatgpt.domain.edits.EditResponse;
import com.prj.chatgpt.domain.embedd.EmbeddingRequest;
import com.prj.chatgpt.domain.embedd.EmbeddingResponse;
import com.prj.chatgpt.domain.files.DeleteFileResponse;
import com.prj.chatgpt.domain.files.UploadFileResponse;
import com.prj.chatgpt.domain.images.ImageEditRequest;
import com.prj.chatgpt.domain.images.ImageRequest;
import com.prj.chatgpt.domain.images.ImageResponse;
import com.prj.chatgpt.domain.other.OpenAiResponse;
import com.prj.chatgpt.domain.qa.QACompletionRequest;
import com.prj.chatgpt.domain.qa.QACompletionResponse;
import com.prj.chatgpt.domain.whisper.TranscriptionsRequest;
import com.prj.chatgpt.domain.whisper.TranslationsRequest;
import com.prj.chatgpt.domain.whisper.WhisperResponse;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * @description: OpenAi Session interface
 */
public interface OpenAiSession {
    /**
     * Text Q&A: simple request, generally for testing.
     * @param question request information
     * @return return result
     */
    QACompletionResponse completions(String question);

    /**
     * Default text Q&A
     * @param qaCompletionRequest request information
     * @return                    response result
     */
    QACompletionResponse completions(QACompletionRequest qaCompletionRequest);

    /**
     * Text Q&A & streaming response request
     * @param qaCompletionRequest request information
     * @param eventSourceListener implements monitoring: receives data through the onEvent method of monitoring
     */
    EventSource completions(QACompletionRequest qaCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;

    /**
     * Default GPT-3.5/4.0 chat model
     * @param chatCompletionRequest request information
     * @return return result
     */
    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);

    /**
     * GPT-3.5/4.0 chat model & streaming response request
     * @param chatCompletionRequest request information
     * @param eventSourceListener implements monitoring: receives data through the onEvent method of monitoring
     * @return return result
     */
    EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;

    /**
     * Text edit
     *
     * @param editRequest request information
     * @return Response result
     */
    EditResponse edit(EditRequest editRequest);

    /**
     * Image generation
     *
     * @param prompt image description
     * @return response result
     */
    ImageResponse genImages(String prompt);

    /**
     *
     *
     * @param imageRequest image description
     * @return response result
     */
    ImageResponse genImages(ImageRequest imageRequest);

    /**
     * Image edit
     *
     * @param image  Image object
     * @param prompt edit description
     * @return response result
     */
    ImageResponse editImages(File image, String prompt);

    /**
     * Image edit
     *
     * @param image            Image object
     * @param imageEditRequest Image parameters
     * @return response result
     */
    ImageResponse editImages(File image, ImageEditRequest imageEditRequest);

    /**
     * Image edit
     *
     * @param image            Image object，PNG image smaller than 4M
     * @param mask             Image object，PNG image smaller than 4M
     * @param imageEditRequest Image parameters
     * @return response result
     */
    ImageResponse editImages(File image, File mask, ImageEditRequest imageEditRequest);

    /**
     * Vector calculation: single text
     * Text vector computation is a technique used in the field of natural language processing (NLP) to measure and compare text similarity.
     * In this approach, each word or phrase is converted into a vector, which can be used to compare similarities between different texts
     * and make recommendations or classifications if needed
     *
     * @param input text information
     * @return response result
     */
    EmbeddingResponse embeddings(String input);

    /**
     * Vector calculation: multiple texts
     * Text vector computation is a technique used in the field of natural language processing (NLP) to measure and compare text similarity.
     * In this approach, each word or phrase is converted into a vector, which can be used to compare similarities between different texts
     * and make recommendations or classifications if needed
     *
     * @param inputs multiple texts
     * @return response result
     */
    EmbeddingResponse embeddings(String... inputs);

    /**
     * Vector calculation: multiple texts
     * Text vector computation is a technique used in the field of natural language processing (NLP) to measure and compare text similarity.
     * In this approach, each word or phrase is converted into a vector, which can be used to compare similarities between different texts
     * and make recommendations or classifications if needed
     * @param inputs multiple texts
     * @return response result
     */
    EmbeddingResponse embeddings(List<String> inputs);

    /**
     * Vector calculation; input parameters
     * Text vector computation is a technique used in the field of natural language processing (NLP) to measure and compare text similarity.
     * In this approach, each word or phrase is converted into a vector, which can be used to compare similarities between different texts
     * and make recommendations or classifications if needed
     *
     * @param embeddingRequest request result
     * @return response result
     */
    EmbeddingResponse embeddings(EmbeddingRequest embeddingRequest);

    /**
     * Get the file
     *
     * @return return response
     */
    OpenAiResponse<File> files();

    /**
     * Upload file
     *
     * @param file file
     * @return return response
     */
    UploadFileResponse uploadFile(File file);

    /**
     * Upload file
     *
     * @param purpose Use "fine-tune" for Fine-tuning. This allows us to validate the format of the uploaded file.
     * @param file file
     * @return response result
     */
    UploadFileResponse uploadFile(String purpose, File file);

    /**
     * Delete file
     *
     * @param fileId file ID
     * @return response result
     */
    DeleteFileResponse deleteFile(String fileId);

    /**
     * Convert audio to text
     *
     * @param file                  audio file
     * @param transcriptionsRequest request information
     * @return response result
     */
    WhisperResponse speed2TextTranscriptions(File file, TranscriptionsRequest transcriptionsRequest);

    /**
     * Audio translation
     *
     * @param file                audio file
     * @param translationsRequest request information
     * @return response result
     */
    WhisperResponse speed2TextTranslations(File file, TranslationsRequest translationsRequest);

    /**
     * Billing query
     *
     * @return response result
     */
    Subscription subscription();

    /**
     * Consumption query
     *
     * @param starDate start time
     * @param endDate  end time
     * @return  response result
     */
    BillingUsage billingUsage(@NotNull LocalDate starDate, @NotNull LocalDate endDate);
}
