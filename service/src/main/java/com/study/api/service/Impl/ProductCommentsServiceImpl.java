package com.study.api.service.Impl;

import com.study.api.dao.ProductCommentsMapper;
import com.study.api.entity.ProductComments;
import com.study.api.entity.ProductCommentsVO;
import com.study.api.service.ProductCommentsService;
import com.study.api.utils.PageHelper;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther lkx
 * @create 2022-05-06 21:06
 * @Description:
 */
@Repository
public class ProductCommentsServiceImpl implements ProductCommentsService {
    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVO listCommentsByProductId(String productId,int pageNum,int limit) {
        //List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.seletCommontsByProductId(productId);
        //1.根据商品id查询总记录数
        Example example =new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int count = productCommentsMapper.selectCountByExample(example);

        //2.计算总页数（必须确定每页显示多少条 pageSize）
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;

        //3.查询当前页的数据（因为评论中需要用户信息，因此需要连表查询------自定义）
        int start = (pageNum-1)*limit;
        List<ProductCommentsVO> list = productCommentsMapper.selectCommentsByProductId(productId, start, limit);

        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", new PageHelper<ProductCommentsVO>(count,pageCount,list));
        return resultVO;
    }

    @Override
    public ResultVO getCommentsCountByProductId(String productId) {
        //1.查询当前商品评价的总数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int total = productCommentsMapper.selectCountByExample(example);

        //2.查询好评评价数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("commType",1);
        int  goodTotal = productCommentsMapper.selectCountByExample(example1);

         //3.查询中评评价数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("commType",0);
        int  midTotal = productCommentsMapper.selectCountByExample(example2);

         //4.查询差评评价数
        Example example3 = new Example(ProductComments.class);
        Example.Criteria criteria3 = example3.createCriteria();
        criteria3.andEqualTo("commType",-1);
        int  badTotal = productCommentsMapper.selectCountByExample(example3);

        //5.计算好评率
        double percent = (Double.parseDouble(goodTotal + "") / Double.parseDouble(total + ""))*100;
        String percentValue =(percent +"").substring(0,(percent+"").lastIndexOf(".")+2);

        Map<String,Object> map =new HashMap<>();
        map.put("total", total);
        map.put("goodTotal", goodTotal);
        map.put("midTotal", midTotal);
        map.put("badTotal", badTotal);
        map.put("percent", percentValue);

        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", map);
        return resultVO;
    }

}
