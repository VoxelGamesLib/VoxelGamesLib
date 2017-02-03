package me.minidigger.voxelgameslib.api.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.persistence.PersistenceHandler;
import me.minidigger.voxelgameslib.api.role.Role;

import jskills.Rating;

/**
 * The data for a user
 */
@Entity
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, Rating> ratings = new HashMap<>();

    public UserData(UUID id) {
        this.uuid = id;
        this.id = id.toString();
    }

    protected UserData() {
        // JPA
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
        this.id = uuid.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.uuid = UUID.fromString(id);
    }

    /**
     * @return the {@link Role} the user is assigned to
     */
    @Nonnull
    public Role getRole() {
        return role;
    }

    /**
     * updates the role of this user
     *
     * @param role the new role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the locale this player wishes to receive messages in
     */
    @Nonnull
    public Locale getLocale() {
        return locale;
    }


    /**
     * sets the preferred locale of this user
     *
     * @param locale the preferred locale of this user
     */
    public void setLocale(@Nonnull Locale locale) {
        this.locale = locale;
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
