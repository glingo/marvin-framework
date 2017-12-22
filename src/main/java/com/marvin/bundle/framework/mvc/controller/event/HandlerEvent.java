package com.marvin.bundle.framework.mvc.controller.event;

import com.marvin.bundle.framework.mvc.Handler;
import com.marvin.component.event.Event;

public class HandlerEvent<I, O> extends Event {

    private Handler<I, O> handler;
    
    public HandlerEvent(Handler<I, O> handler) {
        this.handler = handler;
    }

    public Handler<I, O> getHandler() {
        return handler;
    }

    public void setHandler(Handler<I, O> handler) {
        this.handler = handler;
    }
}
