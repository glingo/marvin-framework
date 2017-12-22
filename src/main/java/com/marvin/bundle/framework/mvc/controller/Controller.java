package com.marvin.bundle.framework.mvc.controller;

import com.marvin.component.container.awareness.ContainerAware;
import com.marvin.component.form.FormBuilder;
import com.marvin.component.form.support.FormType;

public class Controller extends ContainerAware {
    
    protected FormBuilder createFormBuilder(String name, Object data) {
        FormBuilder builder = new FormBuilder(name, new FormType(name, data));
        return builder;
    }
}
