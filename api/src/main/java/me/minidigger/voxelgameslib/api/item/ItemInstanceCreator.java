package me.minidigger.voxelgameslib.api.item;

import com.google.gson.InstanceCreator;
import com.google.inject.Injector;

import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Creates items, duh
 */
@Singleton
public class ItemInstanceCreator implements InstanceCreator<Item> {

    @Inject
    private Injector injector;

    @Override
    public Item createInstance(Type type) {
        return injector.getInstance(Item.class);
    }
}
