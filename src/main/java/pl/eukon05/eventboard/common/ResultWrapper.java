package pl.eukon05.eventboard.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
public class ResultWrapper {

    @JsonIgnore
    public static final String CREATED_RESOURCE_ID = "createdResourceID";

    @JsonIgnore
    private final Result result;

    private Object data;
    private Map<String, String> details;

    //Two special getter methods for Spring to automatically convert into JSON
    public HttpStatus getStatus() {
        return result.getStatus();
    }

    public String getMessage() {
        return result.getMessage();
    }

    //Override for the auto-generated builder
    public static class ResultWrapperBuilder {
        private static final String NO_CONTENT = "";

        //Override for the auto-generated build method
        public ResultWrapper build() {
            if (details == null)
                details = Collections.emptyMap();

            if (data == null)
                data = NO_CONTENT;

            return new ResultWrapper(result, data, details);
        }

        public ResultWrapperBuilder createdResourceID(long id) {
            if (details == null)
                details = new HashMap<>();

            details.put(CREATED_RESOURCE_ID, String.valueOf(id));

            return this;
        }
    }

    //A method to easily "wrap" the Result object, when it's the only data returned by a use case
    public static ResultWrapper wrap(Result result) {
        return builder().result(result).build();
    }

}
