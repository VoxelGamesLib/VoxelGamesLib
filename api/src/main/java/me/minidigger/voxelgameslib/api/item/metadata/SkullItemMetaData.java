package me.minidigger.voxelgameslib.api.item.metadata;

/**
 * Meta data wrapper for skulls
 */
public interface SkullItemMetaData extends ItemMetaData {

    /**
     * @return if this skull has an owner
     */
    boolean hasOwner();

    /**
     * changes the owner of the given skull
     *
     * @param name the new owner
     */
    void setOwner(String name);

    /**
     * @return the owner of the giving skull
     */
    String getOwner();

    /**
     * @return the type of this skull
     */
    SkullType getType();

    /**
     * changes the type of this skull
     *
     * @param type the new type
     */
    void setType(SkullType type);

    /**
     * Represents the type of a skull
     */
    enum SkullType {
        SKELETON(0),
        WITHER(1),
        ZOMBIE(2),
        PLAYER(3),
        CREEPER(4),
        DRAGON(5);

        private int id;

        SkullType(int id) {
            this.id = id;
        }

        /**
         * @return the implementation id
         */
        public int getId() {
            return id;
        }

        /**
         * gets the skull type that corresponds to the given id
         *
         * @param id the id to search for
         * @return the found skull type
         * @throws IllegalArgumentException if there is no
         */
        public static SkullType valueOf(int id) {
            for (SkullType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Unknown SkullType " + id);
        }
    }
}
