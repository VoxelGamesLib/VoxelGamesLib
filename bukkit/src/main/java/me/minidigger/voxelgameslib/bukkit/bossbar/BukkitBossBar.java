package me.minidigger.voxelgameslib.bukkit.bossbar;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.bossbar.AbstractBossBar;
import me.minidigger.voxelgameslib.api.bossbar.BossBarColor;
import me.minidigger.voxelgameslib.api.bossbar.BossBarModifier;
import me.minidigger.voxelgameslib.api.bossbar.BossBarStyle;
import me.minidigger.voxelgameslib.api.user.User;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

/**
 * Created by Martin on 07.01.2017.
 */
public class BukkitBossBar extends AbstractBossBar<BossBar> {

    @Inject
    private BossBarStyleConverter bossBarStyleConverter;
    @Inject
    private BossBarFlagConverter bossBarFlagConverter;
    @Inject
    private BossBarColorConverter bossBarColorConverter;

    private org.bukkit.boss.BossBar bukkitBar;

    @Override
    public void setImplementationType(@Nonnull BossBar bossBar) {
        bukkitBar = bossBar;
    }

    @Nonnull
    @Override
    public BossBar getImplementationType() {
        return bukkitBar;
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
        return bossBarColorConverter.toVGL(bukkitBar.getColor());
    }

    @Override
    public void setColor(@Nonnull BossBarColor color) {
        bukkitBar.setColor(bossBarColorConverter.fromVGL(color));
    }

    @Nonnull
    @Override
    public BossBarStyle getStyle() {
        return bossBarStyleConverter.toVGL(bukkitBar.getStyle());
    }

    @Override
    public void setStyle(@Nonnull BossBarStyle style) {
        bukkitBar.setStyle(bossBarStyleConverter.fromVGL(style));
    }

    @Override
    public void removeModifier(@Nonnull BossBarModifier modifier) {
        bukkitBar.removeFlag(bossBarFlagConverter.fromVGL(modifier));
    }

    @Override
    public void addModifier(@Nonnull BossBarModifier modifier) {
        bukkitBar.addFlag(bossBarFlagConverter.fromVGL(modifier));
    }

    @Override
    public boolean hasModifier(@Nonnull BossBarModifier modifier) {
        return bukkitBar.hasFlag(bossBarFlagConverter.fromVGL(modifier));
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
        bukkitBar.addPlayer((Player) user.getImplementationType());
    }

    @Override
    public void removeUser(@Nonnull User user) {
        super.removeUser(user);
        bukkitBar.removePlayer((Player) user.getImplementationType());
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
