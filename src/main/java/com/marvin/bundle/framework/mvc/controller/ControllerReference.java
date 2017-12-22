package com.marvin.bundle.framework.mvc.controller;

import java.lang.reflect.Method;

public class ControllerReference {

    protected Object holder;
    protected Method action;

    public ControllerReference(Object holder, Method action) {
        super();
        this.holder = holder;
        this.action = action;
    }

    public Object getHolder() {
        return holder;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public void setHolder(Object holder) {
        this.holder = holder;
    }

    @Override
    public String toString() {
        return String.format("ControllerReference (%s, %s)", this.holder, this.action);
    }
}
