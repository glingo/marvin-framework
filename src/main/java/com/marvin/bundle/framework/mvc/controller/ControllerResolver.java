package com.marvin.bundle.framework.mvc.controller;

import com.marvin.component.resolver.Resolver;
import com.marvin.component.util.ClassUtils;
import java.lang.reflect.Method;

public interface ControllerResolver<T> extends Resolver<T, ControllerReference> {
    
    default ControllerReference instantiateController(Class<?> controller, Method meth) throws Exception {
        return new ControllerReference(controller.newInstance(), meth);
    }
    
    default ControllerReference createController(String name) throws Exception {
        if (false == name.contains("::")) {
            String msg = String.format("Unable to find controller '%s'.", name);
            throw new Exception(msg);
        }

        String[] split = name.split("::", 2);

        String className = split[0];
        String methodName = split[1];
        
        Class<?> clazz = ClassUtils.resolveClassName(className, null);
        Method action = ClassUtils.getMethod(clazz, methodName, (Class<?>[]) null);

        return instantiateController(clazz, action);
    }
    
    default ControllerReference castController(Object controller) throws Exception {
        if(controller == null) {
            return null;
        }
        
        if(controller instanceof ControllerReference) { // done;
            return (ControllerReference) controller;
        }
    
        if(controller instanceof String) {
            return createController((String) controller);
        }
        
        return null;
    }

    @Override
    default ControllerReference resolve(T request) throws Exception {
        return castController(request);
    }
}
