package me.minidigger.voxelgameslib.api.user;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.role.Role;

/**
 * The data for a user
 */
@Entity
public class UserData {

    @Transient
    private UUID uuid;

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Role role = Role.DEFAULT;

    @OneToOne
    @JoinColumn(name = "LOCALE_TAG")
    private Locale locale = Locale.ENGLISH;

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
}
