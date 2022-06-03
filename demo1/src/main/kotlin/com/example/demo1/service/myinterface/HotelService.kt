package com.example.demo1.service.myinterface

import com.app.project.hotel.data.connection.model.HotelMainPageData
import com.app.project.hotel.data.connection.model.OrderListData
import com.example.demo1.model.HotelCommentData

interface HotelService {
    fun getHotelBaseMsg(hotelId: Int): HotelMainPageData?

    fun upLoadRoomMsg(
        hotelId: Int,
        bitmapStr: String,
        roomName: String,
        roomDescription: String,
        roomFeature: String,
        roomPrice: Int,
        roomId: String
    )

    fun getHotelOrderList(hotelId: Int, offset: Int?): MutableList<OrderListData>

    fun getHotelOverRoomData(
        hotelId: Int
    ): Any

    fun upLoadHotelIconMsg(
        iconString: String,
        hotelId: Int,
        hotelDesc: String,
        hotelMinPrice: String
    )

    fun orderConfirm(orderId: String)

    fun orderDone(orderId: String, hotelId: Int)

    fun orderReject(orderId: String, hotelId: Int, reason: String)
    fun hotelRoomConfirm(roomId: String)
    fun getOrderLength(hotelId: Int): String?
}