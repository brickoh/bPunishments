package me.ethan.bpunishments.utils.name;

import java.util.Arrays;
import java.util.UUID;

public class NameUtils {

    public static String getName(UUID uuid) {
        NameFetcher fetcher = new NameFetcher(Arrays.asList(uuid));
        try {
            return fetcher.call().get(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
