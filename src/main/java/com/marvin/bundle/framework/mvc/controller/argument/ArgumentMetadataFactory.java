package com.marvin.bundle.framework.mvc.controller.argument;

import com.marvin.bundle.framework.mvc.controller.ControllerReference;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public interface ArgumentMetadataFactory {
    
    default List<ArgumentMetadata> createArgumentMetadata(ControllerReference reference){
        List<ArgumentMetadata> arguments = new ArrayList<>();
        Parameter[] parameters = reference.getAction().getParameters();
        for (Parameter param : parameters) {
//            if (!param.isNamePresent()) {
//                // maybe throw an exception ?
//                System.err.println("No name provided !");
//            }
//            System.err.println("Name : ".concat(param.getName()));
            arguments.add(new ArgumentMetadata(param.getName(), param.getType(), param.isVarArgs()));
        }
        return arguments;
    }
}
