package com.study.api.service.Impl;

import com.study.api.dao.UserAddrMapper;
import com.study.api.entity.UserAddr;
import com.study.api.service.UserAddressService;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-08 14:14
 * @Description:
 */
@Service
public class UserAddrServiceImpl implements UserAddressService {
    @Autowired
    private UserAddrMapper userAddrMapper;

    @Override
    public ResultVO listAddressByUid(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("status",1);

        List<UserAddr> addrList = userAddrMapper.selectByExample(example);
        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", addrList);
        return resultVO;
    }
}
