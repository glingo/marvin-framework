package com.marvin.bundle.framework.mvc;

import com.marvin.bundle.framework.mvc.model.Model;
import java.util.Map;

public class ModelAndView {

    private Object view;
    private Model model;

    public ModelAndView() {
    }

    public ModelAndView(Object view, Model model) {
        this.view = view;
        this.model = model;
    }

    public ModelAndView(Object view) {
        this.view = view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public <T> T getView(Class<T> type) {
        return (T) view;
    }

    public Object getView() {
        return view;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setModel(Map<String, Object> model) {
        if (this.model == null) {
            this.model = new Model();
        }

        this.model.putAll(model);
    }
    
    public static ModelAndViewBuilder builder() {
        return new ModelAndViewBuilder();
    }

    public static class ModelAndViewBuilder {

        private Object view;
        private Map<String, Object> model = new Model();
        
        public ModelAndViewBuilder view(Object view) {
            this.view = view;
            return this;
        }

        public ModelAndViewBuilder model(Model model) {
            this.model = model;
            return this;
        }
        
        public ModelAndViewBuilder model(Map<String, Object> model) {
            this.model = model;
            return this;
        }
        
        public ModelAndViewBuilder model(String key, Object model) {
            this.model.put(key, model);
            return this;
        }
        
        public ModelAndView build() {
            ModelAndView mav = new ModelAndView();

            mav.setView(this.view);
            mav.setModel(this.model);
            
            return mav;
        }
    }
}
