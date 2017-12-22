package com.marvin.bundle.framework.mvc.controller.event;

import com.marvin.bundle.framework.mvc.Handler;

/**
 * This is the first Event that Handler emit.
 */
public class GetResultEvent<I, O, R> extends HandlerEvent<I, O> {

    private R result;
    private I request;
    private O response;
    
    public GetResultEvent(Handler<I, O> handler, I request, O response, R result) {
        super(handler);
        this.result = result;
        this.request = request;
        this.response = response;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
        this.stopEventPropagation();
    }
    
    public boolean hasResult() {
        return this.result != null;
    }

    public I getRequest() {
        return request;
    }

    public void setRequest(I request) {
        this.request = request;
    }

    public O getResponse() {
        return response;
    }

    public void setResponse(O response) {
        this.response = response;
    }
}
