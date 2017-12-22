package com.marvin.bundle.framework.subscriber;

import com.marvin.bundle.framework.mvc.controller.event.GetResultForExceptionEvent;
import com.marvin.component.event.dispatcher.DispatcherInterface;
import com.marvin.component.event.handler.Handler;
import com.marvin.component.event.subscriber.Subscriber;
import com.marvin.bundle.framework.mvc.ModelAndView;

public class ErrorSubscriber extends Subscriber {
    
    private final String errorPath;

    public ErrorSubscriber(String errorPath) {
        this.errorPath = errorPath;
    }

    private Handler<GetResultForExceptionEvent> onException() {
        // try to get the old model ?
        return e -> {
//            e.getException().printStackTrace();
            e.setResult(ModelAndView.builder()
                .model("exception", e.getException())
                .model("message", e.getException().getMessage())
                .model("result", e.getResult())
                .model("request", e.getRequest())
                .view(this.errorPath)
                .build());
        };
    }
    
    @Override
    public void subscribe(DispatcherInterface dispatcher) {
        dispatcher.register(GetResultForExceptionEvent.class, onException());
    }
}
