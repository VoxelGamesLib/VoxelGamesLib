package me.minidigger.voxelgameslib.bukkit.converter;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.bossbar.BossBarColor;
import me.minidigger.voxelgameslib.api.convert.Converter;

import org.bukkit.boss.BarColor;

/**
 * Created by Martin on 12.01.2017.
 */
@Singleton
public class BossBarColorConverter implements Converter<BossBarColor, BarColor> {
    @Override
    public BarColor fromVGL(BossBarColor bossBarColor) {
        return BarColor.valueOf(bossBarColor.name());
    }

    @Override
    public BossBarColor toVGL(BarColor barColor) {
        return BossBarColor.valueOf(barColor.name());
    }
}
