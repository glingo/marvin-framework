package com.marvin.bundle.framework.mvc;

import java.util.Stack;
import java.util.logging.Logger;

import com.marvin.bundle.framework.mvc.controller.ControllerReference;
import com.marvin.bundle.framework.mvc.controller.ControllerResolver;
import com.marvin.bundle.framework.mvc.controller.argument.ArgumentResolverInterface;
import com.marvin.bundle.framework.mvc.controller.event.FilterControllerArgumentsEvent;
import com.marvin.bundle.framework.mvc.controller.event.FilterControllerEvent;
import com.marvin.bundle.framework.mvc.controller.event.FilterControllerResultEvent;
import com.marvin.bundle.framework.mvc.controller.event.FilterRequestEvent;
import com.marvin.bundle.framework.mvc.controller.event.FilterResultEvent;
import com.marvin.bundle.framework.mvc.controller.event.FinishRequestEvent;
import com.marvin.bundle.framework.mvc.controller.event.GetResultEvent;
import com.marvin.bundle.framework.mvc.controller.event.GetResultForExceptionEvent;
import com.marvin.bundle.framework.mvc.exception.ControllerNotFoundException;
import com.marvin.component.event.dispatcher.DispatcherInterface;
import com.marvin.component.util.ReflectionUtils;
import java.util.List;
import java.util.logging.Level;

// This is the handler for the front controller.
// it will handle every requests and actions that the user do.
// I thik that it should look for a ModelAndView response.
// and return it to the front controller who called it.
// which may be different in web than in swing or javafx.
// for instance, in web there is a servlet that the servlet container call.

public class Handler<I, O> {

    protected final Logger logger = Logger.getLogger(getClass().getName());
    
    protected final Stack<I> stack;
    protected final DispatcherInterface dispatcher;
    protected final ControllerResolver ctrlResolver;
    protected final ArgumentResolverInterface argsResolver;
    
    public Handler(
        DispatcherInterface dispatcher, 
        ControllerResolver ctrlResolver, 
        ArgumentResolverInterface argsResolver, 
        Stack<I> stack
    ) {
        this.dispatcher   = dispatcher;
        this.ctrlResolver = ctrlResolver;
        this.argsResolver = argsResolver;
        this.stack        = stack;
    }
    
    public Handler(
        DispatcherInterface dispatcher, 
        ControllerResolver ctrlResolver, 
        ArgumentResolverInterface argsResolver
    ) {
        this(dispatcher, ctrlResolver, argsResolver, new Stack<>());
    }
    
    /**
     * Delegate handle to a sub-method.
     * 
     * If capture is true, it will handle exceptions that occured.
     * If capture is false, it will finish and re-throw the exception.
     * 
     * @throws Exception Only if one occured and we didn't captured it.
     */
    public <R> R handle(I request, O response, boolean capture) throws Exception {
        this.logger.log(Level.FINEST, "Handling a response {}", response);
        
        R result = null;
        
        try { 
            
            // delegate to a private method the handle job.
            result = handle(request, response, result);
            
        } catch(Exception re) {
            
            // look if we choose to capture exceptions.
            if(!capture) {
                // finish and re-throw it.
                finish(request, response);
                throw re;
            }
            
            this.logger.finest("Handler has capture an exception.");
            
            // handle it.
            result = handleException(re, request, response, result);
        }
        
        this.logger.log(Level.FINEST, "A request have been handled.");
        
        return result;
    }
     
