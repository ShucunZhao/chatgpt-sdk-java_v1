package com.prj.chatgpt;

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
import com.prj.chatgpt.domain.images.ImageRequest;
import com.prj.chatgpt.domain.images.ImageResponse;
import com.prj.chatgpt.domain.other.OpenAiResponse;
import com.prj.chatgpt.domain.qa.QACompletionRequest;
import com.prj.chatgpt.domain.qa.QACompletionResponse;
import com.prj.chatgpt.domain.whisper.WhisperResponse;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

/**
 * @description Define the interface based on the ChatGPT official website API model.
 * URL：https://platform.openai.com/playground
 */
public interface IOpenAiApi {

    String v1_completions = "v1/completions";

    /**
     * Text Q&A
     * @param qaCompletionRequest request information
     * @return                    response result
     */
    @POST(v1_completions)
    Single<QACompletionResponse> completions(@Body QACompletionRequest qaCompletionRequest);

    String v1_chat_completions = "v1/chat/completions";
    /**
     * Default GPT-3.5 Q&A model
     * @param chatCompletionRequest request information
     * @return                      response result
     */
    @POST(v1_chat_completions)
    Single<ChatCompletionResponse> completions(@Body ChatCompletionRequest chatCompletionRequest);

    /**
     * Text fix
     *
     * @param editRequest request information; parameters for editing text
     * @return response result
     */
//    @POST("v1/edits") // This version was deprecated already
    @POST(v1_chat_completions)
    Single<EditResponse> edits(@Body EditRequest editRequest);

    /**
     * Generate picture
     * curl https://api.openai.com/v1/images/generations \
     * -H "Content-Type: application/json" \
     * -H "Authorization: Bearer $OPENAI_API_KEY" \
     * -d '{
     * "prompt": "A cute baby sea otter",
     * "n": 2,
     * "size": "1024x1024"
     * }'
     * <p>
     * {
     * "created": 1589478378,
     * "data": [
     * {
     * "url": "https://..."
     * },
     * {
     * "url": "https://..."
     * }
     * ]
     * }
     *
     * @param imageRequest image object
     * @return response result
     */
    @POST("v1/images/generations")
    Single<ImageResponse> genImages(@Body ImageRequest imageRequest);

    /**
     * Modify pictures
     * <p>
     * curl https://api.openai.com/v1/images/edits \
     * -H "Authorization: Bearer $OPENAI_API_KEY" \
     * -F image="@otter.png" \
     * -F mask="@mask.png" \
     * -F prompt="A cute baby sea otter wearing a beret" \
     * -F n=2 \
     * -F size="1024x1024"
     * <p>
     * {
     * "created": 1589478378,
     * "data": [
     * {
     * "url": "https://..."
     * },
     * {
     * "url": "https://..."
     * }
     * ]
     * }
     *
     * @param image image object
     * @param mask picture object
     * @param requestBodyMap request parameters
     * @return response result
     */
    @Multipart
    @POST("v1/images/edits")
    Single<ImageResponse> editImages(@Part MultipartBody.Part image, @Part MultipartBody.Part mask, @PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * Vector calculation
     * curl https://api.openai.com/v1/images/variations \
     * -H "Authorization: Bearer $OPENAI_API_KEY" \
     * -F image="@otter.png" \
     * -F n=2 \
     * -F size="1024x1024"
     *
     * @param embeddingRequest request object
     * @return response result
     */
    @POST("v1/embeddings")
    Single<EmbeddingResponse> embeddings(@Body EmbeddingRequest embeddingRequest);

    /**
     * File list, after you upload the file to the server, you can get the list information
     * curl https://api.openai.com/v1/files \
     * -H "Authorization: Bearer $OPENAI_API_KEY"
     *
     * @return response result
     */
    @GET("v1/files")
    Single<OpenAiResponse<File>> files();

    /**
     * Upload File: Uploads a file containing documentation to be used in various endpoints/functions. Currently, all files uploaded by an organization can be up to 1GB in size. If you need to increase the storage limit, please contact the official website.
     * curl https://api.openai.com/v1/files \
     * -H "Authorization: Bearer $OPENAI_API_KEY" \
     * -F purpose="fine-tune" \
     * -F file="@mydata.jsonl"
     *
     * @param file file
     * @param purpose "fine-tune"
     * @return response result
     */
    @Multipart
    @POST("v1/files")
    Single<UploadFileResponse> uploadFile(@Part MultipartBody.Part file, @Part("purpose") RequestBody purpose);

    /**
     * Delete Files
     * curl https://api.openai.com/v1/files/file-XjGxS3KTG0uNmNOK362iJua3 \
     * -X DELETE \
     * -H "Authorization: Bearer $OPENAI_API_KEY"
     *
     * @param fileId file ID
     * @return response result
     */
    @DELETE("v1/files/{file_id}")
    Single<DeleteFileResponse> deleteFile(@Path("file_id") String fileId);

    /**
     * Retrieve files
     * curl https://api.openai.com/v1/files/file-XjGxS3KTG0uNmNOK362iJua3 \
     * -H "Authorization: Bearer $OPENAI_API_KEY"
     *
     * @param fileId file ID
     * @return response result
     */
    @GET("v1/files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    /**
     * Retrieve file content information
     * curl https://api.openai.com/v1/files/file-XjGxS3KTG0uNmNOK362iJua3/content \
     * -H "Authorization: Bearer $OPENAI_API_KEY" > file.jsonl
     *
     * @param fileId file ID
     * @return response result
     */
    @Streaming
    @GET("v1/files/{file_id}/content")
    Single<ResponseBody> retrieveFileContent(@Path("file_id") String fileId);

    /**
     * Convert speech to text
     * curl https://api.openai.com/v1/audio/transcriptions \
     * -H "Authorization: Bearer $OPENAI_API_KEY" \
     * -H "Content-Type: multipart/form-data" \
     * -F file="@/path/to/file/audio.mp3" \
     * -F model="whisper-1"
     *
     * @param file voice file
     * @param requestBodyMap request information
     * @return response result
     */
    @Multipart
    @POST("v1/audio/transcriptions")
    Single<WhisperResponse> speed2TextTranscriptions(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * Voice translation
     * curl https://api.openai.com/v1/audio/translations \
     * -H "Authorization: Bearer $OPENAI_API_KEY" \
     * -H "Content-Type: multipart/form-data" \
     * -F file="@/path/to/file/german.m4a" \
     * -F model="whisper-1"
     *
     * @param file voice file
     * @param requestBodyMap request information
     * @return response result
     */
    @Multipart
    @POST("v1/audio/translations")
    Single<WhisperResponse> speed2TextTranslations(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * Bill query
     *
     * @return response result
     */
    @GET("v1/dashboard/billing/subscription")
    Single<Subscription> subscription();

    /**
     * Consumption query
     *
     * @param starDate start time
     * @param endDate end time
     * @return response data
     */
    @GET("v1/dashboard/billing/usage")
    Single<BillingUsage> billingUsage(@Query("start_date") LocalDate starDate, @Query("end_date") LocalDate endDate);
}