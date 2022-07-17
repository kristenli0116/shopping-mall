package com.study.api.dao;

import com.study.api.entity.IndexImg;
import com.study.api.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexImgMapper extends GeneralDAO<IndexImg> {

    //1。查询轮播图信息：查询status=1切按照seq进行排序
    public List<IndexImg> listIndexImg();
}