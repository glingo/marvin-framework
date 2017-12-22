package com.marvin.bundle.framework.mvc.controller;

import com.marvin.component.container.awareness.ContainerAware;
import com.marvin.component.container.awareness.ContainerAwareInterface;
import com.marvin.component.util.ClassUtils;
import java.lang.reflect.Method;

public class ContainerControllerResolver<T> extends ContainerAware implements ControllerResolver<T> {

    private final ControllerNameParser parser;
    
    public ContainerControllerResolver(ControllerNameParser parser) {
        assert parser != null : "ControllerNameParser could not be null";
        this.parser = parser;
    }

    @Override
    public ControllerReference instantiateController(Class controller, Method meth) throws Exception {
        ControllerReference reference = ControllerResolver.super.instantiateController(controller, meth);
        
        if (reference.getHolder() instanceof ContainerAwareInterface) {
            ((ContainerAwareInterface) reference.getHolder()).setContainer(getContainer());
        }
        
        return reference;
    }
    
    @Override
    public ControllerReference createController(String name) throws Exception {
        
        if(!name.contains("::")) {
            if(name.contains(":")){
                String[] fragments = name.split(":");
                
                switch(fragments.length) {
                    case 3 :    // a:b:c notation
                        name = this.parser.parse(name);
                        break;
                    case 2:     // service:method notation
                        Object service = getContainer().get(fragments[0]);
//                        Method action = ReflectionUtils.findMethod(service.getClass(), fragments[1]);
                        Method action = ClassUtils.getMethod(service.getClass(), fragments[1], (Class[]) null);
                        return new ControllerReference(service, action);
                }
            }
        }
        
        return ControllerResolver.super.createController(name);
    }
}
