package com.marvin.bundle.framework;

import com.marvin.component.kernel.Kernel;
import com.marvin.bundle.framework.mvc.Handler;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public abstract class Application extends Kernel {
    
    protected static Application instance;
    
    protected static final String ENV_PARAMETER_PREFIX = "-env=";
    protected static final String DEBUG_PARAMETER_PREFIX = "-debug=";
    
    private Handler handler;

    public Application(String environment, boolean debug) {
        super(environment, debug);
    }
    
    protected Handler getHandler() {
        if(this.handler == null) {
            this.handler = getContainer().get("handler", Handler.class);
        }
        
        return this.handler;
    }

    @Override
    public void boot() {
        super.boot();
        this.handler = getHandler();
        startup();
        waitForReady();
        ready();
    }
    
    public void startup() {}
    public void waitForReady() {}
    public void ready() {}
    
    public void shutdown() {}
    
    public final void exit() {}
    
    public void end() {
        Runtime.getRuntime().exit(0);
    }
    
    public static synchronized <T extends Application> void launch(final Class<T> applicationClass, String[] args) {
        try {
            String env = Arrays.stream(args)
                .filter(arg -> arg.startsWith(ENV_PARAMETER_PREFIX))
                .map((param) -> {
                    return param.replace(ENV_PARAMETER_PREFIX, "");
                })
                .findFirst().orElse("dev");

            boolean debug = Arrays.stream(args)
                .filter(arg -> arg.startsWith(DEBUG_PARAMETER_PREFIX))
                .map((param) -> {
                    return Boolean.getBoolean(param.replace(DEBUG_PARAMETER_PREFIX, ""));
                })
                .findFirst().orElse(true);

            instance = create(applicationClass, env, debug);
            instance.boot();
        } catch (Exception e) {
            String msg = String.format("Application %s failed to launch", applicationClass);
            throw new Error(msg, e);
        }
    }
    
    public static synchronized <T extends Application> T getInstance(Class<T> applicationClass, String env, boolean debug) {
        if (instance == null) {
            try {
                instance = create(applicationClass, env, debug);
            } catch (Exception e) {
                String msg = String.format("Couldn't construct %s", applicationClass);
                throw(new Error(msg, e));
            }
        }
	return applicationClass.cast(instance);
    }
    
    public static <T extends Application> T create(Class<T> kernelClass, String env, boolean debug) throws Exception {
        Constructor<T> ctor = kernelClass.getConstructor(new Class[]{String.class, boolean.class});
        
        if (!ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
        
        T kernel = ctor.newInstance(env, debug);
        return kernel;
    }
}
