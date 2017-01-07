package me.minidigger.voxelgameslib.bukkit.bossbar;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.bossbar.AbstractBossBar;
import me.minidigger.voxelgameslib.api.bossbar.BossBarColor;
import me.minidigger.voxelgameslib.api.bossbar.BossBarModifier;
import me.minidigger.voxelgameslib.api.bossbar.BossBarStyle;
import me.minidigger.voxelgameslib.api.user.User;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

/**
 * Created by Martin on 07.01.2017.
 */
public class BukkitBossBar extends AbstractBossBar {
    
    private org.bukkit.boss.BossBar bukkitBar;
    
    @Override
    public void setImplObject(@Nonnull Object object) {
        bukkitBar = (BossBar) object;
    }
    
    @Nonnull
    @Override
    public String getTitle() {
        return bukkitBar.getTitle();
    }
    
    @Override
    public void setTitle(@Nonnull String title) {
        bukkitBar.setTitle(title);
    }
    
    @Nonnull
    @Override
    public BossBarColor getColor() {
        return BossBarColor.valueOf(bukkitBar.getColor().name());
    }
    
    @Override
    public void setColor(@Nonnull BossBarColor color) {
        bukkitBar.setColor(BarColor.valueOf(color.name()));
    }
    
    @Nonnull
    @Override
    public BossBarStyle getStyle() {
        return BossBarStyle.valueOf(bukkitBar.getStyle().name().replace("SEGMENTED", "SPLIT"));
    }
    
    @Override
    public void setStyle(@Nonnull BossBarStyle style) {
        bukkitBar.setStyle(BarStyle.valueOf(style.name().replace("SPLIT", "SEGMENTED")));
    }
    
    @Override
    public void removeModifier(@Nonnull BossBarModifier modifier) {
        bukkitBar.removeFlag(BarFlag.valueOf(modifier.name()));
    }
    
    @Override
    public void addModifier(@Nonnull BossBarModifier modifier) {
        bukkitBar.addFlag(BarFlag.valueOf(modifier.name()));
    }
    
    @Override
    public boolean hasModifier(@Nonnull BossBarModifier modifier) {
        return bukkitBar.hasFlag(BarFlag.valueOf(modifier.name()));
    }
    
    @Override
    public void setProgress(double progress) {
        bukkitBar.setProgress(progress);
    }
    
    @Override
    public double getProgress() {
        return bukkitBar.getProgress();
    }
    
    @Override
    public void addUser(@Nonnull User user) {
        super.addUser(user);
        bukkitBar.addPlayer((Player) user.getPlayerObject());
    }
    
    @Override
    public void removeUser(@Nonnull User user) {
        super.removeUser(user);
        bukkitBar.removePlayer((Player) user.getPlayerObject());
    }
    
    @Override
    public void setVisible(boolean visible) {
        bukkitBar.setVisible(visible);
    }
    
    @Override
    public boolean isVisible() {
        return bukkitBar.isVisible();
    }
}
