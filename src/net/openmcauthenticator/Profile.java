package net.openmcauthenticator;

import java.io.Serializable;
import java.util.UUID;

public class Profile implements Serializable {

    private UUID uuid;
    private String name;
    private boolean legacy;

    public Profile(UUID uuid, String name, boolean legacy) {
        this.uuid = uuid;
        this.name = name;
        this.legacy = legacy;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public boolean isLegacy() {
        return legacy;
    }

}
