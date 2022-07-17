package com.study.api.dao;

import com.study.api.entity.Category;
import com.study.api.entity.CategoryVO;
import com.study.api.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends GeneralDAO<Category> {

    //1.连接查询
     List<Category> selectAllCategories();
     //2.子查询：根据parentID查询子分类
     List<Category> selectAllCategories2(int parentId);

     //3.查询一级类别
    List<CategoryVO> selectFirstLevelCategories();
}