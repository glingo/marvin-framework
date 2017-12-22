package com.marvin.bundle.framework.mvc.controller.argument.argumentResolver;

import com.marvin.bundle.framework.mvc.model.Model;
import com.marvin.bundle.framework.mvc.controller.argument.ArgumentMetadata;
import com.marvin.bundle.framework.mvc.controller.argument.ArgumentValueResolverInterface;
import com.marvin.component.util.ClassUtils;

public class ModelArgumentResolver<I, O> implements ArgumentValueResolverInterface<I, O> {
    
    private Model model;
    
    @Override
    public boolean support(I request, O response, ArgumentMetadata argument) {
        return ClassUtils.isAssignable(argument.getType(), Model.class);
    }

    @Override
    public Object resolve(I request, O response, ArgumentMetadata argument) {
        if(null == this.model) {
            this.model = new Model();
        }
        return this.model;
    }
}
