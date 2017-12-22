package com.marvin.bundle.framework.container.compiler;

import com.marvin.component.container.ContainerBuilder;
import com.marvin.component.container.compiler.passes.CompilerPass;
import com.marvin.component.container.config.Definition;
import com.marvin.component.container.config.Reference;
import java.util.HashMap;

public class RegisterViewResolversPass implements CompilerPass {
    
    public final static String RESOLVER_NAME = "view_resolver";
    public final static String RESOLVER_TAG = "view_resolver";

    @Override
    public void accept(ContainerBuilder builder) {
        
        if(!builder.hasDefinition(RESOLVER_NAME)) {
            return;
        }
        
        Definition dispatcher = builder.getDefinition(RESOLVER_NAME);
        
        HashMap<String, Definition> taggedDefinitions = builder.findTaggedDefinitions(RESOLVER_TAG);
        
        taggedDefinitions.forEach((String name, Definition definition)->{
            
            // do nothing if not public
            
            // do nothing if abstract
//            Object resolver = builder.get(name);
            
            dispatcher.addCall("addResolver", new Reference(name));
        });
    }
    
}
