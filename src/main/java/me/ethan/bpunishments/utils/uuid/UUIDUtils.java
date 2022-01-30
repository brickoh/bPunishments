package me.ethan.bpunishments.utils.uuid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UUIDUtils {

    private static final Map<String, UUID> PLAYER_UUIDS = new HashMap<>();

    public static UUID getUUID(String player) {
        if (getUUIDs().containsKey(player)) {
            return getUUIDs().get(player);
        } else {
            UUIDFetcher fetcher = new UUIDFetcher(Arrays.asList(player));
            try {
                return fetcher.call().get(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static Map<String, UUID> getUUIDs() {
        return PLAYER_UUIDS;
    }
}

