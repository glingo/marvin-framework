package com.marvin.bundle.framework.mvc.view;

import com.marvin.component.container.exception.ContainerException;
import com.marvin.component.resolver.ContainerResolver;

public class ContainerViewResolver extends ContainerResolver<ViewInterface> implements ViewResolverInterface {

    public ContainerViewResolver() {
        super(ViewInterface.class);
    }

    @Override
    public ViewInterface resolve(String name) throws ContainerException {
        return super.resolve(name);
    }
}