    /**
     * Will trigger events in order to delegate tasks.
     * 
     * We first stack the request in a Stack.
     * 
     * Trigger a first event to get a response.
     * If a response is set during this event we stop propagation and return it.
     * 
     * Otherwise we continue and trigger a 
     * 
     * @param request
     * @param response
     * 
     * @throws Exception If something wrong happened.
     */
    protected <R> R handle(I request, O response, R result) throws Exception {
       
        this.stack.add(request);
        
        filterRequest(request, response);
        
        // Dispatch event Request ( getModelAndView )
        GetResultEvent<I, O, R> re = new GetResultEvent(this, request, response, result);
        this.dispatcher.dispatch(re);
        
        if(re.hasResult()) {
            return re.getResult();
        }
        
        // Find controller via resolver
        ControllerReference controller = this.ctrlResolver.resolve(request);

        // At this point "controller" should be an object.
        if(controller == null){
            // if controller is null we have a problem.
            String msg = "No controller found for this request";
            this.logger.fine(msg);
            throw new ControllerNotFoundException(msg);
        }
        
        // Filter controller
        FilterControllerEvent fe = new FilterControllerEvent(this, controller);
        this.dispatcher.dispatch(fe);
       
        // Get the controller in case that the event changed it.
        controller = fe.getController();
        
        // Resolve arguments to pass
        List<Object> arguments = this.argsResolver.getArguments(request, response, controller);
        
        // Filter controller arguments
        FilterControllerArgumentsEvent argsEvent = new FilterControllerArgumentsEvent(this, controller, arguments);
        this.dispatcher.dispatch(argsEvent);
        
        controller = argsEvent.getController();
        arguments  = argsEvent.getArguments();

        this.logger.log(Level.FINER, String.format("Invoke %s(%s)", controller.getAction(), arguments));

        // invoke controller
        Object controllerResult = ReflectionUtils.invokeMethod(controller.getAction(), controller.getHolder(), arguments.toArray());
        
        if(!(controllerResult instanceof ModelAndView)) {
            // if a controller return an other type than ModelAndView 
            // we trigger an event to resolve the returned type.
        
            FilterControllerResultEvent event = new FilterControllerResultEvent(this, controller, controllerResult, result);
            this.dispatcher.dispatch(event);
                
            // get a ModelAndView for controller result
//            GetModelAndViewForControllerResultEvent<R, T> event = new GetModelAndViewForControllerResultEvent(this, request, controllerResult);
//            this.dispatcher.dispatch(HandlerEvents.RESPONSE, event);

            if(event.hasResult()) {
                controllerResult = event.getResult();
            }
            
            if(!(controllerResult instanceof ModelAndView)) {
                
                // We still not have a ModelAndView instance, let's throw an exception.
                String msg = String.format("The Controller must return a ModelAndView (%s given).", controllerResult);
                
                // Check if the result is null, they may forget a return in controller.
                if(null == controllerResult) {
                    msg += " Did you forget to add a return statement in your controller ?";
                }
                
                throw new Exception(msg);
            }
        }
        
        re.setResult((R) controllerResult);
        
        return filterResult(request, response, re.getResult());
    }
    
    protected void filterRequest(I request, O response) throws Exception {
        
        this.logger.finest("pre-filtering ...");
        
        FilterRequestEvent<I, O> fe = new FilterRequestEvent(this, request, response);
        this.dispatcher.dispatch(fe);
    }
    
    protected <R> R filterResult(I request, O response, R result) throws Exception {
        
        this.logger.finest("Filtering ...");
        
        FilterResultEvent<I, O, R> fe = new FilterResultEvent(this, request, response, result);
        this.dispatcher.dispatch(fe);
        
        return fe.getResult();
    }
    
    private void finish(I request, O response) throws Exception {
        this.logger.finest("Finishing request...");
        this.stack.pop();
        
        FinishRequestEvent<I, O> event = new FinishRequestEvent(this, request, response);
        this.dispatcher.dispatch(event);
    }

    private <R> R handleException(Exception exception, I request, O response, R result) throws Exception {
        
        this.logger.finest("Handle an exception.");
        
        GetResultForExceptionEvent<I, O, R> exceptionEvent = new GetResultForExceptionEvent(this, request, response, result, exception);
        this.dispatcher.dispatch(exceptionEvent);
        
        exception = exceptionEvent.getException();
//        this.logger.log(Level.WARNING, "Handeling an exception {}", exception);
        
        // comment it if too many
//        exception.printStackTrace();
        
        if(!exceptionEvent.hasResult()) {
            finish(request, response);
            
            throw new Exception(exception);
        }

        return filterResult(request, response, exceptionEvent.getResult());
    }
}
