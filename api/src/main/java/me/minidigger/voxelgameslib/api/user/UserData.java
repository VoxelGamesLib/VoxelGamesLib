package me.minidigger.voxelgameslib.api.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.persistence.PersistenceHandler;
import me.minidigger.voxelgameslib.api.role.Role;

import jskills.Rating;
import lombok.Getter;
import lombok.Setter;

/**
 * The data for a user
 */
@Entity
@Getter
@Setter
public class UserData implements Serializable {

    @Inject
    @Transient
    private PersistenceHandler persistenceHandler;

    @Transient
    private UUID uuid;

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Role role = Role.DEFAULT;

    @OneToOne
    @JoinColumn(name = "LOCALE_TAG")
    private Locale locale = Locale.ENGLISH;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Map<String, Rating> ratings = new HashMap<>();

    private String displayName = "";

    private String prefix = "{\"text\":\"\"}";
    private String suffix = "{\"text\":\"\"}";

    /**
     * Creates a new userdata object
     *
     * @param id the uuid of the user
     */
    public UserData(UUID id) {
        this.uuid = id;
        this.id = id.toString();
    }

    protected UserData() {
        // JPA
    }

    /**
     * @param mode the mode to get the rating for
     * @return the rating of this player for gamemode mode. will return default values if not
     * present
     */
    public Rating getRating(me.minidigger.voxelgameslib.api.game.GameMode mode) {
        Rating rating = ratings.get(mode.getName());
        if (rating == null) {
            rating = mode.getRatingInfo().getDefaultRating();
            // no need to save here
        }
        return rating;
    }

    /**
     * Saves a rating for this users. will override existing ratings
     *
     * @param mode   the mode the rating was achieved in
     * @param rating the new rating
     */
    public void saveRating(me.minidigger.voxelgameslib.api.game.GameMode mode, Rating rating) {
        ratings.put(mode.getName(), rating);
        persistenceHandler.getProvider().saveUserData(this);
    }

    /**
     * @return all ratings for this player
     */
    public Map<String, Rating> getRatings() {
        return ratings;
    }
}
