package com.ssafy.team8alette.Redis.Controller;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // set
    @PostMapping("")
    public String setRedisKey(@RequestBody Map<String, String> req) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        try {
            // Redis Set Key-value
            System.out.println(req.get("key") + " // " + req.get("value"));
            vop.set(req.get("key"), req.get("value"));
            return "set message success";
        } catch (Exception e) {
            e.printStackTrace();
            return "set message fail";
        }
    }

    // get
    @GetMapping("/{key}")
    public String getRedisKey(@PathVariable String key) {
        System.out.println(key);
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        return vop.get(key);
    }

}