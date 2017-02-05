package me.minidigger.voxelgameslib.bukkit.converter;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.convert.Converter;
import me.minidigger.voxelgameslib.api.map.Vector3D;

import org.bukkit.util.Vector;

/**
 * Created by Martin on 12.01.2017.
 */
@Singleton
public class VectorConverter implements Converter<Vector3D, Vector> {

    @Override
    public Vector fromVGL(Vector3D vector3D) {
        return new Vector(vector3D.getX(), vector3D.getY(), vector3D.getZ());
    }

    @Override
    public Vector3D toVGL(Vector vector) {
        return new Vector3D(vector.getX(), vector.getY(), vector.getZ());
    }
}
