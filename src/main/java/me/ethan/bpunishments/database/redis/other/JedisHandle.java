package me.ethan.bpunishments.database.redis.other;

import com.google.gson.JsonObject;

public interface JedisHandle {

    void handleMessage(JsonObject object);

}