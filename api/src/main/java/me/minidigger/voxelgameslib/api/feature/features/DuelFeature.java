package me.minidigger.voxelgameslib.api.feature.features;

import java.util.concurrent.ThreadLocalRandom;

import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;

/**
 * Feature that handles dueling.
 */
public class DuelFeature extends AbstractFeature {

    private User one;
    private User two;

    /**
     * @return the first user
     */
    public User getOne() {
        return one;
    }

    /**
     * @return the second user
     */
    public User getTwo() {
        return two;
    }

    /**
     * @param user the user to not return
     * @return the user that is not user
     */
    public User getOther(User user) {
        if (user.equals(one)) {
            return two;
        } else if (user.equals(two)) {
            return one;
        }
        throw new IllegalArgumentException(ChatUtil.toPlainText(user.getDisplayName()) + " is neither one nor two");
    }

    @Override
    public void start() {
        if (getPhase().getGame().getPlayers().size() != 2) {
            getPhase().getGame().broadcastMessage(LangKey.DUEL_WRONG_PLAYER_COUNT, getPhase().getGame().getPlayers().size());
            getPhase().getGame().abortGame();
            return;
        }

        one = getPhase().getGame().getPlayers().get(0);
        two = getPhase().getGame().getPlayers().get(1);

        if (ThreadLocalRandom.current().nextBoolean()) {
            User temp = one;
            one = two;
            two = temp;
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

    @Override
    public Class[] getDependencies() {
        return new Class[0];
    }
}
