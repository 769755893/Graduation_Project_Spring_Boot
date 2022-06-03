package com.example.demo1.service.myimplement

import com.app.project.hotel.data.connection.model.HotelMainPageData
import com.app.project.hotel.data.connection.model.OrderListData
import com.example.demo1.dao.myinterface.HotelDao
import com.example.demo1.service.myinterface.HotelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HotelServiceImpl: HotelService {
    @Autowired
    private lateinit var dao: HotelDao
    override fun getHotelBaseMsg(hotelId: Int): HotelMainPageData? {
        return dao.getHotelBaseMsg(hotelId)
    }

    override fun upLoadRoomMsg(
        hotelId: Int,
        bitmapStr: String,
        roomName: String,
        roomDescription: String,
        roomFeature: String,
        roomPrice: Int,
        roomId: String
    ) {
        return dao.upLoadRoomMsg(hotelId, bitmapStr, roomName, roomDescription, roomFeature, roomPrice, roomId)
    }

    override fun getHotelOverRoomData(hotelId: Int): Any {
        return dao.getHotelOverRoomData(hotelId)
    }

    override fun getHotelOrderList(hotelId: Int, offset: Int?): MutableList<OrderListData> {
        return dao.getHotelOrderList(hotelId, offset)
    }

    override fun upLoadHotelIconMsg(iconString: String, hotelId: Int, hotelDesc: String, hotelMinPrice: String) {
        return dao.upLoadHotelIconMsg(iconString, hotelId, hotelDesc, hotelMinPrice)
    }

    override fun orderReject(orderId: String, hotelId: Int, reason: String) {
        dao.hotelBadBehavior(hotelId)
        return dao.hotelOrderStatusChange(orderId, 2, reason)
    }

    override fun orderConfirm(orderId: String) {
        return dao.hotelOrderStatusChange(orderId, 1, "")
    }

    override fun hotelRoomConfirm(roomId: String) {
        dao.hotelRoomConfirm(roomId)
    }

    override fun getOrderLength(hotelId: Int): String? {
        return dao.getOrderLength(hotelId)
    }

    override fun orderDone(orderId: String, hotelId: Int) {
        dao.hotelGoodBehavior(hotelId, orderId)
        return dao.hotelOrderStatusChange(orderId, 3, "")
    }
}