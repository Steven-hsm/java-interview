package com.hsm.brain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsm.brain.model.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangsm
 * @since 2021-08-04
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    @Select("Select * from user where open_id = #{openId}")
    UserPO selectByOpentId(@Param("openId") String openId);
}
