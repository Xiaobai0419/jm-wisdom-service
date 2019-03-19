package com.sunfield.microframe.common.utils;

import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.domain.JmIndustries;
import com.sunfield.microframe.feign.JmAppUserFeignService;
import com.sunfield.microframe.feign.JmIndustriesFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class CacheUtils {//缓存操作Bean,谁需要谁注入

    private AtomicBoolean cacheInRedis = new AtomicBoolean(false);
    public boolean getCacheInRedis() {
        return cacheInRedis.get();
    }
    public void setCacheInRedis(boolean cacheInRedis) {
        this.cacheInRedis.set(cacheInRedis);
    }

    //应用层缓存--用户量大的情形下要移除到Redis集群中，防止应用层内存溢出;每次从Redis获取数据（但又变成了网络连接）;使用单独定时任务工程查询所有用户更新到Redis缓存
    private ConcurrentMap<String,JmAppUser> userCache = new ConcurrentHashMap<>();
    private ConcurrentMap<String, JmIndustries> industriesCache = new ConcurrentHashMap<>();
    public ConcurrentMap<String, JmAppUser> getUserCache() {
        return userCache;
    }
    public ConcurrentMap<String, JmIndustries> getIndustriesCache() {
        return industriesCache;
    }

    //Redis缓存操作
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;//这个RedisTemplate<String, Object>是在RedisConfig里使用@Bean指定的，并指定序列化方式是Jackson，别的类型的不会自动创建，需要自己@Bean实例化
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }
    private static final String USER_CACHE_KEY = "jm_users_cache";
    private static final String INDUSTRY_CACHE_KEY = "jm_industries_cache";
    public static String getUserCacheKey() {
        return USER_CACHE_KEY;
    }
    public static String getIndustryCacheKey() {
        return INDUSTRY_CACHE_KEY;
    }

    //定时任务按缓存方式定期查询数据更新缓存，用于一定时间后重新获取新数据--这时保证单例和单例实例化的同步操作就非常有必要
    public CacheUtils() {
        if(!getCacheInRedis()) {
            Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<JmAppUser> users = findUsers();//远程调用有超时、熔断设置，且这里是另起一个线程异步，不用担心因容器初始化Bean未完成导致的服务长期起不来
                        List<JmIndustries> industries = findIndustries();
                        if(users != null && users.size() > 0) {
                            for(JmAppUser user : users) {
                                userCache.put(user.getId(),user);//无论是否并发，操作ConcurrentMap是安全的，能不同步尽量不同步，无论整体存入是否完成，数据获取者只是并发获取其中的一些数据而已，上一次有旧数据未被覆盖，可用，如果没有，降级，自己去远程调用
                            }
                        }
                        if(industries != null && industries.size() > 0) {
                            for(JmIndustries jmIndustries : industries) {
                                industriesCache.put(jmIndustries.getId(),jmIndustries);
                            }
                        }
                        log.info(Thread.currentThread().getName() + "->userCache and industryCache updated!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },2000,20000, TimeUnit.MILLISECONDS);
        }else {
            Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<JmAppUser> users = findUsers();//远程调用有超时、熔断设置，且这里是另起一个线程异步，不用担心因容器初始化Bean未完成导致的服务长期起不来
                        List<JmIndustries> industries = findIndustries();
                        if(users != null && users.size() > 0) {
                            for(JmAppUser user : users) {//单独线程循环跑Redis缓存同步没问题，不需要额外应用层缓存;Redis为单线程模型，这里即使有并发，Redis端也线程安全;不需要整体同步，Redis有旧数据，如没有，让数据获取者降级为直接远程调用
                                HashOperations<String, String, JmAppUser> hashOperations = redisTemplate.opsForHash();
                                hashOperations.put(USER_CACHE_KEY,user.getId(),user);
                            }
                        }
                        if(industries != null && industries.size() > 0) {
                            for(JmIndustries jmIndustries : industries) {
                                HashOperations<String, String, JmIndustries> hashOperations = redisTemplate.opsForHash();
                                hashOperations.put(INDUSTRY_CACHE_KEY,jmIndustries.getId(),jmIndustries);
                            }
                        }
                        log.info(Thread.currentThread().getName() + "->userCache and industryCache updated in Redis!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },2000,20000, TimeUnit.MILLISECONDS);
        }
    }

    //远程调用客户端
    @Autowired
    @Qualifier("jmAppUserFeignService")
    private JmAppUserFeignService jmAppUserFeignService;
    @Autowired
    @Qualifier("jmIndustriesFeignService")
    private JmIndustriesFeignService jmIndustriesFeignService;

    //缓存所有用户--改为定时任务查询最新存入应用层缓存，这里要最新数据，不能缓存
//	@Cacheable(value = "jm_wisdom_user_cache",key = "'jm_wisdom_user_cache'")
    public List<JmAppUser> findUsers() {
        return jmAppUserFeignService.findList().getData();
    }

    //缓存所有行业--改为定时任务查询最新存入应用层缓存，这里要最新数据，不能缓存
//	@Cacheable(value = "jm_wisdom_industry_cache",key = "'jm_wisdom_industry_cache'")
    public List<JmIndustries> findIndustries() {
        return jmIndustriesFeignService.findList(new JmIndustries()).getData();
    }
}
