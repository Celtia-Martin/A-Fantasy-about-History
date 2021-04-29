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
	@RequestMapping(value="/cache",method= RequestMethod.GET)
	public Map<Object,Object> getCacheContent(){
		ConcurrentMapCacheManager cachemgr=(ConcurrentMapCacheManager)cacheManager;
		ConcurrentMapCache cache= (ConcurrentMapCache) cachemgr.getCache("historyFantasy");
		return cache.getNativeCache();
		}

}
