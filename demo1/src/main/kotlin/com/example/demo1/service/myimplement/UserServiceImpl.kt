package com.example.demo1.service.myimplement

import com.app.project.hotel.data.connection.model.OrderListData
import com.app.project.hotel.data.connection.model.ProFileData
import com.app.project.hotel.data.connection.model.UserHotelRoomData
import com.example.demo1.dao.myinterface.UserDao
import com.example.demo1.model.HotelCommentData
import com.example.demo1.model.HotelListData
import com.example.demo1.service.myinterface.UserService
import com.example.demo1.base.util.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var dao: UserDao

    override fun getHotelList(sortType: Int, offset: Int, keyWord: String): MutableList<HotelListData> {
        return dao.getHotelList(sortType, offset, keyWord)
    }

    override fun getHotelRoomList(hotelId: Int): MutableList<UserHotelRoomData> {
        return dao.getHotelRoomList(hotelId)
    }

    override fun getUserMsg(userId: String): ProFileData {
        return dao.getUserMsg(userId)
    }

    override fun upLoadUserMsg(
        userIcon: String,
        userName: String?,
        userPass: String?,
        userLocation: String?,
        userPhone: String?,
        userId: String?,
        userBz: String?
    ) {
        return dao.upLoadUserMsg(userIcon, userName, userPass, userLocation, userPhone, userId, userBz)
    }

    override fun updateOrderState(orderId: String, state: Int) {
        return dao.updateOrderState(orderId, state)
    }

    override fun orderPay(
        hotelId: String,
        userId: String,
        roomId: String,
        starTime: String,
        endTime: String,
        roomPrice: String
    ) {
        val nowTime = Utils.getNowTimeString()
        val orderTimeID = userId + roomId + nowTime.filter {
            it != ' ' && it != '-' && it != ':'
        }
        return dao.orderPay(
            hotelId,
            userId,
            roomId,
            starTime.replace('年', '-').replace('月', '-').replace('日', '-').replace("\\s".toRegex(), "").dropLast(1),
            endTime.replace('年', '-').replace('月', '-').replace('日', '-').replace("\\s".toRegex(), "").dropLast(1),
            roomPrice,
            nowTime,
            orderTimeID
        )
    }

    override fun getOrderList(userId: String, offset: Int?, orderTimeType: Int): MutableList<OrderListData> {
        return dao.getUserOrderList(userId, offset, orderTimeType)
    }

    override fun cancelOrder(orderId: String, userId: String) {
        dao.badBehavior(userId)
        return dao.userCancelOrder(orderId)
    }

    override fun getHotelCommentList(hotelId: Int): MutableList<HotelCommentData> {
        return dao.getHotelCommentList(hotelId)
    }

    override fun goodClick(commentId: String) {
        return dao.goodClick(commentId)
    }

    override fun hotelOrderDone(orderId: String) {
        return dao.hotelOrderDone(orderId)
    }

    override fun userOrderConfirm(orderId: String) {
        return dao.userOrderConfirm(orderId)
    }

    override fun getOrderLength(userId: String): String? {
        return dao.getOrderLength(userId)
    }

    override fun userRecharge(userId: String, rechargeKey: String): Boolean {
        val money = dao.getTypeMoney(rechargeKey)
        if (money < 100) return false
        dao.userRecharge(userId, money)
        return true
    }

    override fun getUserMoney(userId: String): String {
        return dao.getUserMoney(userId).toString()
    }

    override fun userPay(userId: String, money: Int): Boolean {
        val Money = dao.getUserMoney(userId)
        if (Money < money) return false
        dao.userPay(userId, money)
        return true
    }

    override fun getHotelCount(): String {
        return dao.getHotelCount()
    }

    override fun getPassOrderLength(userId: String): String? {
        return dao.getPassOrderLength(userId)
    }

    override fun upLoadUserComment(
        orderId: String,
        commentId: String,
        hotelId: Int?,
        userId: String?,
        userComment: String?,
        userCommentScore: Int,
        startTime: String?,
        roomId: String?
    ) {
        return dao.upLoadUserComment(
            orderId, commentId, hotelId, userId, userComment, userCommentScore, startTime, roomId
        )
    }

}