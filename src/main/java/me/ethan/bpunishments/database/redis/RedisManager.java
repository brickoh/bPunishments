package me.ethan.bpunishments.database.redis;

import com.google.gson.JsonObject;
import lombok.Getter;
import me.ethan.bpunishments.database.redis.impl.Payload;
import me.ethan.bpunishments.database.redis.impl.RedisListener;
import me.ethan.bpunishments.database.redis.other.JedisPublisher;
import me.ethan.bpunishments.database.redis.other.JedisSubscriber;
import redis.clients.jedis.JedisPool;

@Getter
public class RedisManager {

    private final JedisPool pool;
    private final JedisPublisher publisher;
    private final JedisSubscriber subscriber;
    private final boolean connected = true;
    private final RedisListener redisListener;

    public RedisManager() {
        this.redisListener = new RedisListener();
        this.pool = new JedisPool("127.0.0.1", 6379);
        this.publisher = new JedisPublisher();
        this.subscriber = new JedisSubscriber("bpunishments", new RedisListener());
    }


    public boolean isActive() {
        return !this.pool.isClosed();
    }

    public void write(Payload payload, JsonObject data) {
        if (!isConnected() || !isActive()) {
            JsonObject object = new JsonObject();
            object.addProperty("payload", payload.name());
            object.add("data", data == null ? new JsonObject() : data);
            redisListener.handleMessage(object);
            return;
        }
        JsonObject object = new JsonObject();
        object.addProperty("payload", payload.name());
        object.add("data", data == null ? new JsonObject() : data);
        this.publisher.write("bsite", object);
    }
}