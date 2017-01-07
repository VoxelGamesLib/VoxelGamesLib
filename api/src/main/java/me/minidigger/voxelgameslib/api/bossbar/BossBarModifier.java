package me.minidigger.voxelgameslib.api.bossbar;

/**
 * Represents a modifier for that world that is in action when it is added to a boss bar
 */
public enum BossBarModifier {
    
    /**
     * Effect that darkens the sky. Vanilla displays this while fighting the wither
     */
    DARKEN_SKY,
    /**
     * Effect that players the boss music. Vanilla plays this while fighting the enderdragon
     */
    PLAY_BOSS_MUSIC,
    /**
     * Creates fog around the world.
     */
    CREATE_FOG,
}
