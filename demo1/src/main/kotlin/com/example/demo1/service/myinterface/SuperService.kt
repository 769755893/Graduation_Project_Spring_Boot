package com.example.demo1.service.myinterface

import com.app.project.hotel.data.connection.model.SuperManageHotelData
import com.app.project.hotel.data.connection.model.SuperManageUserData

interface SuperService {
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
    fun hotelEnterRequest(key: String): Boolean
    fun superEnterRequest(key: String): Boolean
    fun upLoadUserState(userId: String, userState: Int)
    fun getUserLength(): String
    fun getHotelLength(): String
}