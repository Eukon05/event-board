package pl.eukon05.eventboard.common;

import lombok.Getter;

@Getter
public class ResultWrapper {

    private final Result result;
    private final Object content;

    private ResultWrapper(Result result) {
        this.result = result;
        this.content = result.getMessage();
    }

    private ResultWrapper(Result result, Object content) {
        this.result = result;
        this.content = content;
    }

    public static ResultWrapper of(Result result) {
        return new ResultWrapper(result);
    }

    public static ResultWrapper of(Result result, Object content) {
        return new ResultWrapper(result, content);
    }

}
