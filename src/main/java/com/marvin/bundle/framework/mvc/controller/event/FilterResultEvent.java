package com.marvin.bundle.framework.mvc.controller.event;

import com.marvin.bundle.framework.mvc.Handler;

public class FilterResultEvent<I, O, R> extends HandlerEvent<I, O> {

    private R result;
    private I request;
    private O response;
    
    public FilterResultEvent(Handler<I, O> handler, I request, O response, R result) {
        super(handler);
        this.request = request;
        this.result = result;
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

    public O getResponse() {
        return response;
    }
    
}
