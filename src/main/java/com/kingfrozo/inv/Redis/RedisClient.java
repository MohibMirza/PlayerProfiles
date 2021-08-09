package com.kingfrozo.inv.Redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.REntity;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RedisClient {

    public RedissonClient redisson;

    private String address;
    private Config config;


    public RedisClient(String address) {
        this.address = address;
        config = new Config();
        config.useSingleServer().setAddress(address);

        System.out.println("Instantiated Redis Client");
    }

    public void start() {
        redisson = Redisson.create(config);
        System.out.println("Redis Client Starting Up");
    }

    public void shutdown() {
        if(redisson.isShutdown()){
            System.out.println("Redis Client is already shut down...");
            return;
        }
        redisson.shutdown();
        System.out.println("Redis Client Shutting Down");
    }

    public boolean isRunning() {
        return (redisson.isShutdown());
    }

    public void loadConfig(String fileName) throws IOException {
        String content = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
        config = Config.fromJSON(content);
        config.useSingleServer().setAddress(address);
    }

//    public void writeOutConfig(String fileName) throws IOException {
//        Main.writeToFile("config.yml", config.toJSON());
//    }







}
