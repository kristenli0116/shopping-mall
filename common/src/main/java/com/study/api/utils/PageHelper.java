package com.study.api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-06 22:06
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper<T> {

    //总记录数
    private int count;

    //总页数
    private int pageCount;

    //分页数据
    private List<T> list;
}
