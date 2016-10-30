package me.minidigger.voxelgameslib.api.phase;

/**
 * A special {@link Phase} that automatically ends after a specified amount of ticks.
 */
public abstract class TimedPhase extends AbstractPhase {
    
    private int ticks;
    
    /**
     * Sets the amount of ticks this phase should tick, can be modified after start
     *
     * @param ticks the amount of ticks this phase should tick
     */
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
    
    /**
     * @return the amount of ticks this phase will go on for
     */
    public int getTicks() {
        return ticks;
    }
    
    @Override
    public void tick() {
        super.tick();
        ticks--;
        
        if (ticks <= 0) {
            getGame().endPhase();
        }
    }
}
