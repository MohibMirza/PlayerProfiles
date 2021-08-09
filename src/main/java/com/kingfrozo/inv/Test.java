package com.kingfrozo.inv;

import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RedisClient;
import org.redisson.api.RLiveObjectService;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        RedisClient redis = new RedisClient("redis://127.0.0.1:6379");
        redis.loadConfig("config.yml");
        redis.start();

        RLiveObjectService service = redis.redisson.getLiveObjectService();

        if(!service.isClassRegistered(RPlayer.class)) {
            System.out.println("RPlayer not registered to Redisson RLO!");
            service.registerClass(RPlayer.class);
            System.out.println("Registered RPlayer on RLO.");
        }

        RPlayer player = new RPlayer("1", "Nazam", "Admin", 421, "diamond__axe");
        // service.persist(player);

        RPlayer livePlayer = service.get(RPlayer.class, "1");

        System.out.println(livePlayer.getTitle());

        redis.shutdown();
    }
}
