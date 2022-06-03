package com.example.demo1.dao.myinterface

import com.app.project.hotel.data.connection.model.SuperManageHotelData
import com.app.project.hotel.data.connection.model.SuperManageUserData

interface SuperDao {
    fun login(key: String): Boolean
    fun queryHotelList(
        hotelName: String?,
        hotelLocation: String?,
        hotelId: Int?,
        offset: Int?,
        sortType: Int
    ): MutableList<SuperManageHotelData>

    fun queryHotelRoomList(
        hotelId: Int
    ): MutableList<SuperManageHotelData>

    fun queryUserList(userId: String?, userName: String?, offset: Int?, sortType: Int): MutableList<SuperManageUserData>
    fun changedRoomStatus(roomId: String, publishStatus: Int, reason: String)
    fun changeHotelState(hotelId: String?, state: Int)
    fun hotelEnterRequest(): String
    fun superEnterRequest(): String
    fun upLoadUserState(userId: String, userState: Int)
    fun getUserLength(): String
    fun getHotelLength(): String
}