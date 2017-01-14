package me.minidigger.voxelgameslib.bukkit.scoreboard;

import java.util.Optional;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.scoreboard.AbstractScoreboard;
import me.minidigger.voxelgameslib.api.scoreboard.ScoreboardLine;
import me.minidigger.voxelgameslib.api.user.User;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Created by Martin on 07.01.2017.
 */
public class BukkitScoreboard extends AbstractScoreboard {

    private Scoreboard scoreboard;
    private Objective objective;

    @Override
    public void setImplObject(Object object) {
        scoreboard = (Scoreboard) object;
        objective = scoreboard.registerNewObjective("VoxelGamesLib", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @Nonnull
    @Override
    public String getTitle() {
        return objective.getDisplayName();
    }

    @Override
    public void setTitle(@Nonnull String title) {
        objective.setDisplayName(title);
    }

    @Override
    public void addLine(int key, @Nonnull ScoreboardLine line) {
        super.addLine(key, line);

        objective.getScore(line.getValue()).setScore(key);
    }

    @Override
    public void removeLine(int key) {
        Optional<ScoreboardLine> line = getLine(key);
        if (!line.isPresent()) {
            super.removeLine(key);
            return;
        }

        scoreboard.resetScores(line.get().getValue());

        super.removeLine(key);
    }

    @Override
    public void addUser(@Nonnull User user) {
        super.addUser(user);

        ((Player) user.getImplementationType()).setScoreboard(scoreboard);
    }

    @Override
    public void removeUser(@Nonnull User user) {
        super.removeUser(user);

        ((Player) user.getImplementationType()).setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
