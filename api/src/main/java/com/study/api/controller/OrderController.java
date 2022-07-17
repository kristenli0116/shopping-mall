package com.study.api.controller;

import com.github.wxpay.sdk.WXPay;
import com.study.api.config.MyPayConfig;
import com.study.api.entity.Orders;
import com.study.api.service.OrderService;
import com.study.api.vo.ResStatus;
import com.study.api.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther lkx
 * @create 2022-05-08 21:04
 * @Description:
 */
@RestController
@CrossOrigin
@Api(value = "提供订单相关的操作接口", tags = "订单管理")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVO add(String cids, @RequestBody Orders orders) {
        ResultVO resultVO = null;
        try {
            Map<String, String> orderInfo = orderService.addOrder(cids, orders);
            String orderId = orderInfo.get("orderId");

            if (orderId != null) {
                //设置当前订单信息
                HashMap<String, String> data = new HashMap<>();
                data.put("body", orderInfo.get("productNames"));        //商品描述
                data.put("out_trade_no", orderId);   //使用当前用户ID
                data.put("fee_type", "CNY");         //支付货种
                data.put("total_fee", orders.getActualAmount() * 100 + "");       //支付金额
                data.put("trade_type", "NATIVE");    //交易类型
                data.put("notify_url", "http://lkx.free.idcfengye.com/pay/callback");          //设置支付完成时回调的url

                //发送请求，获取响应
                //微信支付：申请支付链接
                WXPay wxPay = new WXPay(new MyPayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);
                orderInfo.put("payUrl", resp.get("code_url"));

                resultVO = new ResultVO(ResStatus.OK, "提交订单成功", orderInfo);
            } else {
                resultVO = new ResultVO(ResStatus.NO, "提交订单失败！", null);
            }

        } catch (SQLException e) {
            resultVO = new ResultVO(ResStatus.NO, "提交订单失败！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultVO;

    }


    @GetMapping("/status/{oid}")
    public ResultVO getOrderStatus(@PathVariable("orderId") String orderId, @RequestHeader("token") String token) {
        ResultVO resultVO = orderService.getOrderById(orderId);
        return resultVO;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(dataType = "string", name = "status", value = "订单状态", required = false),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "limit", value = "每页条数", required = true)

    })
    @GetMapping("/list")
    public ResultVO list(@RequestHeader("token") String token,
                         String userId, String status, int pageNum, int limit) {
        ResultVO resultVO = orderService.listOrders(userId, status, pageNum, limit);
        return resultVO;
    }
}

