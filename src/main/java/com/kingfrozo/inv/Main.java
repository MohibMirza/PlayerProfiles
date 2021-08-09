package com.kingfrozo.inv;

import com.kingfrozo.inv.RClasses.RPlayer;
import com.kingfrozo.inv.Redis.RedisClient;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.api.RLiveObjectService;

import java.io.IOException;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static RedisClient redis;
    private static RLiveObjectService rlo;



    @Override
    public void onEnable() {
        plugin = this;
        connectToRedis();


    }

    @Override
    public void onDisable() {
        disconnectRedis();


    }

    public static Main getPlugin() {
        return plugin;
    }

    private static void connectToRedis() {
        redis = new RedisClient("redis://127.0.0.1:6379");

        try {
            redis.loadConfig("config.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        redis.start();

        rlo = redis.redisson.getLiveObjectService();

        if(!rlo.isClassRegistered(RPlayer.class)) {
            System.out.println("RPlayer not registered to Redisson RLO!");
            rlo.registerClass(RPlayer.class);
            System.out.println("Registered RPlayer on RLO.");
        }






    }

    private static void disconnectRedis(){
        redis.shutdown();
    }

}
