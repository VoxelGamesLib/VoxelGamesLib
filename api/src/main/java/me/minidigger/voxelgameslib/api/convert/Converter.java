package me.minidigger.voxelgameslib.api.convert;

/**
 * Converter for VoxelGamesLibTypes to implementation types
 *
 * @param <V> the VoxelGamesLib type
 * @param <T> the implementation type
 */
public interface Converter<V, T> {

    T fromVGL(V v);

    V toVGL(T t);
}
