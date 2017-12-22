package com.marvin.bundle.framework.mvc.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Model extends HashMap<String, Object> {

    public Model() {
    }
    
    public Model(Map<String, Object> model) {
        super(model);
    }
    
    public <E> Optional<E> get(String name, Class type) {
        return Optional.ofNullable((E) get(name));
    }
}
