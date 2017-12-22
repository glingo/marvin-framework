package com.marvin.bundle.framework.container;

import com.marvin.bundle.framework.FrameworkBundle;

import com.marvin.component.configuration.ConfigurationInterface;
import com.marvin.component.container.ContainerBuilder;
import com.marvin.component.container.extension.Extension;
import com.marvin.component.container.xml.XMLDefinitionReader;
import com.marvin.component.resource.ResourceService;
import com.marvin.component.resource.loader.ClasspathResourceLoader;
import com.marvin.component.resource.reference.ResourceReference;
import com.marvin.component.xml.XMLReader;

import java.util.Map;

public class FrameworkExtension extends Extension {

    @Override
    public void load(Map<String, Object> configs, ContainerBuilder builder) {
        
        ResourceService service = ResourceService.builder()
                .with(ResourceReference.CLASSPATH, new ClasspathResourceLoader(FrameworkBundle.class))
                .build();
        XMLReader reader = new XMLDefinitionReader(service, builder);
        
        reader.read("resources/config/services.xml");

        ConfigurationInterface configuration = getConfiguration();
        Map<String, Object> config = processConfiguration(configuration, configs);

        registerRouterConfiguration(config.get("router"), builder);
        registerTemplatingConfiguration(config.get("templating"), builder);

//            HashMap<String, Definition> taggedDefinitions = builder.findTaggedDefinitions("event_subscriber");
//            for (Map.Entry<String, Definition> entrySet : taggedDefinitions.entrySet()) {
//                String id = entrySet.getKey();
//                Definition definition = entrySet.getValue();
//                
//            }
    }

    private void registerRouterConfiguration(Object config, ContainerBuilder builder) {
        if(!(config instanceof Map)) {
            return;
        }
        
        Map<String, Object> conf = (Map<String, Object>) config;
        builder.addParameter("router.resource", conf.get("resource"));
    }

    private void registerTemplatingConfiguration(Object config, ContainerBuilder builder) {
        if(!(config instanceof Map)) {
            return;
        }
        
        Map<String, Object> conf = (Map<String, Object>) config;
        builder.addParameter("templating.engine", conf.getOrDefault("engine", "console"));
    }
}
