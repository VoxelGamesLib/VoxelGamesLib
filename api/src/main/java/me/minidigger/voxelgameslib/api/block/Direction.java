package me.minidigger.voxelgameslib.api.block;

/**
 * Represents the direction things can point in
 */
public enum Direction {

    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    SOUTH(0, 0, 1),
    WEST(-1, 0, 0),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    NORTH_EAST(NORTH, EAST),
    NORTH_WEST(NORTH, WEST),
    SOUTH_EAST(SOUTH, EAST),
    SOUTH_WEST(SOUTH, WEST),
    WEST_NORTH_WEST(WEST, NORTH_WEST),
    NORTH_NORTH_WEST(NORTH, NORTH_WEST),
    NORTH_NORTH_EAST(NORTH, NORTH_EAST),
    EAST_NORTH_EAST(EAST, NORTH_EAST),
    EAST_SOUTH_EAST(EAST, SOUTH_EAST),
    SOUTH_SOUTH_EAST(SOUTH, SOUTH_EAST),
    SOUTH_SOUTH_WEST(SOUTH, SOUTH_WEST),
    WEST_SOUTH_WEST(WEST, SOUTH_WEST),
    SELF(0, 0, 0);

    private final int modX;
    private final int modY;
    private final int modZ;

    Direction(int modX, int modY, int modZ) {
        this.modX = modX;
        this.modY = modY;
        this.modZ = modZ;
    }

    Direction(Direction face1, Direction face2) {
        this.modX = face1.getModX() + face2.getModX();
        this.modY = face1.getModY() + face2.getModY();
        this.modZ = face1.getModZ() + face2.getModZ();
    }

    /**
     * Get the amount of X-coordinates to modify to point in the specified direction
     *
     * @return Amount of X-coordinates to modify
     */
    public int getModX() {
        return modX;
    }

    /**
     * Get the amount of Y-coordinates to modify to point in the specified direction
     *
     * @return Amount of Y-coordinates to modify
     */
    public int getModY() {
        return modY;
    }

    /**
     * Get the amount of Z-coordinates to modify to point in the specified direction
     *
     * @return Amount of Z-coordinates to modify
     */
    public int getModZ() {
        return modZ;
    }

    /**
     * Return the direction that is rotated by 180 degree
     *
     * @return The direction that is rotated by 180 degree
     */
    public Direction getOppositeFace() {
        switch (this) {
            case NORTH:
                return Direction.SOUTH;

            case SOUTH:
                return Direction.NORTH;

            case EAST:
                return Direction.WEST;

            case WEST:
                return Direction.EAST;

            case UP:
                return Direction.DOWN;

            case DOWN:
                return Direction.UP;

            case NORTH_EAST:
                return Direction.SOUTH_WEST;

            case NORTH_WEST:
                return Direction.SOUTH_EAST;

            case SOUTH_EAST:
                return Direction.NORTH_WEST;

            case SOUTH_WEST:
                return Direction.NORTH_EAST;

            case WEST_NORTH_WEST:
                return Direction.EAST_SOUTH_EAST;

            case NORTH_NORTH_WEST:
                return Direction.SOUTH_SOUTH_EAST;

            case NORTH_NORTH_EAST:
                return Direction.SOUTH_SOUTH_WEST;

            case EAST_NORTH_EAST:
                return Direction.WEST_SOUTH_WEST;

            case EAST_SOUTH_EAST:
                return Direction.WEST_NORTH_WEST;

            case SOUTH_SOUTH_EAST:
                return Direction.NORTH_NORTH_WEST;

            case SOUTH_SOUTH_WEST:
                return Direction.NORTH_NORTH_EAST;

            case WEST_SOUTH_WEST:
                return Direction.EAST_NORTH_EAST;

            case SELF:
                return Direction.SELF;

            default:
                return Direction.SELF;
        }
    }
}
