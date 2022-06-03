package com.example.demo1.service.myimplement

import com.app.project.hotel.data.connection.model.SuperManageHotelData
import com.app.project.hotel.data.connection.model.SuperManageUserData
import com.example.demo1.dao.myinterface.SuperDao
import com.example.demo1.service.myinterface.SuperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SuperServiceImpl: SuperService {
    @Autowired
    private lateinit var dao: SuperDao
    override fun login(key: String): Boolean {
        return dao.login(key)
    }

    override fun queryHotelList(
        hotelName: String?,
        hotelLocation: String?,
        hotelId: Int?,
        offset: Int?,
        sortType: Int
    ): MutableList<SuperManageHotelData> {
        return dao.queryHotelList(hotelName, hotelLocation, hotelId, offset, sortType)
    }

    override fun queryHotelRoomList(hotelId: Int): MutableList<SuperManageHotelData> {
        return dao.queryHotelRoomList(hotelId)
    }

    override fun queryUserList(userId: String?, userName: String?, offset: Int?, sortType: Int): MutableList<SuperManageUserData> {
        return dao.queryUserList(userId,userName, offset, sortType)
    }

    override fun changedRoomStatus(roomId: String, publishStatus: Int, reason: String) {
        return dao.changedRoomStatus(roomId, publishStatus, reason)
    }

    override fun changeHotelState(hotelId: String?, state: Int) {
        return dao.changeHotelState(hotelId, state)
    }

    override fun superEnterRequest(key: String): Boolean {
        return dao.superEnterRequest() == key
    }

    override fun upLoadUserState(userId: String, userState: Int) {
        dao.upLoadUserState(userId, userState)
    }
    override fun hotelEnterRequest(key: String): Boolean {
        return dao.hotelEnterRequest() == key
    }

    override fun getUserLength(): String {
        return dao.getUserLength()
    }

    override fun getHotelLength(): String {
        return dao.getHotelLength()
    }
}