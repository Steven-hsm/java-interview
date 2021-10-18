1. jedis

   * maven 依赖
   
     ```xml
     <dependency>
         <groupId>redis.clients</groupId>
         <artifactId>jedis</artifactId>
         <version>3.2.0</version>
     </dependency>
     ```
   
   * 连接使用
   
     ```java
     Jedis jedis = new Jedis("127.0.0.1", 6379);
     ```
   
2. spring-boot

   * maven 依赖

     ```xml
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-redis</artifactId>
     </dependency>
     ```

   * 在application.ymal中增加配置

     ```yaml
     spring: 
     	redis:
     		host: 127.0.0.1 
     		port: 6379
     ```

   * 自动以redis配置

     ```java
     @Configuration
     public class RedisConfig {
         // 这是我给大家写好的一个固定模板，大家在企业中，拿去就可以直接使用！
         // 自己定义了一个 RedisTemplate
         @Bean
         @SuppressWarnings("all")
         public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
             // 我们为了自己开发方便，一般直接使用
             //<String, Object>
             RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
             template.setConnectionFactory(factory);
             // Json序列化配置
             Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
             ObjectMapper om = new ObjectMapper();
             om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
             om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
             jackson2JsonRedisSerializer.setObjectMapper(om);
             // String 的序列化
             StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
             // key采用String的序列化方式
             template.setKeySerializer(stringRedisSerializer);
             // hash的key也采用String的序列化方式
             template.setHashKeySerializer(stringRedisSerializer);
             // value序列化方式采用jackson
             template.setValueSerializer(jackson2JsonRedisSerializer);
             // hash的value序列化方式采用jackson
             template.setHashValueSerializer(jackson2JsonRedisSerializer);
             template.afterPropertiesSet();
             return template;
         }
     }
     ```

   * 启动类

     ```java
     @SpringBootApplication
     public class WebApplication {
         public static void main(String[] args) {
             SpringApplication.run(WebApplication.class, args);
         }
     }
     ```

   * 测试类

     ```java
     @SpringBootTest
     @RunWith(SpringRunner.class)
     public class RedisTest {
         @Autowired
         private RedisTemplate redisTemplate;
         @Test
         public void test() {
             redisTemplate.opsForValue().set("myKey","myValue");
             System.out.println(redisTemplate.opsForValue().get("myKey"));
         }
     }
     ```

   

