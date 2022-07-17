package com.study.api.service.Impl;

import com.study.api.dao.CategoryMapper;
import com.study.api.entity.Category;
import com.study.api.entity.CategoryVO;
import com.study.api.service.CategoryService;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-02 23:42
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询分类列表（包含三个子级别的分类）
     * @return
     */
    @Override
    public ResultVO listCategories() {
        List<Category> categoryVOS = categoryMapper.selectAllCategories();
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", categoryVOS);
        return resultVO;
    }

    /**
     * 查询所有一级分类，同时查询一级分类下所有销量最高的六个商品
     * @return
     */
    @Override
    public ResultVO listFirstLevelCategories() {
        List<CategoryVO> voList = categoryMapper.selectFirstLevelCategories();
        ResultVO resultVO = new ResultVO(ResStatus.OK,"success",voList);
        return resultVO;
    }
}
