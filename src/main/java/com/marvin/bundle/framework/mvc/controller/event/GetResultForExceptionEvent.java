package com.marvin.bundle.framework.mvc.controller.event;

import com.marvin.bundle.framework.mvc.Handler;

public class GetResultForExceptionEvent<I, O, R> extends GetResultEvent<I, O, R> {
    
    private Exception exception;

    public GetResultForExceptionEvent(Handler<I, O> handler, I request, O response, R result, Exception exception) {
        super(handler, request, response, result);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}