package com.marvin.bundle.framework.mvc.controller.argument;

import com.marvin.bundle.framework.mvc.controller.ControllerReference;
import java.util.ArrayList;
import java.util.List;

public class ArgumentResolver implements ArgumentResolverInterface {
    
    private List<ArgumentValueResolverInterface> resolvers = new ArrayList<>();
    
    public ArgumentResolver() {
        super();
    }

    public ArgumentResolver(List<ArgumentValueResolverInterface> resolvers) {
        this();
        this.resolvers = resolvers;
    }
    
    @Override
    public List<Object> getArguments(Object request, Object response, ControllerReference controller) {
        List<Object> arguments = new ArrayList<>();
        
        createArgumentMetadata(controller).stream().forEach((ArgumentMetadata argument) -> {
            this.resolvers.stream().forEach((ArgumentValueResolverInterface resolver) -> {
                if(resolver.support(request, response, argument)) {
                    Object resolved = resolver.resolve(request, response, argument);
                    arguments.add(resolved);
                }
            });
        });
        
        return arguments;
    }
    
    public void addResolver(ArgumentValueResolverInterface resolver) {
        getResolvers().add(resolver);
    }

    public void setResolvers(List<ArgumentValueResolverInterface> resolvers) {
        this.resolvers = resolvers;
    }

    public List<ArgumentValueResolverInterface> getResolvers() {
        return resolvers;
    }

}
