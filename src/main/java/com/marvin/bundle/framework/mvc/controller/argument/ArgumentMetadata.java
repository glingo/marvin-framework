package com.marvin.bundle.framework.mvc.controller.argument;

public class ArgumentMetadata {
    
    private String name;
    private Class type;
    private boolean variadic;
    private boolean hasDefaultValue = false;
    private Object defaultValue;

    public ArgumentMetadata(String name, Class type, boolean variadic) {
        this.name = name;
        this.type = type;
        this.variadic = variadic;
    }
    
    public ArgumentMetadata(String name, Class type, boolean variadic, Object defaultValue) {
        this.name = name;
        this.type = type;
        this.variadic = variadic;
        this.hasDefaultValue = true;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public boolean isVariadic() {
        return variadic;
    }

    public void setVariadic(boolean variadic) {
        this.variadic = variadic;
    }

    public boolean hasDefaultValue() {
        return hasDefaultValue;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        this.hasDefaultValue = true;
    }
}
