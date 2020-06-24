package com.shouchuang_property.showboard.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.lettuce.core.api.reactive.RedisReactiveCommands;

@Component
public class CacheServiceRedisImpl implements ICacheService {
	@Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

	/*************************************** key 命令 ****************************/	

	/**
	 * 原子操作
	 */
	@Override
	public boolean exists(String key) {
		if (key == null) {
			return false;
		}
		
		return redisTemplate.hasKey(key);
	}
	
	/*
	 *  删除某些Key
	 */
	@Override
	public void remove(String... keys) {
		if (keys == null) {
			return;
		}
		
		List<String> allKeys = Arrays.asList(keys);
		redisTemplate.delete(allKeys);		
	}

	//清缓存时，不清redis中的获取seq的表, 所有的seq表都是以T_开头
	@Override
	public void removeAll() {
		Set<String> allKeys = keys("*");
		Iterator<String> it = allKeys.iterator();
		while(it.hasNext()){
			String redisKey = it.next();
			if (redisKey.length()>2 && "T_".equals(redisKey.substring(0, 2))){
				continue;
			} else if(redisKey.equals("maintenance")){
				continue;
			} else if(redisKey.length()>3 && "SYS".equalsIgnoreCase(redisKey.substring(0,3))){
				continue;
			}
			remove(redisKey);
		}
	}
	
	@Override
	public long size() {
		return redisTemplate.getRequiredConnectionFactory().getConnection().dbSize();
	}
		

	@Override
	public Set<String> keys(String pattern){
		return redisTemplate.keys(pattern);
	}	
	
	@Override	
	public boolean expire(final String key, final long seconds){
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}
	
	@Override	
	public boolean expireAt(String key, Date date){
		return redisTemplate.expireAt(key, date);
	}
	
	@Override	
	public boolean persist(String key){
		return redisTemplate.persist(key);
	}
	
/***********************************String 命令 *****************************************/
	@Override
	public boolean put(final String key, final Serializable object) {
		return put(key, object, 0);
	}
	
	@Override
	public boolean put(final String key, final Serializable object, final long livetime) {
		if (key == null || object == null) {
			return false;
		}
		if (livetime > 0) {
			redisTemplate.opsForValue().set(key, object, livetime, TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, object);
		}
		return true;
	}
	
	@Override
	public boolean put(final String key, final long value){		
		return put(key, value, 0);
	}
	
	@Override
	public boolean put(final String key, final long value, final long livetime){
		if (key == null) {
			return false;
		}
		if (livetime > 0) {
			redisTemplate.opsForValue().set(key, value, livetime, TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, value);
		}
		return true;
	}
	
	@Override
	public boolean put(final String key, final double value) {
		return put(key, value, 0);
	}
	
	@Override
	public boolean put(final String key, final double value, final long livetime){
		if (key == null) {
			return false;
		}
		if (livetime > 0) {
			redisTemplate.opsForValue().set(key, value, livetime, TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, value);
		}
		return true;
	}
	
	@Override
	public <T> T get(String key) {
		if (key == null) {
			return null;
		}
		Object obj = redisTemplate.opsForValue().get(key);
		if (obj == null) {
			return null;
		}
		return (T)obj;
	}
	
	@Override
	public long getLong(String key){
		if(key == null){
			return 0;
		}
		String v = (String)redisTemplate.opsForValue().get(key);
		
		if (v == null){
			return 0;
		}
		try{
			return Long.parseLong(v);
		}catch(NumberFormatException e){
			try{
				double ret = Double.parseDouble(v);
				return (long)ret;
			}catch(Exception e1){
				return 0;
			}
		}
	}

	@Override
	public double getDouble(String key){
		if(key == null){
			return 0;
		}
		String value = (String)redisTemplate.opsForValue().get(key);
		if (value == null){
			return 0;
		}
		try{
			return Double.parseDouble(value);
		}catch(Exception e){
			return 0;
		}
	}
	
	@Override
	public long incr(String key){
		return incr(key, 1);
	}
	
	@Override
	public long incr(String key, long count){
		return redisTemplate.opsForValue().increment(key, count);
	}
	
	@Override
	public double incr(String key, double count){
		return redisTemplate.opsForValue().increment(key, count);
	}
	
	@Override
	public long decr(final String key){
		return incr(key, -1);
	}
	
	@Override
	public long decr(final String key, final long count){
		return incr(key, count);
	}
	
