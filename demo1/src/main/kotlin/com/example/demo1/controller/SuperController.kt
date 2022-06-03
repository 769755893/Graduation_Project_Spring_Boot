package com.example.demo1.controller

import com.alibaba.fastjson.JSON
import com.example.demo1.base.model.BaseResponse
import com.example.demo1.service.myinterface.SuperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/super")
class SuperController {
    @Autowired
    private lateinit var service: SuperService


    @RequestMapping("/getUserLength")
    @ResponseBody
    fun getUserLength(): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getUserLength()
            )
        )
    }

    @RequestMapping("/getHotelLength")
    @ResponseBody
    fun getHotelLength(): Any? {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.getHotelLength()
            )
        )
    }


    @RequestMapping("/upLoadUserState")
    @ResponseBody
    fun upLoadUserState(userId: String, userState: Int): Any? {
        service.upLoadUserState(userId, userState)
        return JSON.toJSON(
            BaseResponse(
                msg = "成功"
            )
        )
    }

    @RequestMapping("/hotelEnterRequest")
    @ResponseBody
    fun hotelEnterRequest(key: String): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = if (service.hotelEnterRequest(key)) "true" else "false"
            )
        )
    }

    @RequestMapping("superEnterRequest")
    @ResponseBody
    fun superEnterRequest(key: String): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = if (service.superEnterRequest(key)) "true" else "false"
            )
        )
    }

    @RequestMapping("/login")
    @ResponseBody
    fun login(key: String): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = if (service.login(key)) "true" else "false"
            )
        )
    }

    @RequestMapping("/queryHotelList")
    @ResponseBody
    fun queryHotelList(//args nullable means can be dropped in url
        hotelId: Int?,
        hotelName: String?,
        hotelLocation: String?,
        offset: Int?,
        sortType: Int
    ): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.queryHotelList(hotelName, hotelLocation, hotelId, offset, sortType)
            )
        )
    }

    @RequestMapping("/queryHotelRoomList")
    @ResponseBody
    fun queryHotelRoomList(
        hotelId: Int
    ): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.queryHotelRoomList(hotelId)
            )
        )
    }

    @RequestMapping("/queryUserList")
    @ResponseBody
    fun queryUserList(userId: String?, userName: String?, offset: Int?, sortType: Int): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.queryUserList(userId, userName, offset, sortType)
            )
        )
    }

    @RequestMapping("/changedRoomStatus")
    @ResponseBody
    fun changedRoomStatus(roomId: String, publishStatus: Int, reason: String): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.changedRoomStatus(roomId, publishStatus, reason)
            )
        )
    }

    @RequestMapping("/changeHotelState")
    @ResponseBody
    fun changeHotelState(hotelId: String?, state: Int): Any {
        return JSON.toJSON(
            BaseResponse(
                msg = "成功",
                data = service.changeHotelState(hotelId, state)
            )
        )
    }
}