package com.study.api.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.study.api.service.OrderService;
import com.study.api.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther lkx
 * @create 2022-05-09 9:34
 * @Description:
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    /**
     * 回调接口:当用户支付成功之后，微信支付平台
     * @return
     */
    @PostMapping("/callback")
    public String paySuccess(HttpServletRequest request) throws Exception {
        System.out.println("------------callback");
        ServletInputStream is = request.getInputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        StringBuilder builder = new StringBuilder();
        while ((len = is.read(bs)) != -1){
            builder.append(new String(bs,0,len));
        }

        String s = builder.toString();

        Map<String, String> map = WXPayUtil.xmlToMap(s);

        if (map != null && "success".equalsIgnoreCase(map.get("result_code"))){
            //支付成功
            //2. 修改订单状态“代发货/已支付”
            String orderId = map.get("out_trade_no");
            int i = orderService.updateOrderStatus(orderId, "2");
            System.out.println("--orderId:" + orderId);
            //3.响应微信支付平台
            WebSocketServer.sendMsg(orderId,"1");

            //4,响应微信支付平台
            if (i>0) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("return_code", "success");
                hashMap.put("return_msg", "OK");
                hashMap.put("appid", map.get("appid"));
                hashMap.put("return_code", "success");
                return WXPayUtil.mapToXml(hashMap);
            }
        }
        return null;

    }
}
