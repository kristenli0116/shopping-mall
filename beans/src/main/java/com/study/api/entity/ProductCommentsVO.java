package com.study.api.entity;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @auther lkx
 * @create 2022-05-06 19:06
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductCommentsVO {
    @Id
    @Column(name = "comm_id")
    private String commId;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "order_item_id")
    private String orderItemId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "is_anonymous")
    private Integer isAnonymous;
    @Column(name = "comm_type")
    private Integer commType;
    @Column(name = "comm_level")
    private Integer commLevel;
    @Column(name = "comm_content")
    private String commContent;
    @Column(name = "comm_imgs")
    private String commImgs;
    @Column(name = "sepc_name")
    private Date sepcName;
    @Column(name = "reply_status")
    private Integer replyStatus;
    @Column(name = "reply_content")
    private String replyContent;
    @Column(name = "reply_time")
    private Date replyTime;
    @Column(name = "is_show")
    private Integer isShow;

    private String username;
    private String nickname;
    private String userImg;
}
