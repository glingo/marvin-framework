package com.marvin.bundle.framework.mvc.view;

import com.marvin.component.resolver.Resolver;

public interface ViewResolverInterface extends Resolver<String, ViewInterface> {
    
    @Override
    ViewInterface resolve(String name) throws Exception;
}
