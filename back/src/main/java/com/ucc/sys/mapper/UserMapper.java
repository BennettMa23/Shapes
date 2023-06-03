package com.ucc.sys.mapper;

import com.ucc.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mabaishun
 * @since 2023-04-11
 */
public interface UserMapper extends BaseMapper<User> {
//    @Select()
    public List<String> getRoleNameByUserId(Integer userId);
}
