package com.hsm.elasticsearch.service;

import com.hsm.elasticsearch.entity.UserPO;
import org.zxp.esclientrhl.auto.intfproxy.ESCRepository;

/**
 * 接口必须继承自ESCRepository，并且定义接口时必须注明泛型的真实类型
 * 对应的实体类必须添加ESMetaData注解，组件才能自动识别
 * 实体类名称整个工程内不能重复，否则会导致生成代理类失败
 */
public interface UserReposity extends ESCRepository<UserPO,String> {
}
