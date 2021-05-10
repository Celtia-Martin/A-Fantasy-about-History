package es.urjc.etsii.dad.Components;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//INSPECCIONAR
@RestController
public class CacheController {
	@Autowired
	private CacheManager cacheManager;
	
	@RequestMapping(value="/cachePersonaje", method=RequestMethod.GET)
	public Map<Object,Object> getCachePersonajes(){
		ConcurrentMapCacheManager cachemgr = (ConcurrentMapCacheManager) cacheManager;
		ConcurrentMapCache cache = (ConcurrentMapCache) cachemgr.getCache("personajes");
		return cache.getNativeCache();
	}

	@RequestMapping(value="/cacheFormacion", method=RequestMethod.GET)
	public Map<Object,Object> getCacheFormacion(){
		ConcurrentMapCacheManager cachemgr = (ConcurrentMapCacheManager) cacheManager;
		ConcurrentMapCache cache = (ConcurrentMapCache) cachemgr.getCache("formacion");
		return cache.getNativeCache();
	}
}