	@Override
	public boolean setNX(String key, long maxId){
		return redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(maxId));
	}
	
	@Override
	public boolean setNX(String key, String value){
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAndSetValue(String key, Serializable value)	
	{
		return (T)redisTemplate.opsForValue().getAndSet(key, value);
	}
	
	public long getAndSetLongValue(String key, long value)	
	{
		String oldValue = (String)redisTemplate.opsForValue().getAndSet(key, String.valueOf(value));
		return Long.parseLong(oldValue);
	}
	
/*************************************** List 命令 **********************************/	
	
	@Override
	public boolean addToList(String key, Serializable value) {
		if (key == null || value == null) {
			return false;
		}
		return redisTemplate.opsForList().rightPush(key, value) != 0;
	}

	@Override
	public boolean addToListHead(String key, Serializable value){
		if (key == null || value == null) {
			return false;
		}
		
		return redisTemplate.opsForList().leftPush(key, value) != 0;
	}
	
	@Override
	public <T> List<T> getList(String key) {
		long size = redisTemplate.opsForList().size(key);
		List<Serializable> redisValues = redisTemplate.opsForList().range(key, 0, size - 1);
		if (redisValues == null){
			return null;
		}
		List<T> result = new ArrayList<T>();
		for(Serializable obj : redisValues){
			result.add((T)obj);
		}
		return result;
	}

	@Override
	public <T> T indexOf(String key, long index){
		return (T)(redisTemplate.opsForList().index(key, index));
	}
	
	@Override
	public <T> T popFromList(String key){
		return (T)(redisTemplate.opsForList().leftPop(key));
	}
	
	@Override
	public <T> T rightPopFromList(String key){
		return (T)(redisTemplate.opsForList().rightPop(key));
	}
	
	@Override
	public long getListSize(String key) {
		return redisTemplate.opsForList().size(key);
	}	
	
/********************************** Set 命令 ***************************************/
	@Override
	public long getSetSize(String key){
		return redisTemplate.opsForSet().size(key);
	}
	
	@Override
	public void addToSet(String key, Serializable value){
		redisTemplate.opsForSet().add(key, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Set<T> getFromSet(String key){
		Set<Serializable> redisValues = redisTemplate.opsForSet().members(key);
		if (redisValues == null){
			return null;
		}
		Set<T> ret = new HashSet<T>();
		for(Serializable v : redisValues){
			ret.add((T)v);
		}
		
		return ret;
	}
	
	@Override
	public boolean existsInSet(String key, Serializable value){
		return redisTemplate.opsForSet().isMember(key, value);
	}
	
	@Override
	public void removeFromSet(String key, Serializable ... values){
		if (values == null){
			return;
		}
		
		List<Serializable> tmp = new ArrayList<Serializable>();
		for(Serializable o : values){
			tmp.add(o);
		}
		
		redisTemplate.opsForSet().remove(key, tmp.toArray());
	}
	
	
	
/********************************** Map 命令 ***************************************/	
	@Override
	public void addToMap(String key, String entryKey, Serializable entryValue) {
		if (key == null || entryKey == null || entryValue == null) {
			return ;
		}
		redisTemplate.opsForHash().put(key, entryKey, entryValue);
	}
	
	@Override
	public void addToMap(String key, String entryKey, long value) {
		if (key == null || entryKey == null) {
			return ;
		}
		redisTemplate.opsForHash().put(key, entryKey, String.valueOf(value));
	}

	@Override
	public void addToMap(String key, String entryKey, double value) {
		if (key == null || entryKey == null) {
			return ;
		}
		redisTemplate.opsForHash().put(key, entryKey, String.valueOf(value));
	}
	
	@Override
	public void removeFromMap(String key, String... entryKeys) {
		if (key == null || entryKeys == null) {
			return;
		}
		Object[] objs = new Object[entryKeys.length];
		int i = 0;
		for(String k : entryKeys) {
			objs[i++] = k;
		}
		redisTemplate.opsForHash().delete(key, objs);
	}

	@Override
	public <T> T getFromMap(String key, String entryKey) {
		if  (key == null || entryKey == null) {
			return null;
		}
		
		String value = (String)redisTemplate.opsForHash().get(key, entryKey);
		return (T)(value);
	}
	
	@Override
	public long getLongFromMap(String key, String entryKey) {
		if  (key == null || entryKey == null) {
			return 0L;
		}
		
		String value = (String)redisTemplate.opsForHash().get(key, entryKey);
		if (value == null){
			return 0L;
		}
		try{
			return Long.parseLong(value);
		}catch(NumberFormatException e){
			try{
				double ret = Double.parseDouble(value);
				return (long)ret;
			}catch(Exception e1){
				return 0L;
			}
		}	
	}
	
	@Override
	public double getDoubleFromMap(String key, String entryKey) {
		if  (key == null || entryKey == null) {
			return 0;
		}
		
		String value = (String)redisTemplate.opsForHash().get(key, entryKey);
		if (value == null){
			return 0;
		}
		try{
			return Double.parseDouble(value);
		}catch(Exception e){
			return 0;
		}
	}
	
	@Override
	public <T> Map<String, T> getMap(String key){
		if(key == null){
			return null;
		}
		
		Map<Object, Object> v = redisTemplate.opsForHash().entries(key);
		if (v == null){
			return null;
		}
		
		Map<String, T> ret = new HashMap<String, T>();
		Set<Map.Entry<Object, Object>> tmp = v.entrySet();
		for(Map.Entry<Object, Object> m : tmp){
			String k = (String)m.getKey();
			Object kv = m.getValue();
			ret.put(k, (T)kv);
		}
		
		return ret;
	}
	
	@Override
	public Map<String, Long> getAllLongFromMap(String key){
		if(key == null){
			return null;
		}
		
		Map<Object, Object> v = redisTemplate.opsForHash().entries(key);
		if (v == null){
			return null;
		}
		
		Map<String, Long> ret = new HashMap<String, Long>();
		Set<Map.Entry<Object, Object>> tmp = v.entrySet();
		for(Map.Entry<Object, Object> m : tmp){
			String k = (String)m.getKey();
			String kv = (String)m.getValue();
			ret.put(k, Long.parseLong(kv));
		}
		
		return ret;
	}
	
	@Override
	public Map<String, Double> getAllDoubleFromMap(String key){
		if(key == null){
			return null;
		}
		
		Map<Object, Object> v = redisTemplate.opsForHash().entries(key);
		if (v == null){
			return null;
		}
		
		Map<String, Double> ret = new HashMap<String, Double>();
		Set<Map.Entry<Object, Object>> tmp = v.entrySet();
		for(Map.Entry<Object, Object> m : tmp){
			String k = (String)m.getKey();
			String kv = (String)m.getValue();
			ret.put(k, Double.parseDouble(kv));
		}
		
		return ret;
	}
	
	@Override
	public boolean existsInMap(String key, String entryKey){
		return redisTemplate.opsForHash().hasKey(key, entryKey);
	}
	
	@Override
	public Set<String> getAllKeysFromMap(String key){
		Set<Object> v = redisTemplate.opsForHash().keys(key);
		if (v == null){
			return null;
		}
		
		Set<String> ret = new HashSet<String>();
		for(Object o : v){
			String t = o.toString();
			ret.add(t);
		}
		return ret;
	}
	
	@Override
	public long getMapSize(String key){
		return redisTemplate.opsForHash().size(key);
	}
	
	@Override
	public boolean addToMapNX(String key, String entryKey, Serializable entryValue){
		return redisTemplate.opsForHash().putIfAbsent(key, entryKey, entryValue);
	}
	
	@Override
	public long incrMap(String key, String entryKey, long value){
		return redisTemplate.opsForHash().increment(key, entryKey, value);
	}
	
	@Override
	public double incrMap(String key, String entryKey, double value){
		return redisTemplate.opsForHash().increment(key, entryKey, value);
	}

	
//	private byte[] toByteArray(final Object obj) {
//		return toRedisString(obj).getBytes();
//	}

//	private String toRedisString(final Object obj) {
//		if (obj == null){
//			return "";
//		}
//		try{
//			JSONObject json = new JSONObject();
//			json.put("class", obj.getClass().getName());
//			boolean type = isBasicType(obj);
//			json.put("basicType", type);
//			json.put("data", JSONObject.toJSONString(obj));
//			return json.toString();
//		}catch (Exception e){
//			e.printStackTrace();
//			return "";
//		}
//	}
	
//	private boolean isBasicType(Object obj){
//		return (  obj instanceof  Integer 
//				|| obj instanceof Long
//				|| obj instanceof String
//				|| obj instanceof Boolean
//				|| obj instanceof Double
//				|| obj instanceof Byte
//				|| obj instanceof Short
//				|| obj instanceof Float
//			   );
//	}
	
	/**
	 * <byte[]转Object>
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
//	@SuppressWarnings("unchecked")
//	private <T> T toObject(Object object) {
//		if (object == null){
//			return null;
//		}
//		try {
//			JSONObject json = JSONObject.parseObject((String)object);
//			String className = json.getString("class");
//			if (json.getBooleanValue("basicType")){
//				return (T) JSON.parseObject(json.getString("data"), Class.forName(className));
//			}else{
//				return (T) JSON.parseObject(json.get("data").toString(),Class.forName(className));
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		} catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
//	}
			
	/**
	 * 计算对象缓存标识符信息
	 * 
	 * @param object
	 * @param keyField
	 * @return
	 */
//	private static String getCacheKey(Object object, String keyField) {
//		StringBuffer key = new StringBuffer();
//		key.append(object.getClass().getName());
//		key.append("-");
//		key.append(ReflectTool.readFieldValue(object, keyField));
//		return key.toString();
//	}
	
	/**
	 * 计算指定类型，特定数据的缓存键值
	 * 
	 * @param clazz
	 * @param keyValue
	 * @return
	 */
//	private String getCacheKey(Class<?> clazz, String keyValue) {
//		StringBuffer key = new StringBuffer();
//		key.append(clazz.getName());
//		key.append("-");
//		key.append(keyValue);
//		return key.toString();
//	}
}
