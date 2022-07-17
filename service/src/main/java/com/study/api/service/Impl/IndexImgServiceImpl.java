package com.study.api.service.Impl;

import com.study.api.dao.IndexImgMapper;
import com.study.api.entity.IndexImg;
import com.study.api.service.IndexImgService;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-02 13:11
 * @Description:
 */
@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    public ResultVO listIndexImg() {
        List<IndexImg> list = indexImgMapper.listIndexImg();
        if (list.size() == 0){
            return new ResultVO(ResStatus.NO,"fail",null);
        }else {
            return new ResultVO(ResStatus.OK, "success", list);
        }
    }
}
