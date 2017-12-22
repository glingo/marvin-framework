package com.marvin.bundle.framework.subscriber;

import com.marvin.bundle.framework.mvc.controller.event.FilterResultEvent;
import com.marvin.bundle.framework.mvc.exception.NoViewForControllerException;
import com.marvin.component.event.dispatcher.DispatcherInterface;
import com.marvin.component.event.handler.Handler;
import com.marvin.component.event.subscriber.Subscriber;
import com.marvin.bundle.framework.mvc.ModelAndView;
import com.marvin.bundle.framework.mvc.view.ViewInterface;
import com.marvin.bundle.framework.mvc.view.ViewResolverInterface;

public class ResponseSubscriber extends Subscriber {
    
    protected final ViewResolverInterface viewResolver;

    public ResponseSubscriber(ViewResolverInterface viewResolver) {
        this.viewResolver = viewResolver;
    }
    
    public Handler<FilterResultEvent> onResponse() {
        return e -> {
            Object result = e.getResult();
            Object request = e.getRequest();
            Object response = e.getResponse();
            
            if (!(result instanceof ModelAndView)) {
                throw new Exception("A Controller must return a ModelAndView, did you forget a return statement ?");
            }
            
            ModelAndView mav = (ModelAndView) result;
            Object view = mav.getView();
            
            if(view instanceof String) {
                view = this.viewResolver.resolve(view.toString());
            }

            if (view == null) {
                throw new NoViewForControllerException("No view for this controller");
            }
            
            mav.setView(view);
            
            //TODO call a renderer

            // render view ?
            ((ViewInterface) view).render(e.getHandler(), mav.getModel(), request, response);
        };
    }
    
    @Override
    public void subscribe(DispatcherInterface dispatcher) {
        dispatcher.register(FilterResultEvent.class, onResponse());
    }
}
