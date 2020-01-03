package me.xueyao.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2019-12-31 15:41
 **/
public class EhcacheUtil {

    private CacheManager cacheManager;
    private Cache cache;

    public EhcacheUtil() {
        cacheManager = new CacheManager();
        cache = cacheManager.getCache("cache_test");
    }


    public Element getElement(String key) {
        return cache.get(key);
    }

    public void save(String key, String value) {
        cache.put(new Element(key, value));
        cache.flush();
    }

    public void saveAll(LinkedHashMap<Object, Object> hashMap) {
        List<Element> allElement = new ArrayList<Element>();
        for (Map.Entry<Object, Object> objectObjectEntry : hashMap.entrySet()) {
            allElement.add(new Element(objectObjectEntry.getKey(), objectObjectEntry.getValue()));
        }
        cache.putAll(allElement);
        cache.flush();
    }

    public void close() {
        cacheManager.shutdown();
    }






    public static void main(String[] args) {
        EhcacheUtil ehcacheUtil = new EhcacheUtil();
        //ehcacheUtil.save("1", "小明");
        System.out.println(ehcacheUtil.getElement("1").getObjectValue());
        ehcacheUtil.close();
    }


}
