package com.hsm.brain.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * @Classname JpaQueryConfig
 * @Description Jqa查询配置
 * @Date 2021/7/9 17:57
 * @Created by huangsm
 */
@Configuration
public class JpaQueryConfig {
    @Bean
    public JPAQueryFactory jpaQuery(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
