package com.example.demo1.dao.myimplement

import com.app.project.hotel.data.connection.model.SuperManageHotelData
import com.app.project.hotel.data.connection.model.SuperManageUserData
import com.example.demo1.dao.myinterface.SuperDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SuperDaoImpl : SuperDao {
    private val ROOT_PASS_TABLE_NAME = "root_pass"
    private val COL_CREDIT_KEY = "credit_key"
    private val COL_HOTEL_KEY = "hotel_key"
    private val COL_SUPER_KEY = "super_key"

    private val HOTEL_TABLE_NAME = "hotel_user"
    private val COL_HOTEL_NAME = "hotel_name"
    private val COL_HOTEL_LOCATION = "hotel_location"
    private val COL_HOTEL_PHONE = "hotel_phone"
    private val COL_HOTEL_ID = "hotel_id"
    private val COL_HOTEL_PASS = "hotel_pass"
    private val COL_HOTEL_STATE = "hotel_state"
    private val COL_HOTEL_BAD_CNT = "hotel_bad_cnt"

    private val ROOM_TABLE_NAME = "hotel_rooms"
    private val COL_ROOM_ICON = "room_icon"
    private val COL_ROOM_NAME = "room_name"
    private val COL_ROOM_DESC = "room_desc"
    private val COL_ROOM_FEATURE = "room_feature"
    private val COL_ROOM_PRICE = "room_price"
    private val COL_ROOM_ID = "room_id"
    private val COL_ROOM_PUBLISHED = "room_published"
    private val COL_ROOM_REASON = "reason"
    private val COL_HOTEL_CONFIRM = "hotel_confirm"

    private val USER_TABLE_NAME = "user"
    private val COL_USER_ID = "user_id"
    private val COL_USER_NAME = "user_name"
    private val COL_USER_PASS = "user_pass"
    private val COL_USER_DATE = "user_date"
    private val COL_USER_ICON = "user_icon"
    private val COL_USER_STATUS = "user_state"
    private val COL_USER_PHONE = "user_phone"
    private val COL_USER_LOCATION = "user_location"
    private val COL_USER_BAD_CNT = "user_bad_cnt"

    @Autowired
    private lateinit var ta: JdbcTemplate

    override fun login(key: String): Boolean {
        val sql = "select $COL_CREDIT_KEY from $ROOT_PASS_TABLE_NAME"
        var rk = ""
        ta.query(sql) {
            rk = it.getString(COL_CREDIT_KEY)
        }
        return key == rk
    }


    //查询酒店列表
    override fun queryHotelList(
        hotelName: String?,
        hotelLocation: String?,
        hotelId: Int?,
        offset: Int?,
        sortType: Int
    ): MutableList<SuperManageHotelData> {
        val sql = if (sortType == 0) {
            "select * from $HOTEL_TABLE_NAME " +
                    "where ($COL_HOTEL_NAME like '%${hotelName ?: ""}%'" +
                    " and $COL_HOTEL_LOCATION like '%${hotelLocation ?: ""}%'" +
                    " and $COL_HOTEL_ID like '%${hotelId ?: ""}%') and id > 1" +
                    " order by $COL_HOTEL_BAD_CNT limit 5 offset ${offset ?: 0}"
        } else {
            "select * from $HOTEL_TABLE_NAME " +
                    "where ($COL_HOTEL_NAME like '%${hotelName ?: ""}%'" +
                    " and $COL_HOTEL_LOCATION like '%${hotelLocation ?: ""}%'" +
                    " and $COL_HOTEL_ID like '%${hotelId ?: ""}%') and id > 1" +
                    " order by $COL_HOTEL_BAD_CNT desc limit 5 offset ${offset ?: 0}"
        }

        val ans = mutableListOf<SuperManageHotelData>()
        ta.query(sql) {
            ans.add(
                SuperManageHotelData(
                    hotelName = it.getString(COL_HOTEL_NAME),
                    hotelLocation = it.getString(COL_HOTEL_LOCATION),
                    hotelPhone = it.getString(COL_HOTEL_PHONE),
                    hotelId = it.getInt(COL_HOTEL_ID).toString(),
                    hotelPass = it.getString(COL_HOTEL_PASS),
                    isParent = true,
                    foldState = true,
                    hotelBadCnt = it.getInt(COL_HOTEL_BAD_CNT),
                    hotelState = it.getInt(COL_HOTEL_STATE),
                    childSize = 0
                )
            )
        }
        return ans
    }

    //查询酒店所有房间列表--子可折叠展开recyclerview
    override fun queryHotelRoomList(
        hotelId: Int
    ): MutableList<SuperManageHotelData> {
        val sql = "select * from $ROOM_TABLE_NAME where $COL_HOTEL_ID = $hotelId ORDER BY room_id"
        val ans = mutableListOf<SuperManageHotelData>()
        var childSize = 0
        ta.query(sql) {
            ans.add(
                SuperManageHotelData(
                    hotelId = it.getInt(COL_HOTEL_ID).toString(),
                    roomIcon = it.getString(COL_ROOM_ICON),
                    roomName = it.getString(COL_ROOM_NAME),
                    roomDescription = it.getString(COL_ROOM_DESC),
                    roomFeature = it.getString(COL_ROOM_FEATURE),
                    roomPrice = it.getString(COL_ROOM_PRICE),
                    room_id = it.getString(COL_ROOM_ID),
                    room_published = it.getInt(COL_ROOM_PUBLISHED),
                    isParent = false
                )
            )
            childSize++
        }
        ans.forEach {
            it.childSize = childSize
        }
        return ans
    }

    override fun queryUserList(userId: String?, userName: String?, offset: Int?, sortType: Int): MutableList<SuperManageUserData> {
        val sql = if (sortType == 0) {
            "select * from $USER_TABLE_NAME where $COL_USER_ID like '%${userId ?: ""}%' and $COL_USER_NAME like '%${userName ?: ""}%' order by $COL_USER_BAD_CNT limit 10 offset ${offset ?: 0}"
        } else {
            "select * from $USER_TABLE_NAME where $COL_USER_ID like '%${userId ?: ""}%' and $COL_USER_NAME like '%${userName ?: ""}%' order by $COL_USER_BAD_CNT desc limit 10 offset ${offset ?: 0}"
        }
            val ans = mutableListOf<SuperManageUserData>()
        ta.query(sql) {
            ans.add(
                SuperManageUserData(
                    userName = it.getString(COL_USER_NAME),
                    userPass = it.getString(COL_USER_PASS),
                    userIcon = it.getString(COL_USER_ICON),
                    userStatus = it.getInt(COL_USER_STATUS),
                    userDate = it.getString(COL_USER_DATE),
                    userId = it.getString(COL_USER_ID),
                    userPhone = it.getString(COL_USER_PHONE),
                    userLocation = it.getString(COL_USER_LOCATION),
                    userBadCnt = it.getInt(COL_USER_BAD_CNT)
                )
            )
        }
        return ans
    }

    override fun changedRoomStatus(roomId: String, publishStatus: Int, reason: String) {
        val sql =
            "update $ROOM_TABLE_NAME set $COL_ROOM_PUBLISHED = $publishStatus,$COL_ROOM_REASON = '$reason', $COL_HOTEL_CONFIRM = 0 where $COL_ROOM_ID = '$roomId'"
        ta.execute(sql)
    }

    override fun changeHotelState(hotelId: String?, state: Int) {
        val sql = "update $HOTEL_TABLE_NAME set $COL_HOTEL_STATE = $state where $COL_HOTEL_ID = $hotelId"
        ta.execute(sql)
    }

    override fun superEnterRequest(): String {
        val sql = "select $COL_SUPER_KEY from $ROOT_PASS_TABLE_NAME"
        var ans = ""
        ta.query(sql) {
            ans = it.getString(COL_SUPER_KEY)
        }
        return ans
    }

    override fun upLoadUserState(userId: String, userState: Int) {
        val sql = "update $USER_TABLE_NAME set $COL_USER_STATUS = $userState where $COL_USER_ID = '$userId'"
        ta.execute(sql)
    }

    override fun getUserLength(): String {
        val sql = "select count(*) cnt from $USER_TABLE_NAME"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt("cnt")
        }
        return ans.toString()
    }

    override fun getHotelLength(): String {
        val sql = "select count(*) cnt from $HOTEL_TABLE_NAME"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt("cnt") -1
        }
        return ans.toString()
    }

    override fun hotelEnterRequest(): String {
        val sql = "select $COL_HOTEL_KEY from $ROOT_PASS_TABLE_NAME"
        var ans = ""
        ta.query(sql) {
            ans = it.getString(COL_HOTEL_KEY)
        }
        return ans
    }
}