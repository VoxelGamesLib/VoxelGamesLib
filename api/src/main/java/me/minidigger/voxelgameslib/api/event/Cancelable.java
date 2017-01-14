package me.minidigger.voxelgameslib.api.event;

/**
 * Represents a cancelable event
 */
public interface Cancelable {

    /**
     * Sets the cancellation state of this event. A cancelled event will not be executed in the
     * server, but will still pass to other plugins.
     *
     * @param canceled true if you wish to cancel this event
     */
    void setCanceled(boolean canceled);

    /**
     * Gets the cancellation state of this event. A cancelled event will not be executed in the
     * server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    boolean isCanceled();
}
