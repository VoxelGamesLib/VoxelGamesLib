package me.minidigger.voxelgameslib.api.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.minidigger.voxelgameslib.api.exception.EventException;

/**
 * Created by Martin on 30.10.2016.
 */
public class RegisteredListener {
    
    private Object listener;
    private Method executor;
    
    public RegisteredListener(Object listener, Method executor) {
        this.listener = listener;
        this.executor = executor;
    }
    
    public void execute(Event event) {
        try {
            executor.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EventException("Could not handle event " + event.getClass().getName() + " to listener in " + listener.getClass().getName(), e);
        }
    }
    
    public Method getExecutor() {
        return executor;
    }
    
    public Object getListener() {
        return listener;
    }
}
