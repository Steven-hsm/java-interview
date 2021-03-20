package com.hsm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsm.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
