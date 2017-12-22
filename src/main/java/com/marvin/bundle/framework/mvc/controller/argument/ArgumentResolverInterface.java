package com.marvin.bundle.framework.mvc.controller.argument;

import com.marvin.bundle.framework.mvc.controller.ControllerReference;
import java.util.List;

@FunctionalInterface
public interface ArgumentResolverInterface extends ArgumentMetadataFactory  {
    List<Object> getArguments(Object request, Object response, ControllerReference controller);
}
