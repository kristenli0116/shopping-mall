package com.study.api.service.Impl;

import com.study.api.dao.UsersMapper;
import com.study.api.entity.Users;
import com.study.api.service.UserService;
import com.study.api.utils.Base64Utils;
import com.study.api.utils.MD5Utils;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @auther lkx
 * @create 2022-04-27 22:51
 * @Description:
 */
@Service
@Scope("singleton")
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional
    public ResultVO userRegister(String name, String pwd) {
        synchronized (this) {
            //1.根据用户查询，这个用户是否已经被注册
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username", name);
            List<Users> users = usersMapper.selectByExample(example);

            //2.如果用户没有被注册则惊醒保存操作
            if (users.size() == 0) {
                String md5Pwd = MD5Utils.md5(pwd);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserRegtime(new Date());
                user.setUserModtime(new Date());
                int i = usersMapper.insert(user);
                if (i > 0) {
                    return new ResultVO(ResStatus.OK, "注册成功", null);
                } else {
                    return new ResultVO(ResStatus.NO, "注册失败", null);
                }
            } else {
                return new ResultVO(ResStatus.NO, "用户已经存在", null);
            }
        }
    }

    @Override
    public ResultVO checkLogin(String name, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", name);
        List<Users> users = usersMapper.selectByExample(example);


        if (users.size() == 0) {
            return new ResultVO(ResStatus.NO, "登陆失败，用户名不存在", null);
        } else {
            String md5Pwd = MD5Utils.md5(pwd);
            if (md5Pwd.equals(users.get(0).getPassword())) {
                //如果登录验证成功，则需要生成令牌token(token就是按照特定规则生成的字符串)
                JwtBuilder builder = Jwts.builder();
                HashMap<String, Object> map = new HashMap<>();
                map.put("key1","value1");
                map.put("key2","value2");

                String token = builder.setSubject(name)  //主题，也就是token中携带的数据
                        .setIssuedAt(new Date())    //设置token生成时间
                        .setId(users.get(0).getUserId() + "")   //设置用户id为token id
                        .setClaims(map)     //map可以存放用户角色的权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))  //设置token的过期时间
                        .signWith(SignatureAlgorithm.HS256, "123456")   //设置加密方式，和加密密码
                        .compact();

                return new ResultVO(ResStatus.OK,token, users.get(0));
            } else {
                return new ResultVO(ResStatus.NO, "登陆失败", null);
            }
        }
    }
}
