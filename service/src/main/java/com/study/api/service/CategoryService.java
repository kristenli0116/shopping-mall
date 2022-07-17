package com.study.api.service;

import com.study.api.vo.ResultVO;

/**
 * @auther lkx
 * @create 2022-05-02 23:41
 * @Description:
 */
public interface CategoryService {
   ResultVO listCategories();

   ResultVO listFirstLevelCategories();
}
