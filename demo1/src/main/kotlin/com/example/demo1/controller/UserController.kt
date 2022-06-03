package com.example.demo1.controller

import com.alibaba.fastjson.JSON
import com.example.demo1.base.model.BaseResponse
import com.example.demo1.service.myinterface.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var service: UserService

    @RequestMapping("/userOrderConfirm")
    @ResponseBody
    fun userOrderConfirm(orderId: String): Any? {
        service.userOrderConfirm(orderId)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("/userRecharge")
    @ResponseBody
    fun userRecharge(userId: String, rechargeKey: String): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = if (service.userRecharge(userId, rechargeKey)) "充值成功" else "充值失败"
            )
        )
    }

    @RequestMapping("/getUserMoney")
    @ResponseBody
    fun getUserMoney(userId: String): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getUserMoney(userId)
            )
        )
    }

    @RequestMapping("/userPay")
    @ResponseBody
    fun userPay(userId: String, money: Int): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = if (service.userPay(userId, money)) "支付成功" else "支付失败"
            )
        )
    }


    @RequestMapping("/getHotelList")
    @ResponseBody
    fun getHotelList(sortType: Int, offset: Int, keyWord: String? = ""): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelList(sortType, offset, keyWord ?: "")
            )
        )
    }

    @RequestMapping("/getHotelCount")
    @ResponseBody
    fun getHotelCount(): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelCount()
            )
        )
    }

    @RequestMapping("/getHotelRoomList")
    @ResponseBody
    fun getHotelRoomList(
        hotelId: Int
    ): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelRoomList(hotelId)
            )
        )
    }

    @RequestMapping("/getUserMsg")
    @ResponseBody
    fun getUserMsg(userId: String): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getUserMsg(userId)
            )
        )
    }

    @RequestMapping("/upLoadUserMsg")
    @ResponseBody
    fun upLoadUserMsg(
        userIcon: String,
        userName: String?,
        userPass: String?,
        userLocation: String?,
        userPhone: String?,
        userId: String?,
        userBz: String?
    ): Any {
        service.upLoadUserMsg(userIcon, userName, userPass, userLocation, userPhone, userId, userBz)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
            )
        )
    }

    @RequestMapping("/updateOrderState")
    @ResponseBody
    fun updateOrderState(orderId: String, state: Int): Any {
        service.updateOrderState(orderId, state)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("/orderPay")
    @ResponseBody
    fun orderPay(
        hotelId: String,
        userId: String,
        roomId: String,
        startTime: String,
        endTime: String,
        roomPrice: String
    ): Any {
        service.orderPay(hotelId, userId, roomId, startTime, endTime, roomPrice)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("/getOrderList")
    @ResponseBody
    fun getOrderList(userId: String, offset: Int? = null, orderTimeType: Int? = 0): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getOrderList(userId, offset, orderTimeType!!)
            )
        )
    }

    @RequestMapping("/getPassOrderLength")
    @ResponseBody
    fun getPassOrderLength(userId: String): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getPassOrderLength(userId)
            )
        )
    }

    @RequestMapping("/getOrderLength")
    @ResponseBody
    fun getOrderLength(userId: String): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getOrderLength(userId)
            )
        )

    }

    @RequestMapping("/cancelOrder")
    @ResponseBody
    fun cancelOrder(orderId: String, userId: String): Any {
        service.cancelOrder(orderId, userId)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("/getHotelCommentList")
    @ResponseBody
    fun getHotelCommentList(hotelId: Int): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelCommentList(hotelId)
            )
        )
    }

    @RequestMapping("/goodClick")
    @ResponseBody
    fun goodClick(commentId: String): Any {
        service.goodClick(commentId)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("/hotelOrderDone")
    @ResponseBody
    fun hotelOrderDone(orderId: String): Any {
        service.hotelOrderDone(orderId)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("/upLoadUserComment")
    @ResponseBody
    fun upLoadUserComment(
        orderId: String,
        commentId: String,
        hotelId: Int?,
        userId: String?,
        userComment: String?,
        userCommentScore: Int,
        startTime: String?,
        roomId: String?,
    ): Any {
        service.upLoadUserComment(
            orderId,
            commentId,
            hotelId,
            userId,
            userComment,
            userCommentScore,
            startTime,
            roomId
        )
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }
}