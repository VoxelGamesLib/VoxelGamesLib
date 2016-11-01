package me.minidigger.voxelgameslib.api.lang;

import java.util.Optional;
import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Martin on 09.10.2016.
 */
@Data
@AllArgsConstructor
public class Locale {
    
    public static final Locale ENGLISH = new Locale("english", "en");
    public static final Locale FRENCH = new Locale("french", "fr");
    public static final Locale GERMAN = new Locale("german", "de");
    public static final Locale ITALIAN = new Locale("italian", "it");
    public static final Locale PORTUGUESE = new Locale("portuguese", "pt");
    public static final Locale RUSSIAN = new Locale("russian", "ru");
    public static final Locale SPANISH = new Locale("spanish", "es");
    public static final Locale SWEDISH = new Locale("swedish", "sv");
    public static final Locale TURKISH = new Locale("turkish", "tr");
    
    /**
     * @return all known locales
     */
    @Nonnull
    public static Locale[] values() {
        return new Locale[]{ENGLISH, FRENCH, GERMAN, ITALIAN, PORTUGUESE, RUSSIAN, SPANISH, SWEDISH, TURKISH};
    }
    
    /**
     * Searches for a locale with that tag
     *
     * @param tag the tag to search for
     * @return a optionally found locale
     */
    @Nonnull
    public static Optional<Locale> fromTag(@Nonnull String tag) {
        for (Locale loc : values()) {
            if (loc.getTag().equalsIgnoreCase(tag)) {
                return Optional.of(loc);
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Searches for a locale with that name
     *
     * @param name the name to search for
     * @return a optionally found locale
     */
    @Nonnull
    public static Optional<Locale> fromName(@Nonnull String name) {
        for (Locale loc : values()) {
            if (loc.getName().equalsIgnoreCase(name)) {
                return Optional.of(loc);
            }
        }
        
        return Optional.empty();
    }
    
    private String name;
    private String tag;
}
