package com.study.api.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import io.jsonwebtoken.*;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @auther lkx
 * @create 2022-05-01 22:06
 * @Description:
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }

        //请求头传参
        String token = request.getHeader("token");
        System.out.println("----------" + token);
        if (token == null) {
            ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL, "请先登录", null);
            //提示请先登录
            doResponse(response,resultVO);

        } else {
            try {
                //验证token
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("123456");//解析token的SingingKey必须和生成token时的密码一致

                //如果token正确
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_TIMEOUT, "登录过期,请重新登陆！", null);
                doResponse(response, resultVO);
            } catch (UnsupportedJwtException e) {
                ResultVO resultVO = new ResultVO(ResStatus.NO, "token不合法，请重试！", null);
                doResponse(response, resultVO);
            } catch (Exception e) {
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL, "请先登录！", null);
                doResponse(response, resultVO);
            }

        }
        return false;
    }

    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }
}
