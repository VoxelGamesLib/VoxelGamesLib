package me.minidigger.voxelgameslib.bukkit.util;

import java.util.EnumMap;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.utils.MathUtil;

import org.bukkit.block.BlockFace;

/**
 * Small util for BlockFace <-> yaw conversation <br> https://github.com/bergerkiller/BKCommonLib/blob/master/src/main/java/com/bergerkiller/bukkit/common/utils/FaceUtil.java
 */
public class FaceUtil {
    
    public static final BlockFace[] AXIS = new BlockFace[4];
    public static final BlockFace[] RADIAL = {BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST};
    private static final EnumMap<BlockFace, Integer> notches = new EnumMap<>(BlockFace.class);
    
    static {
        for (int i = 0; i < RADIAL.length; i++) {
            notches.put(RADIAL[i], i);
        }
        for (int i = 0; i < AXIS.length; i++) {
            AXIS[i] = RADIAL[i << 1];
        }
    }
    
    /**
     * Gets the Notch integer representation of a BlockFace<br>
     * <b>These are the horizontal faces, which exclude up and down</b>
     *
     * @param face to get
     * @return Notch of the face
     */
    public static int faceToNotch(@Nonnull BlockFace face) {
        Integer notch = notches.get(face);
        return notch == null ? 0 : notch;
    }
    
    /**
     * Gets the angle from a horizontal Block Face
     *
     * @param face to get the angle for
     * @return face angle
     */
    public static int faceToYaw(@Nonnull BlockFace face) {
        return MathUtil.wrapAngle(45 * faceToNotch(face));
    }
    
    /**
     * Gets the horizontal Block Face from a given yaw angle<br>
     * This includes the NORTH_WEST faces
     *
     * @param yaw angle
     * @return The Block Face of the angle
     */
    @Nonnull
    public static BlockFace yawToFace(float yaw) {
        return yawToFace(yaw, true);
    }
    
    /**
     * Gets the horizontal Block Face from a given yaw angle
     *
     * @param yaw                      angle
     * @param useSubCardinalDirections setting, True to allow NORTH_WEST to be returned
     * @return The Block Face of the angle
     */
    @Nonnull
    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections) {
            return RADIAL[Math.round(yaw / 45f) & 0x7];
        } else {
            return AXIS[Math.round(yaw / 90f) & 0x3];
        }
    }
}
