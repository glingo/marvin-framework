package com.marvin.bundle.framework.mvc.controller.event;

import com.marvin.bundle.framework.mvc.Handler;

public class FilterRequestEvent<I, O> extends HandlerEvent<I, O> {

    private I request;
    private O response;
    
    public FilterRequestEvent(Handler<I, O> handler, I request, O response) {
        super(handler);
        this.request = request;
        this.response = response;
    }

    public I getRequest() {
        return request;
    }

    public O getResponse() {
        return response;
    }
}
