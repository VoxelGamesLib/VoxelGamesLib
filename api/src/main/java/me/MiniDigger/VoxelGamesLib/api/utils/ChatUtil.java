package me.MiniDigger.VoxelGamesLib.api.utils;

import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.BaseComponent;

/**
 * Created by Martin on 13.10.2016.
 */
public class ChatUtil {
    
    public static String toPlainText(BaseComponent... comps) {
        StringBuilder sb = new StringBuilder();
        for (BaseComponent comp : comps) {
            sb.append(comp.toPlainText());
        }
        return sb.toString();
    }
}
