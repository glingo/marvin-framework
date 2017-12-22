package com.marvin.bundle.framework.mvc.controller.event;

import com.marvin.bundle.framework.mvc.Handler;
import java.util.List;

import com.marvin.bundle.framework.mvc.controller.ControllerReference;

public class FilterControllerArgumentsEvent<R> extends FilterControllerEvent<R> {

    private List<Object> arguments;

    public FilterControllerArgumentsEvent(Handler<R, ControllerReference> handler, ControllerReference controller, List<Object> arguments) {
        super(handler, controller);
        this.arguments = arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }

    public List<Object> getArguments() {
        return arguments;
    }
}
