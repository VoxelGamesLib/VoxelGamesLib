package me.minidigger.voxelgameslib.splatoon;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.block.Direction;
import me.minidigger.voxelgameslib.api.block.metadata.ColorMetaData;
import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.user.UserInteractEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.features.MapFeature;
import me.minidigger.voxelgameslib.api.feature.features.ScoreboardFeature;
import me.minidigger.voxelgameslib.api.feature.features.TeamFeature;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;
import me.minidigger.voxelgameslib.api.scoreboard.StringScoreboardLine;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.team.Team;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.utils.DirectionUtil;
import me.minidigger.voxelgameslib.api.world.World;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;

import lombok.extern.java.Log;

/**
 * Created by Martin on 05.02.2017.
 */
@Log
public class SplatoonFeature extends AbstractFeature {

    private Map<Vector3D, ChatColor> colors = new HashMap<>();
    private Map<UUID, Weapon> weapons = new HashMap<>();
    private Scoreboard scoreboard;

    private World world;

    private int greenCounter = 10;
    private int purpleCounter = 10;

    private StringScoreboardLine greenLine;
    private StringScoreboardLine purple;

    @Inject
    private Server server;


    @Override
    public void start() {
        System.out.println("start");
        // scoreboard
        scoreboard = getPhase().getFeature(ScoreboardFeature.class).getScoreboard();

        scoreboard.setTitle("Splatoon");
        scoreboard.addLine("teamNameGreen", new StringScoreboardLine(ChatColor.GREEN + "Green"));
        scoreboard.addLine("blocksGreen", greenLine = new StringScoreboardLine(ChatColor.GREEN + "50%"));

        scoreboard.addLine("teamNamePurple", new StringScoreboardLine(ChatColor.LIGHT_PURPLE + "Purple"));
        scoreboard.addLine("blocksPurple", purple = new StringScoreboardLine(ChatColor.LIGHT_PURPLE + "50%"));

        // load world
        Optional<World> world = server.getWorld(getPhase().getFeature(MapFeature.class).getMap().getWorldName());
        if (!world.isPresent()) {
            getPhase().getGame().abortGame();
            return;
        }
        this.world = world.get();

        // weapons
        //TODO select weapons
        for (User user : getPhase().getGame().getPlayers()) {
            System.out.println(user.getData().getDisplayName() + " has a splat roller now");
            weapons.put(user.getUuid(), Weapon.SPLAT_ROLLER);
        }

    }

    @Override
    public void stop() {
        colors.clear();
    }

    @Override
    public void tick() {
        for (User user : getPhase().getGame().getPlayers()) {
            if (weapons.get(user.getUuid()) == Weapon.SPLAT_ROLLER) {
                Direction dir = user.getFacingDirection();
                Direction left = DirectionUtil.getNext(dir, -2);
                Direction right = DirectionUtil.getNext(dir, 2);

                // one down and one ahead
                Block orig = world.getBlockAt(user.getLocation()).getRelative(Direction.DOWN).getRelative(dir);
                paint(user, orig, orig.getRelative(left), orig.getRelative(right));
            }
        }

        double green = ((greenCounter + purpleCounter) * 100) / greenCounter;
        double purple = ((greenCounter + purpleCounter) * 100) / purpleCounter;

        System.out.println("green: " + green + " purple: " + purple);
        // TODO update scoreboard
    }

    private void paint(User user, Block... blocks) {
        Optional<Team> team = getPhase().getFeature(TeamFeature.class).getTeam(user);
        if (!team.isPresent()) {
            log.warning(user.getData().getDisplayName() + " has no team?!");
            return;
        }

        for (Block block : blocks) {
            System.out.println(block.getMaterial().name() + " " + block.getLocation().toString());
            if (block.getMaterial() == Material.WOOL) {
                ColorMetaData metaData = (ColorMetaData) block.getMetaData();
                // update score
                if (team.get().getColor() == ChatColor.GREEN) {
                    if (metaData.getColor() == ChatColor.LIGHT_PURPLE) {
                        greenCounter++;
                        purpleCounter--;
                    } else {
                        greenCounter++;
                    }
                } else {
                    if (metaData.getColor() == ChatColor.GREEN) {
                        purpleCounter++;
                        greenCounter--;
                    } else {
                        purpleCounter++;
                    }
                }

                // set block
                metaData.setColor(team.get().getColor());
                metaData.update();
                colors.put(block.getLocation(), team.get().getColor());
            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public Class[] getDependencies() {
        return new Class[]{ScoreboardFeature.class, MapFeature.class, TeamFeature.class};
    }

    @EventListener
    public void onInteract(UserInteractEvent event) {

    }

    enum Weapon {
        SPLATTER_SHOT,
        SPLAT_CHARGER,
        SPLAT_ROLLER
    }
}
