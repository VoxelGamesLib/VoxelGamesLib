package me.minidigger.voxelgameslib.api.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.exception.EventException;

/**
 * Represents a listener method that was registered and saved
 */
class RegisteredListener {
    
    private Object listener;
    private Method executor;
    
    /**
     * @param listener a instance of the listener class
     * @param executor the method that is in the listener class
     */
    RegisteredListener(@Nonnull Object listener, @Nonnull Method executor) {
        this.listener = listener;
        this.executor = executor;
    }
    
    /**
     * Executes the event
     *
     * @param event the event to execute
     */
    void execute(@Nonnull Event event) {
        try {
            executor.invoke(listener, event);
        } catch (@Nonnull IllegalAccessException | InvocationTargetException e) {
            throw new EventException("Could not handle event " + event.getClass().getName() + " to listener in " + listener.getClass().getName(), e);
        }
    }
    
    /**
     * @return the executor that will execute events
     */
    @Nonnull
    Method getExecutor() {
        return executor;
    }
    
    /**
     * @return a object of the listener class
     */
    @Nonnull
    Object getListener() {
        return listener;
    }
}
