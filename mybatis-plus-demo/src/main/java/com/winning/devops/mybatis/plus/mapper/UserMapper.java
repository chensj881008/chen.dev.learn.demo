package com.winning.devops.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winning.devops.mybatis.plus.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author chensj
 * @title Mapper类
 * @project mybatis-plus-demo
 * @package com.winning.devops.mybatis.plus.mapper
 * @date: 2019-02-01 15:05
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
