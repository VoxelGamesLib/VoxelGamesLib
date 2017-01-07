package me.minidigger.voxelgameslib.api.scoreboard;

/**
 * Created by Martin on 07.01.2017.
 */
public class StringScoreboardLine implements ScoreboardLine {
    
    private String value;
    
    public StringScoreboardLine(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return value;
    }
}
