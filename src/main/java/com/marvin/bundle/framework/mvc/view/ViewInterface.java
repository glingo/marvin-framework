package com.marvin.bundle.framework.mvc.view;

import com.marvin.bundle.framework.mvc.Handler;
import java.util.Map;

public interface ViewInterface<I, O> {
    
    void load() throws Exception;
    void render(Handler<I, O> handler, Map<String, Object> model, I request, O response) throws Exception;
    
    default void importView(ViewInterface view, Handler<I, O> handler, Map<String, Object> model, I request, O response) throws Exception {
        view.render(handler, model, request, response);
    }
}
