package com.marvin.bundle.framework.mvc.controller.argument;

public interface ArgumentValueResolverInterface<I, O> {
    
    public boolean support(I request, O response, ArgumentMetadata argument);
    
    public Object resolve(I request, O response, ArgumentMetadata argument);
}
