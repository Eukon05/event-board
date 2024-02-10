package pl.eukon05.eventboard.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
public class ResultWrapper<T> {

    @JsonIgnore
    public static final String CREATED_RESOURCE_ID = "createdResourceID";

    @JsonIgnore
    public static final String NO_CONTENT = "No content";

    @JsonIgnore
    @Schema(hidden = true)
    private final Result result;

    private T data;
    private Map<String, String> details;

    //Two special getter methods for Spring to automatically convert into JSON
    public HttpStatus getStatus() {
        return result.getStatus();
    }

    public String getMessage() {
        return result.getMessage();
    }

    //Override for the auto-generated builder
    public static class ResultWrapperBuilder<T> {
        private long createdResourceID;

        public ResultWrapper<T> build() {
            if (createdResourceID > 0) {
                if (details == null) {
                    details = new HashMap<>();
                }
                details.put(CREATED_RESOURCE_ID, String.valueOf(createdResourceID));
            } else {
                details = Collections.emptyMap();
            }

            return new ResultWrapper<>(result, data, details);
        }

        public ResultWrapperBuilder<T> createdResourceID(long id) {
            createdResourceID = id;
            return this;
        }
    }
    //A method to easily "wrap" the Result object, when it's the only data returned by a use case
    public static ResultWrapper<String> wrap(Result result) {
        return ResultWrapper.<String>builder().result(result).data(NO_CONTENT).build();
    }


}
