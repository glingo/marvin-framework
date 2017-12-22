package com.marvin.bundle.framework.mvc.view;

import com.marvin.component.kernel.Kernel;
import com.marvin.component.kernel.bundle.Bundle;
import com.marvin.component.util.StringUtils;

public class ViewNameParser {
    
    private static final String VIEW_PATTERN = "%s/resources/view/%s";
    private final Kernel kernel;
    private final String engine;

    public ViewNameParser(Kernel kernel, String engine) {
        this.kernel = kernel;
        this.engine = engine;
    }
    
    public String parse(String viewName) throws Exception {
        String original = viewName;
        
        if (viewName.contains("::")) {
            String[] fragments = viewName.split("::");
            String view = fragments[1];
            
            String end = this.engine.concat("/").concat(StringUtils.capitalize(view)).concat("View");
            
            return String.format(VIEW_PATTERN, this.kernel.getRootDir(), end);
        }
        
        String[] fragments = viewName.split(":");
        
        if(fragments.length != 3) {
            String msg = String.format("The '%s' view is not a valid 'a:b:c' view string.", viewName);
            throw new Exception(msg);
        }
        
        String name = fragments[0];
        String pkg = fragments[1];
        String view = fragments[2];
        
        Bundle bundle = this.kernel.getBundle(name);
        
        if(bundle == null) {
            String msg = String.format(
                "The '%s' (from the _view value '%s') does not exist or is not enabled in your kernel!.", 
                name,
                original
            );
            
            String alternative = StringUtils.findAlternative(name, this.kernel.getBundles().keySet());
            
            if(alternative != null) {
                msg += String.format("\nDid you mean '%s:%s:%s' ?\n", alternative, pkg, view);
            }
            
            throw new Exception(msg);
        }
        
        String end = pkg.concat("/").concat(this.engine).concat("/").concat(StringUtils.capitalize(view)).concat("View");
        return String.format(VIEW_PATTERN, bundle.getNamespace(), end);
    }
}
