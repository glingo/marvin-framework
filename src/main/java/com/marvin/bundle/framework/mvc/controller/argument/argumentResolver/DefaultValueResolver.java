package com.marvin.bundle.framework.mvc.controller.argument.argumentResolver;

import com.marvin.bundle.framework.mvc.controller.argument.ArgumentMetadata;
import com.marvin.bundle.framework.mvc.controller.argument.ArgumentValueResolverInterface;

public class DefaultValueResolver<I, O> implements ArgumentValueResolverInterface<I, O> {

    @Override
    public boolean support(I request, O response, ArgumentMetadata argument) {
        return argument.hasDefaultValue();
    }

    @Override
    public Object resolve(I request, O response, ArgumentMetadata argument) {
        return argument.getDefaultValue();
    }
}
