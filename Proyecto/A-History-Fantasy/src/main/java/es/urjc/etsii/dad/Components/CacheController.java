package es.urjc.etsii.dad.Components;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hazelcast.spring.cache.HazelcastCache;
import com.hazelcast.spring.cache.HazelcastCacheManager;


@RestController
public class CacheController {

    @Autowired
    private CacheManager cacheManager;
    
    @RequestMapping(value = "/cacheUsuarios")
    public Map<Object, Object> getCacheUserContent() {
        HazelcastCacheManager hazelcastCacheManager = (HazelcastCacheManager) cacheManager;
        HazelcastCache hazelcastCache = (HazelcastCache) hazelcastCacheManager.getCache("user");
        return hazelcastCache.getNativeCache();
    }
    @RequestMapping(value = "/cachePersonajes")
    public Map<Object, Object> getCachePersonajeContent() {
        HazelcastCacheManager hazelcastCacheManager = (HazelcastCacheManager) cacheManager;
        HazelcastCache hazelcastCache = (HazelcastCache) hazelcastCacheManager.getCache("personajes");
        return hazelcastCache.getNativeCache();
    }

}