package com.study.api.controller;

import com.auth0.jwt.impl.JWTParser;
import com.study.api.entity.ShoppingCart;
import com.study.api.interceptor.CheckTokenInterceptor;
import com.study.api.service.ShoppingCartService;
import com.study.api.utils.Base64Utils;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import com.sun.prism.PixelFormat;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther lkx
 * @create 2022-05-01 18:08
 * @Description:
 */
@RestController
@RequestMapping("/shopCart")
@CrossOrigin
@Api(value = "提供购物车业务接口",tags = "购物车管理")
public class ShopCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

//    @GetMapping("/list")
//    @ApiImplicitParam(dataType = "string",name = "token",value = "授权令牌",required = true)
//    public ResultVO listCarts(String token){
//
//        if(token == null){
//            return new ResultVO(ResStatus.NO,"请先登录",null);
//        }else {
//            //验证token
//            JwtParser parser = Jwts.parser();
//            parser.setSigningKey("123456");
//            try {
//                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
//
//                Claims body = claimsJws.getBody();  //获取token中用户数据
//                String subject = body.getSubject(); //获取token中的
//                String key1 = body.get("key1", String.class);       //获取生成token时存储的claims的map中的值
//
//                return new ResultVO(ResStatus.OK,"success",null);
//            }catch (ExpiredJwtException e){
//                return new ResultVO(ResStatus.NO,"登录过期,请先登录",null);
//            }catch (UnsupportedJwtException e){
//                return new ResultVO(ResStatus.NO,"token不合法",null);
//            }catch (Exception e){
//                return new ResultVO(ResStatus.NO,"请重新登陆",null);
//            }
//               //解析token的SingingKey必须和生成token时的密码一致
//        }
//    }

    @PostMapping("/add")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart,@RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.addShoppingCart(cart);
        return resultVO;
    }

    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int",name = "userId",value = "用户ID",required = true)
    public ResultVO listShoppingCartByUserId(Integer userId,
                                             @RequestHeader("token") String token){
            ResultVO resultVO = shoppingCartService.listShoppingCartByUserId(userId);
            return resultVO;
    }

    @PutMapping("/update/{cid}/{cnum}")
    public ResultVO updateCartNum(@PathVariable("cid") Integer cartId,
                                  @PathVariable("cnum") Integer cartNum,
                                  @RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.updateCartNum(cartId, cartNum);
        return resultVO;
    }

    @GetMapping("/listByCids")
    @ApiImplicitParam(dataType = "java.long.List",name = "cids",value = "选择购物车记录的id",required = true)
    public ResultVO listShoppingCartsByCids(String cids,@RequestHeader("token") String token){
        ResultVO resultVO = shoppingCartService.listShoppingCartsByCids(cids);
        return  resultVO;
    }
}
