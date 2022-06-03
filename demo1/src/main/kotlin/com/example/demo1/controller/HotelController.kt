package com.example.demo1.controller

import com.alibaba.fastjson.JSON
import com.example.demo1.base.model.BaseResponse
import com.example.demo1.service.myinterface.HotelService
import com.fasterxml.jackson.databind.ser.Serializers.Base
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hotel")
class HotelController {
    @Autowired
    private lateinit var service: HotelService


    @RequestMapping("getOrderLength")
    @ResponseBody
    fun getOrderLength(hotelId: Int): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getOrderLength(hotelId)
            )
        )
    }

    @RequestMapping("getHotelBaseMsg")
    @ResponseBody
    fun getHotelBaseMsg(hotelId: Int): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelBaseMsg(hotelId)
            )
        )
    }

    @RequestMapping("getHotelOrderList")
    @ResponseBody
    fun getHotelOrderList(
        hotelId: Int,
        offset: Int? = 0
    ): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelOrderList(hotelId, offset)
            )
        )
    }

    @RequestMapping("upLoadRoomMsg")
    @ResponseBody
    fun upLoadRoomMsg(
        hotelId: Int,
        bitmapStr: String,
        roomName: String,
        roomDescription: String,
        roomFeature: String,
        roomPrice: Int,
        roomId: String
    ): Any {
        service.upLoadRoomMsg(hotelId, bitmapStr, roomName, roomDescription, roomFeature, roomPrice, roomId)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("getHotelOverRoomData")
    @ResponseBody
    fun getHotelOverRoomData(
        hotelId: Int
    ): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelOverRoomData(hotelId)
            )
        )
    }

    @RequestMapping("hotelRoomConfirm")
    @ResponseBody
    fun hotelRoomConfirm(
        roomId: String
    ): Any {
        service.hotelRoomConfirm(roomId)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("upLoadHotelIconMsg")
    @ResponseBody
    fun upLoadHotelIconMsg(
        iconString: String,
        hotelId: Int,
        hotelDesc: String,
        hotelMinPrice: String
    ): Any {
        service.upLoadHotelIconMsg(iconString, hotelId, hotelDesc, hotelMinPrice)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("orderReject")
    @ResponseBody
    fun orderReject(
        orderId: String,
        hotelId: Int,
        reason: String
    ): Any {
        service.orderReject(orderId, hotelId, reason)
        return JSON.toJSON(
            BaseResponse(
                msg = "订单已拒绝"
            )
        )
    }

    @RequestMapping("orderConfirm")
    @ResponseBody
    fun orderConfirm(
        orderId: String
    ): Any {
        service.orderConfirm(orderId)
        return JSON.toJSON(
            BaseResponse(
                msg = "订单已确认"
            )
        )
    }

    @RequestMapping("orderDone")
    @ResponseBody
    fun orderDone(
        orderId: String,
        hotelId: Int
    ): Any {
        service.orderDone(orderId, hotelId)
        return JSON.toJSON(
            BaseResponse(
                msg = "订单已完成"
            )
        )
    }
}