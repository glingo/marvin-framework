package com.marvin.bundle.framework.mvc.controller.event;

import com.marvin.bundle.framework.mvc.Handler;
import com.marvin.bundle.framework.mvc.controller.ControllerReference;


public class FilterControllerResultEvent<R, I> extends FilterControllerEvent<I> {
    
    private final Object controllerResult;
    
    private R result;

    public FilterControllerResultEvent(Handler<I, ControllerReference> handler, ControllerReference controller, Object controllerResult, R result) {
        super(handler, controller);
        this.controllerResult = controllerResult;
        this.result = result;
    }

    public R getResult() {
        return result;
    }
    
    public void setResult(R result) {
        this.result = result;
        this.stopEventPropagation();
    }
    
    public Object getControllerResult() {
        return this.controllerResult;
    }

    public boolean hasResult(){
        return this.result != null;
    }

    public boolean hasControllerResult(){
        return this.controllerResult != null;
    }
    
}
