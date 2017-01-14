package me.minidigger.voxelgameslib.api;

import javax.annotation.Nonnull;

/**
 * All sub interfaces of this interface need to be implemented in implementation modules
 *
 * @param <T> the implementation type
 */
public interface ImplementMe<T> {

    /**
     * @return the object that is wrapped by this class
     */
    @Nonnull
    T getImplementationType();

    /**
     * sets the object that will be wrapped by this class
     *
     * @param t the object that will be wrapped by this class
     */
    void setImplementationType(@Nonnull T t);
}
