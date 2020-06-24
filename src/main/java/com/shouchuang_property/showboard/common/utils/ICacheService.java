package com.shouchuang_property.showboard.common.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICacheService {
/**********************************  key 命令 *********************************/
	
	/**
	 * 判断缓存当中是否包含指定标识;  原子操作
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key);	
	
	
	/**
	 * 移除指定标识的缓存
	 * 
	 * @param keys
	 */
	public void remove(String... keys);	
	
	
	/**
	 * 清空缓存
	 */
	public void removeAll();
	
	
	/**
	 * 查询并返回缓存服务已缓存数量 : 原子操作
	 * 
	 * @return
	 */
	public long size();
	
	public Set<String> keys(String pattern);	
	
	/**
	 * 原子操作
	 * @param key
	 * @param seconds
	 * @return
	 */
	public boolean expire(String key, long seconds);
	
	/**
	 * 原子操作
	 * @param key
	 * @param date
	 * @return
	 */
	public boolean expireAt(String key, Date date);
	
	/**
	 * 原子操作
	 * @param key
	 * @return
	 */
	public boolean persist(String key);
	
	
/******************************  String 命令 ******************************/
	
	/**
	 * 向缓存服务插入对象  原子操作
	 * 
	 * @param key
	 * @param object
	 * @param livetime
	 * @return
	 */
	public boolean put(String key, final Serializable object);
	public boolean put(String key, final Serializable object, long livetime);
	
	/**
	 * 原子操作
	 * @param key
	 * @param maxId
	 * @param livetime
	 * @return
	 */
	public boolean put(String key, long value);
	public boolean put(String key, long value, final long livetime);
	
	public boolean put(String key, double value);
	public boolean put(String key, double value, final long livetime);
	
	/**
	 * 从缓存服务获取对象  原子操作
	 * 
	 * @param key
	 * @return
	 */
	public <T> T get(String key);
	
	/**
	 * 原子操作
	 * @param key
	 * @return
	 */
	public long getLong(String key);
	
	public double getDouble(String key);

	/**
	 * 原子操作
	 * @param key
	 * @return
	 */
	public long incr(final String key);
	
	/**
	 * 原子操作
	 * @param key
	 * @param count
	 * @return
	 */
	public long incr(final String key,final long count);
	
	public double incr(final String key,final double count);
	/**
	 * 原子操作
	 * @param key
	 * @param maxId
	 * @return
	 */
	public boolean setNX(String key, long maxId);
	
	/**
	 * 原子操作
	 * @param key
	 * @param maxId
	 * @return
	 */
	public boolean setNX(String key, String maxId);
	
	/**
	 * 原子操作
	 * @param key
	 * @return
	 */
	public long decr(final String key);
	
	/**
	 * 原子操作
	 * @param key
	 * @param count
	 * @return
	 */
	public long decr(final String key, final long count);	
	
	/**
	 * 原子操作
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> T getAndSetValue(String key, Serializable value);
	
	/**
	 * 原子操作
	 * @param key
	 * @param value
	 * @return
	 */
	public long getAndSetLongValue(String key, long value);
	
/***************************************** List 命令 ************************************/	
	/**
	 *原子操作 
	 * @param key
	 * @return
	 */
	public long getListSize(String key);
	
	/**
	 * 向指定标识的缓存列表中尾部添加元素原子操作
	 * 
	 * @param key
	 * @param value
	 */
	public boolean addToList(String key, Serializable value);
	
	public boolean addToListHead(String key, Serializable value);
	/**
	 * 返回指定标识的缓存列表
	 * 
	 * @param key
	 * @return
	 */
	public <T> List<T> getList(String key);
	
	
	public <T> T indexOf(String key, long index);
	
	
	/**
	 * 删除并得到列表的第一个元素
	 * @param key
	 * @return
	 */
	public <T> T popFromList(String key);
	
	/**
	 * 删除并得到列表的最后一个元素
	 * @param key
	 * @return
	 */	
	public <T> T rightPopFromList(String key);
	
	
/************************************** Set 命令 **********************************************/
	public long getSetSize(String key);
	
	public void addToSet(String key, Serializable value);
	
	public <T> Set<T> getFromSet(String key);
	
	public boolean existsInSet(String key, Serializable value);
	
	public  void removeFromSet(String key, Serializable ... values);
	
	
/**************************************** Map 命令 **************************************/	
	/**
	 * 向指定标识的缓存中添加索引
	 * 
	 * @param key
	 * @param entryKey
	 * @param entryValue
	 */
	public void addToMap(String key, String entryKey, Serializable entryValue);
	
	/**
	 * 从指定表示的缓存中移除特定的索引
	 * 
	 * @param key
	 * @param entryKeys
	 */
	public void removeFromMap(String key, String... entryKeys);
	
	/**
	 * 从指定标识的map中查找特定键值对应数据
	 * 
	 * @param key
	 * @param entryKey
	 * @return
	 */
	
	public <T> T getFromMap(String key, String entryKey);
	/** 
	 * @Title: getMap 
	 * @Description: 通过map的key获取该map
	 * @param key
	 * @return    设定文件 
	 * @return Map<Object,Object>    返回类型 
	 */
	
	public <T> Map<String, T> getMap(String key);
	

	public boolean existsInMap(String key, String entryKey);
	
	public Set<String> getAllKeysFromMap(String key);
	
	public long getMapSize(String key);
	
	public boolean addToMapNX(String key, String entryKey, Serializable entryValue);
	
	/**
	 * 原子操作
	 * @param key
	 * @param entryKey
	 * @param value
	 * @return
	 */
	public long incrMap(String key, String entryKey, long value);
	
	public Map<String, Long> getAllLongFromMap(String key);
	
	public long getLongFromMap(String key, String entryKey);
	
	public void addToMap(String key, String entryKey, long value);
	
	
	public double incrMap(String key, String entryKey, double value);
	
	public Map<String, Double> getAllDoubleFromMap(String key);
	
	public double getDoubleFromMap(String key, String entryKey);
	
	public void addToMap(String key, String entryKey, double value);	
}
