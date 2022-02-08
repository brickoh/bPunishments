package me.ethan.bpunishments.database.redis.other;

import com.google.gson.JsonObject;
import me.ethan.bpunishments.bPunishments;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPublisher {

    public void write(String channel, JsonObject payload) {
        JedisPool pool = bPunishments.getInstance().getRedisManager().getPool();
        if (pool == null) return;

        Jedis jedis = null;

        try {
            jedis = bPunishments.getInstance().getRedisManager().getPool().getResource();
            jedis.publish(channel, payload.toString());
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }
}

