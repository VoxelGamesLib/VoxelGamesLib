package me.minidigger.voxelgameslib.api.event;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.handler.Handler;

import lombok.extern.java.Log;

/**
 * Handles all events (registration/unregistration and calling)
 */
@Log
@Singleton
public class VGLEventHandler implements Handler {
    
    @Inject
    private Injector injector;
    
    @Nonnull
    private Map<Class<? extends Event>, List<RegisteredListener>> registeredListeners = new ConcurrentHashMap<>();
    
    @Override
    public void start() {
        registerEvents();
    }
    
    @Override
    public void stop() {
        unregisterEvents();
    }
    
    /**
     * Calls a event, will execute all registered listeners that are subscribed to this event
     *
     * @param event the event to execute
     */
    public void callEvent(@Nonnull Event event) {
        registeredListeners.getOrDefault(event.getClass(), new CopyOnWriteArrayList<>()).forEach(listener -> listener.execute(event));
    }
    
    /**
     * Searches the classpath for events to register. registers every event
     */
    public void registerEvents() {
        Set<Class<?>> listeners = new Reflections().getTypesAnnotatedWith(EventListener.class);
        
        listeners.forEach(this::registerEvents);
    }
    
    /**
     * registers all events in that class (and creates a new instance using guice)
     *
     * @param listener the class that  contains listeners that should be registered
     */
    public void registerEvents(@Nonnull Class<?> listener) {
        registerEvents(injector.getInstance(listener));
    }
    
    /**
     * registers all events in that object
     *
     * @param listener the object that contains listeners that should be registered
     */
    public void registerEvents(@Nonnull Object listener) {
        for (Method executor : listener.getClass().getMethods()) {
            if (!executor.isAnnotationPresent(EventListener.class)) {
                continue;
            }
            if (executor.getParameterCount() != 1) {
                log.warning("Could not register event listener " + executor.getDeclaringClass().getName() + ":" + executor.getName() + ": Method as more than 1 parameter!");
                continue;
            }
            if (!Event.class.isAssignableFrom(executor.getParameterTypes()[0])) {
                log.warning("Could not register event listener " + executor.getDeclaringClass().getName() + ":" + executor.getName() + ": " + executor.getParameterTypes()[0].getName() + " is not a valid event!");
                continue;
            }
            //EventListener eventListener = executor.getAnnotation(EventListener.class); TODO DO something with the EventListener annotation
            RegisteredListener registeredListener = new RegisteredListener(listener, executor);
            @SuppressWarnings("unchecked")
            Class<? extends Event> event = (Class<? extends Event>) executor.getParameterTypes()[0];
            
            List<RegisteredListener> list = registeredListeners.get(event);
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
            }
            list.add(registeredListener);
            registeredListeners.put(event, list);
        }
    }
    
    /**
     * Unregisteres all registered listeners
     */
    public void unregisterEvents() {
        registeredListeners.values().forEach(list -> list.forEach(this::unregisterEvents));
    }
    
    /**
     * unregisters all events for that class
     *
     * @param listener the class which listeners should be unregistered
     */
    public void unregisterEvents(@Nonnull Class<?> listener) {
        registeredListeners.values().forEach(list -> list.stream().filter(registeredListener -> registeredListener.getExecutor().getDeclaringClass().equals(listener)).forEach(this::unregisterEvents));
    }
    
    /**
     * unregisters one listener
     *
     * @param listener the listener to unregister
     */
    public void unregisterEvents(@Nonnull RegisteredListener listener) {
        for (Class<? extends Event> key : registeredListeners.keySet()) {
            List<RegisteredListener> listeners = registeredListeners.get(key);
            if (listeners.contains(listener)) {
                listeners.remove(listener);
                registeredListeners.put(key, listeners);
            }
        }
    }
}
