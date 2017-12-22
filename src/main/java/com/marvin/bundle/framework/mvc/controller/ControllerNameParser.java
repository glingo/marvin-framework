package com.marvin.bundle.framework.mvc.controller;

import com.marvin.component.kernel.Kernel;
import com.marvin.component.kernel.bundle.Bundle;
import com.marvin.component.util.StringUtils;

public class ControllerNameParser {
    
    private static final String CONTROLLER_PATTERN = "%s.controller.%sController::%s";
    private final Kernel kernel;

    public ControllerNameParser(Kernel kernel) {
        this.kernel = kernel;
    }
    
    public String parse(String controller) throws Exception {
        String original = controller;
        
        String[] fragments = controller.split(":");
        
        if(fragments.length != 3) {
            String msg = String.format("The '%s' controller is not a valid 'a:b:c' controller string.", controller);
            throw new Exception(msg);
        }
        
        String name = fragments[0];
        String ctrl = fragments[1];
        String action = fragments[2];
        
        Bundle bundle = this.kernel.getBundle(name);
        
        if(bundle == null) {
            String msg = String.format(
                "The '%s' (from the _controller value '%s') does not exist or is not enabled in your kernel!.", 
                name,
                original
            );
            
            String alternative = StringUtils.findAlternative(name, this.kernel.getBundles().keySet());
            
            if(alternative != null) {
                msg += String.format("\nDid you mean '%s:%s:%s' ?\n", alternative, ctrl, action);
            }
            
            throw new Exception(msg);
        }
        
        return String.format(CONTROLLER_PATTERN, bundle.getNamespace(), ctrl, action);
    }
    
}
