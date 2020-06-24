package com.shouchuang_property.showboard;

import java.io.Serializable;
import java.text.Format;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.test.context.junit4.SpringRunner;

import com.shouchuang_property.showboard.common.utils.ICacheService;
import com.shouchuang_property.showboard.entity.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	private static final Logger log = LoggerFactory.getLogger(RedisTest.class);
//	@Autowired
//    private StringRedisTemplate stringRedisTemplate;
//	
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
    
//    @Autowired
//    private ICacheService cacheService;
    
	@Test
	public void valid() {
		Employee employee1 = new Employee();
		employee1.setId(1);
        employee1.setName("张三");
        employee1.setAge(15);
        employee1.setDepartName("财务部");
        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setName("李四");
        employee2.setAge(48);
        employee2.setDepartName("技术部");
        Employee employee3 = new Employee();
        employee3.setId(3);
        employee3.setName("王五");
        employee3.setAge(36);
        employee3.setDepartName("发展部");
		// Value ---------------------------------------------------------
		// 基本类型测试
//		redisTemplate.opsForValue().set("key_long", 1L);
//		redisTemplate.opsForValue().set("key_long1", 29L, 500, TimeUnit.SECONDS);
//		redisTemplate.opsForValue().set("key_double", 1.6);
//		redisTemplate.opsForValue().set("key_double1", 25.8, 500, TimeUnit.SECONDS);
//		redisTemplate.opsForValue().set("key_string", "aaa");
//		redisTemplate.opsForValue().set("key_string1", "bbbb", 500, TimeUnit.SECONDS);
//		
//		System.out.println(String.format("key_long：{0}", (long)redisTemplate.opsForValue().get("key_long")));
//		System.out.println(String.format("key_long1：{0}", (long)redisTemplate.opsForValue().get("key_long1")));
//		System.out.println(String.format("key_double：{0}", (double)redisTemplate.opsForValue().get("key_double")));
//		System.out.println(String.format("key_double1：{0}", (double)redisTemplate.opsForValue().get("key_double1")));
//		System.out.println(String.format("key_string：{0}", (String)redisTemplate.opsForValue().get("key_string")));
//		System.out.println(String.format("key_string1：{0}", (String)redisTemplate.opsForValue().get("key_string1")));
		// 对象
//		Employee employee = new Employee();
//		employee.setId(1);
//      employee.setName("张三");
//      employee.setAge(15);
//        
//		redisTemplate.opsForValue().set("employee1", employee);
//		log.info(redisTemplate.opsForValue().get("employee1").toString());
//		log.info(((Employee)redisTemplate.opsForValue().get("employee1")).toString());
		// Map ---------------------------------------------------------
//		redisTemplate.opsForHash().put("hash_string", 1, "value1");
//		redisTemplate.opsForHash().put("hash_string", 2, "value2");
//		log.info(redisTemplate.opsForHash().get("hash_string", 1).toString());
//		log.info(redisTemplate.opsForHash().get("hash_string", 2).toString());
//		redisTemplate.opsForHash().put("hash_employee", employee1.getId(), employee1);
//		redisTemplate.opsForHash().put("hash_employee", employee2.getId(), employee2);
//		log.info(redisTemplate.opsForHash().get("hash_employee", 1).toString());
//		log.info(redisTemplate.opsForHash().get("hash_employee", 2).toString());
//		redisTemplate.opsForHash().put("hash_key_string", "1", "value111");
//		redisTemplate.opsForHash().put("hash_key_string", "2", "value222");
//		log.info(redisTemplate.opsForHash().get("hash_key_string", "1").toString());
//		log.info(redisTemplate.opsForHash().get("hash_key_string", "2").toString());
        // 	List ---------------------------------------------------------
        redisTemplate.opsForList().rightPush("list_string", "000");
        redisTemplate.opsForList().rightPush("list_string", "111");
        redisTemplate.opsForList().rightPush("list_string", "222");
        redisTemplate.opsForList().rightPush("list_string", "333");
        redisTemplate.opsForList().rightPushIfPresent("list_string", "444");
        redisTemplate.opsForList().leftPop("list_string");
        List<Serializable> list = redisTemplate.opsForList().range("list_string", 0, redisTemplate.opsForList().size("list_string") - 1);
        log.info(String.valueOf(list.size()));
        for(Serializable s : list) {
        	log.info(s.toString());
        }
        redisTemplate.opsForList().rightPush("list_member", employee1);
        redisTemplate.opsForList().rightPush("list_member", employee2);
        redisTemplate.opsForList().rightPush("list_member", employee3);
        List<Serializable> list1 = redisTemplate.opsForList().range("list_member", 0, redisTemplate.opsForList().size("list_member") - 1);
        for(Serializable s : list1) {
        	Employee emp = (Employee)s;
        	log.info(emp.toString());
        }
        // Set ---------------------------------------------------------
//        redisTemplate.opsForSet().add("set_employee", employee1);
//        redisTemplate.opsForSet().add("set_employee", employee2);
//        redisTemplate.opsForSet().add("set_employee", employee3);
//        redisTemplate.opsForSet().add("set_employee1", employee1);
//        redisTemplate.opsForSet().add("set_employee1", employee2);
//        
//        log.info(String.valueOf(redisTemplate.opsForSet().intersect("set_employee", "set_employee1").size()));
//        redisTemplate.opsForSet().intersectAndStore("set_employee", "set_employee1", "set_employee2");
//        log.info(String.valueOf(redisTemplate.opsForSet().difference("set_employee", "set_employee1").size()));
//        redisTemplate.opsForSet().remove("set_employee", employee2);
        // 
//        redisTemplate.opsForZSet().add("zset_employee", employee1, 20);
//        redisTemplate.opsForZSet().add("zset_employee", employee2, 10);
//        redisTemplate.opsForZSet().add("zset_employee", employee3, 40);
	}
	
	@Test
	public void validTest() {
		Employee employee1 = new Employee();
		employee1.setId(1);
        employee1.setName("张三");
        employee1.setAge(15);
        employee1.setDepartName("财务部");
        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setName("李四");
        employee2.setAge(48);
        employee2.setDepartName("技术部");
        Employee employee3 = new Employee();
        employee3.setId(3);
        employee3.setName("王五");
        employee3.setAge(36);
        employee3.setDepartName("发展部");
	}
}
