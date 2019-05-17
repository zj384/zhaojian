package com.zhxs.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

// 该配置为配置类,代替之前配置文件 web.xml和spring配置文件
@Configuration // 标识配置类,当springboot程序启动时,会加载配置类
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String nodes;
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> hostAndPorts = new HashSet<>();
		String[] node = nodes.split(",");
		for (String n : node) {
			String[] hAndp = n.split(":");
			hostAndPorts.add(new HostAndPort(hAndp[0], Integer.parseInt(hAndp[1])));
		}
		return new JedisCluster(hostAndPorts);
	}
}
