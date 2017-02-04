package me.minidigger.voxelgameslib.api.block.metadata;

/**
 * Metadata wrapper for signs
 */
public interface SignMetaData extends BlockMetaData {

    /**
     * @return the lines of the sign
     */
    String[] getLines();

    /**
     * @param i the position of the line
     * @return line number i
     */
    String getLine(int i);

    /**
     * Sets one line of this sign
     *
     * @param line the line to set
     * @param i    the number of the line
     */
    void setLine(String line, int i);

    /**
     * sets the lines of this sign
     *
     * @param lines the new lines
     */
    void setLines(String[] lines);
}
