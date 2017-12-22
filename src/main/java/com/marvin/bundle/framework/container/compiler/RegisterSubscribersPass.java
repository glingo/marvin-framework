package com.marvin.bundle.framework.container.compiler;

import com.marvin.component.container.ContainerBuilder;
import com.marvin.component.container.compiler.passes.CompilerPass;
import com.marvin.component.container.config.Definition;
import com.marvin.component.container.config.Reference;
import java.util.HashMap;

public class RegisterSubscribersPass implements CompilerPass {
    
    public final static String DISPATCHER_NAME = "event_dispatcher";
    public final static String SUBSCRIBER_TAG = "event_subscriber";

    @Override
    public void accept(ContainerBuilder builder) {
        
        if(!builder.hasDefinition(DISPATCHER_NAME)) {
            return;
        }
        
        Definition dispatcher = builder.getDefinition(DISPATCHER_NAME);
        
        HashMap<String, Definition> taggedDefinitions = builder.findTaggedDefinitions(SUBSCRIBER_TAG);
        
        taggedDefinitions.forEach((String name, Definition definition)->{
            
            // do nothing if not public
            
            // do nothing if abstract
            
//            dispatcher.addCall("addSubscriber", definition);
            
            // shall we instanciate the subscriber here ?
//            Object subscriber = builder.get(name);
            dispatcher.addCall("register", new Reference(name));
        });

    }
    
}
