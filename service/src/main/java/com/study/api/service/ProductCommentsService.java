package com.study.api.service;

import com.study.api.vo.ResultVO;
import org.springframework.stereotype.Repository;

/**
 * @auther lkx
 * @create 2022-05-06 21:02
 * @Description:
 */

public interface ProductCommentsService {
    /**
     * 根据商品id分页查询评论信息
     * @param productId 商品id
     * @param pageNum 起始索引
     * @param limit 查询条数
     * @return
     */
    ResultVO listCommentsByProductId(String productId,int pageNum,int limit);

    ResultVO getCommentsCountByProductId(String productId);
}
