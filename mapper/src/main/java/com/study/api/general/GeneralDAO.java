package com.study.api.general;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @auther lkx
 * @create 2022-04-28 22:39
 * @Description:
 */
public interface GeneralDAO<T> extends Mapper<T>, MySqlMapper<T> {
}
