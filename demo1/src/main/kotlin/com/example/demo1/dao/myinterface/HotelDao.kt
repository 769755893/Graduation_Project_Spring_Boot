package com.example.demo1.dao.myinterface

import com.app.project.hotel.data.connection.model.HotelMainPageData
import com.app.project.hotel.data.connection.model.OrderListData

interface HotelDao {

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

    fun getHotelOverRoomData(
        hotelId: Int
    ): Any

    fun upLoadHotelIconMsg(
        iconString: String,
        hotelId: Int,
        hotelDesc: String,
        hotelMinPrice: String
    )

    fun hotelBadBehavior(hotelId: Int)

    fun hotelGoodBehavior(hotelId: Int, orderId: String)

    fun hotelOrderStatusChange(orderId: String, status: Int, reason: String)

    fun getHotelOrderList(hotelId: Int, offset: Int?): MutableList<OrderListData>
    fun hotelRoomConfirm(roomId: String)
    fun getOrderLength(hotelId: Int): String?

}