package me.minidigger.voxelgameslib.onevsone;

import com.google.inject.Injector;

import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.user.UserDeathEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.features.DuelFeature;
import me.minidigger.voxelgameslib.api.item.Hand;
import me.minidigger.voxelgameslib.api.item.ItemBuilder;
import me.minidigger.voxelgameslib.api.item.Material;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;

import jskills.Rating;
import lombok.extern.java.Log;

/**
 * Created by Martin on 28.01.2017.
 */
@Log
public class OneVsOneFeature extends AbstractFeature {

    @Inject
    private Injector injector;

    @Override
    public void start() {
        DuelFeature duelFeature = getPhase().getFeature(DuelFeature.class);
        Rating r1 = duelFeature.getOne().getRating(getPhase().getGame().getGameMode());
        Rating r2 = duelFeature.getTwo().getRating(getPhase().getGame().getGameMode());

        log.info(ChatUtil.toPlainText(duelFeature.getOne().getDisplayName()) + "[" +
                r1.getMean() + "(" + r1.getStandardDeviation() + ")] duels "
                + ChatUtil.toPlainText(duelFeature.getTwo().getDisplayName()) + "[" +
                r2.getMean() + "(" + r2.getStandardDeviation() + ")]");

        // TODO better items, lol
        for (User user : new User[]{duelFeature.getOne(), duelFeature.getTwo()}) {
            user.setItemInHand(Hand.MAINHAND, new ItemBuilder(Material.DIAMOND_SWORD, injector).build());
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {

    }

    @EventListener
    public void onDeath(UserDeathEvent e) {
        if (getPhase().getGame().isPlaying(e.getUser())) {
            DuelFeature duelFeature = getPhase().getFeature(DuelFeature.class);
            getPhase().getGame().endGame(null, duelFeature.getOther(e.getUser()));
        }
    }

    @Override
    public Class[] getDependencies() {
        return new Class[]{DuelFeature.class};
    }
}
