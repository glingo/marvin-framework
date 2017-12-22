package com.marvin.bundle.framework.subscriber;

import com.marvin.bundle.framework.mvc.ModelAndView;
import com.marvin.bundle.framework.mvc.view.ViewInterface;
import com.marvin.bundle.framework.mvc.controller.event.FilterControllerResultEvent;
import com.marvin.component.event.dispatcher.DispatcherInterface;
import com.marvin.component.event.handler.Handler;
import com.marvin.component.event.subscriber.Subscriber;
import java.util.Map;

public class ViewSubscriber extends Subscriber {

    public static final String VIEW_PARAMETER = "_view";
    
    public Handler<FilterControllerResultEvent> onView() {
        return event -> {
            Object result = event.getControllerResult();

            if(null == result || result instanceof ModelAndView) {
                return;
            }

            // If it is a String object so we will assume that is a view name.
            if(result instanceof String || result instanceof ViewInterface) {
                event.setResult(ModelAndView.builder().view(result).build());
                return;
            }

            // if it is a Map we will assume that is a model.
            if(result instanceof Map) {
                Map<String, Object> model = (Map<String, Object>) result;
                ModelAndView.ModelAndViewBuilder builder = ModelAndView.builder().model(model);

                // if we set view as a model parameter.
                if(model.containsKey(VIEW_PARAMETER)) {
                    Object view = model.remove(VIEW_PARAMETER);
                    builder.view(view);
                }

                event.setResult(builder.model(model).build());
            }
        };
    }
    
    @Override
    public void subscribe(DispatcherInterface dispatcher) {
        dispatcher.register(FilterControllerResultEvent.class, onView());
    }
}
