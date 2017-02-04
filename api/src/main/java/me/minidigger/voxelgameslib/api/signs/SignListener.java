package me.minidigger.voxelgameslib.api.signs;

import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.block.metadata.SignMetaData;
import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.block.BlockBreakEvent;
import me.minidigger.voxelgameslib.api.event.events.sign.SignUpdateEvent;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.Role;

import lombok.extern.java.Log;

@Log
@EventListener
@SuppressWarnings("JavaDoc")
public class SignListener {

    private Permission placeHolderSignPlace = Permission.register("placeholdersign.place", Role.MODERATOR);
    private Permission placeHolderSignBreak = Permission.register("placeholdersign.break", Role.MODERATOR);

    @Inject
    private SignHandler signHandler;

    @EventListener
    public void signUpdate(SignUpdateEvent event) {
        for (int i = 0; i < event.getText().length; i++) {
            String line = event.getText()[i];
            for (String key : signHandler.getPlaceHolders().keySet()) {
                if (line.contains("%" + key + "%")) {
                    // we got a sign with a placeholder, first check if a user placed it and if he is allowed to do that
                    if (event.getUser().isPresent()) {
                        if (!event.getUser().get().hasPermission(placeHolderSignPlace)) {
                            Lang.msg(event.getUser().get(), LangKey.SIGNS_PLACE_NO_PERM, key, placeHolderSignBreak.getRole().getName());
                            return;
                        } else {
                            Lang.msg(event.getUser().get(), LangKey.SIGNS_PLACE_SUCCESS, key);
                            signHandler.addSign(event.getLocation(), event.getWorld());
                        }
                    }

                    SignPlaceHolder placeHolder = signHandler.getPlaceHolders().get(key);
                    if (placeHolder instanceof SimpleSignPlaceHolder) {
                        line = line.replace("%" + key + "%", ((SimpleSignPlaceHolder) placeHolder).apply(event, key));
                    } else if (placeHolder instanceof FullSignPlaceHolder) {
                        event.setText(((FullSignPlaceHolder) placeHolder).apply(event, key));
                        return;
                    } else {
                        log.warning("Unknown SignPlaceHolder type " + placeHolder.getClass().getName() + " with key " + key);
                    }
                }
            }
            event.getText()[i] = line;
        }
    }

    @EventListener
    public void signBreakEvent(BlockBreakEvent event) {
        // is block a sign?
        if (event.getBlock().getMetaData() instanceof SignMetaData) {
            SignMetaData metaData = (SignMetaData) event.getBlock().getMetaData();
            // has sign a placeholder?
            for (int i = 0; i < metaData.getLines().length; i++) {
                String line = metaData.getLines()[i];
                for (String key : signHandler.getPlaceHolders().keySet()) {
                    if (line.contains("%" + key + "%")) {
                        // has user permission for that?
                        if (event.getUser().hasPermission(placeHolderSignBreak)) {
                            Lang.msg(event.getUser(), LangKey.SIGNS_BREAK_SUCCESS, key);
                            signHandler.removeSign(event.getBlock());
                            return;
                        } else {
                            event.setCanceled(true);
                            Lang.msg(event.getUser(), LangKey.SIGNS_BREAK_NO_PERM, key, placeHolderSignBreak.getRole().getName());
                            return;
                        }
                    }
                }
            }
        }
    }
}
