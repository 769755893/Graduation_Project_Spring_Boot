package com.example.demo1.service.myinterface

import com.app.project.hotel.data.connection.model.OrderListData
import com.app.project.hotel.data.connection.model.ProFileData
import com.app.project.hotel.data.connection.model.UserHotelRoomData
import com.example.demo1.model.HotelCommentData
import com.example.demo1.model.HotelListData

interface UserService {
    fun getHotelList(sortType: Int, offset: Int, keyWord: String):MutableList<HotelListData>

    fun getHotelRoomList(
        hotelId: Int
    ): MutableList<UserHotelRoomData>

    fun getUserMsg(userId: String): ProFileData

    fun upLoadUserMsg(
        userIcon: String,
        userName: String?,
        userPass: String?,
        userLocation: String?,
        userPhone: String?,
        userId: String?,
        userBz: String?
    )

    fun updateOrderState(orderId: String, state: Int)

    fun orderPay(
        hotelId: String,
        userId: String,
        roomId: String,
        starTime: String,
        endTime: String,
        roomPrice: String
    )

    fun getOrderList(userId: String, offset: Int?, orderTimeType: Int):MutableList<OrderListData>

    fun cancelOrder(orderId: String, userId: String)

    fun getHotelCommentList(hotelId: Int):MutableList<HotelCommentData>

    fun goodClick(commentId: String)

    fun hotelOrderDone(orderId: String)

    fun upLoadUserComment(
        orderId: String,
        commentId: String,
        hotelId: Int?,
        userId: String?,
        userComment: String?,
        userCommentScore: Int,
        startTime: String?,
        roomId: String?
    )

    fun userOrderConfirm(orderId: String)
    fun getOrderLength(userId: String): String?
    fun userRecharge(userId: String, rechargeKey: String): Boolean
    fun getUserMoney(userId: String): String
    fun userPay(userId: String, money: Int): Boolean
    fun getHotelCount(): String
    fun getPassOrderLength(userId: String): String?
}