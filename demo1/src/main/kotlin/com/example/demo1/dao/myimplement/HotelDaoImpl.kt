package com.example.demo1.dao.myimplement

import com.app.project.hotel.data.connection.model.HotelMainPageData
import com.app.project.hotel.data.connection.model.HotelManageOverViewData
import com.app.project.hotel.data.connection.model.OrderListData
import com.example.demo1.dao.myinterface.HotelDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class HotelDaoImpl : HotelDao {

    private val USER_TABLE_NAME = "user"
    private val COL_USER_ID = "user_id"
    private val COL_USER_NAME = "user_name"
    private val COL_USER_PASS = "user_pass"
    private val COL_USER_DATE = "user_date"
    private val COL_USER_ICON = "user_icon"
    private val COL_USER_STATUS = "user_state"
    private val COL_USER_LOCATION = "user_location"
    private val COL_USER_PHONE = "user_phone"
    private val COL_USER_BZ = "user_bz"
    private val COL_USER_BAD_CNT = "user_bad_cnt"

    private val HOTEL_TABLE_NAME = "hotel_user"
    private val COL_HOTEL_NAME = "hotel_name"
    private val COL_HOTEL_LOCATION = "hotel_location"
    private val COL_HOTEL_PHONE = "hotel_phone"
    private val COL_HOTEL_PASS = "hotel_pass"
    private val COL_HOTEL_ICON = "hotel_icon"
    private val COL_HOTEL_DESC = "hotel_desc"
    private val COL_HOTEL_MIN_PRICE = "hotel_min_price"
    private val COL_HOTEL_STATE = "hotel_state"
    private val COL_HOTEL_BAD_CNT = "hotel_bad_cnt"

    private val COL_HOTEL_ID = "hotel_id"

    private val ROOM_TABLE_NAME = "hotel_rooms"
    private val COL_ROOM_ID = "room_id"
    private val COL_ROOM_PUBLISHED = "room_published"
    private val COL_ROOM_NAME = "room_name"
    private val COL_ROOM_DESC = "room_desc"
    private val COL_ROOM_FEATURE = "room_feature"
    private val COL_ROOM_PRICE = "room_price"
    private val COL_ROOM_ICON = "room_icon"
    private val COL_ROOM_REASON = "reason"
    private val COL_HOTEL_CONFIRM_ROOM = "hotel_confirm"

    private val ORDER_TABLE_NAME = "user_order"
    private val COL_ORDER_TIME = "order_time"
    private val COL_START_TIME = "start_time"
    private val COL_END_TIME = "end_time"
    private val COL_ORDER_STATUS = "status"
    private val COL_ORDER_ID = "order_id"
    private val COL_ORDER_CASE = "reason"

    @Autowired
    private lateinit var ta: JdbcTemplate

    override fun getHotelBaseMsg(hotelId: Int): HotelMainPageData? {
        val sql =
            "select * from $HOTEL_TABLE_NAME " +
                    "where $COL_HOTEL_ID = $hotelId"
        var ans: HotelMainPageData? = null
        ta.queryForObject(sql) { it, _ ->
            ans = HotelMainPageData(
                hotelIcon = it.getString(COL_HOTEL_ICON),
                hotelName = it.getString(COL_HOTEL_NAME),
                hotelId = it.getInt(COL_HOTEL_ID).toString(),
                hotelLocation = it.getString(
                    COL_HOTEL_LOCATION
                ),
                hotelContactPhone = it.getString(COL_HOTEL_PHONE),
                hotelMinPrice = it.getString(COL_HOTEL_MIN_PRICE),
                hotelDesc = it.getString(COL_HOTEL_DESC),
                hotelPass = it.getString(COL_HOTEL_PASS)
            )
        }
        return ans
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
        //check
        val sql0 = "SELECT 1 from $ROOM_TABLE_NAME where $COL_ROOM_ID = '$roomId'"
        val sql: String
        var isExistsRoom = false

        ta.query(sql0) {
            if (it.getInt(1) == 1) {
                isExistsRoom = true
            }
        }
        if (isExistsRoom) {
            sql = "update $ROOM_TABLE_NAME set $COL_ROOM_ICON = '$bitmapStr'," +
                    "$COL_ROOM_NAME = '$roomName'," +
                    "$COL_ROOM_DESC = '$roomDescription'," +
                    "$COL_ROOM_FEATURE = '$roomFeature'," +
                    "$COL_ROOM_PRICE = '$roomPrice'," +
                    "$COL_ROOM_PUBLISHED = 0" +
                    " where $COL_ROOM_ID = '$roomId'"
        } else {
            sql =
                "insert into $ROOM_TABLE_NAME ($COL_HOTEL_ID,$COL_ROOM_ICON, $COL_ROOM_NAME,$COL_ROOM_DESC,$COL_ROOM_FEATURE,$COL_ROOM_PRICE, $COL_ROOM_ID, $COL_ROOM_PUBLISHED)" +
                        "values('$hotelId','$bitmapStr','$roomName','$roomDescription','$roomFeature','$roomPrice','$roomId',0)"

        }
        ta.execute(sql)
    }

    override fun getHotelOverRoomData(
        hotelId: Int
    ): Any {
        val sql = "select * from $ROOM_TABLE_NAME where $COL_HOTEL_ID = $hotelId"
        val ans = mutableListOf<HotelManageOverViewData>()
        ta.query(sql) {
            ans.add(
                HotelManageOverViewData(
                    roomIcon = it.getString(COL_ROOM_ICON),
                    roomName = it.getString(COL_ROOM_NAME),
                    roomDescription = it.getString(COL_ROOM_DESC),
                    roomFeature = it.getString(COL_ROOM_FEATURE),
                    room_id = it.getString(COL_ROOM_ID),
                    roomPrice = it.getString(COL_ROOM_PRICE),
                    room_published = it.getInt(COL_ROOM_PUBLISHED),
                    room_upLoad = true,
                    reason = it.getString(COL_ROOM_REASON),
                    hotelConfirm = it.getInt(COL_HOTEL_CONFIRM_ROOM)
                )
            )
        }
        return ans
    }

    override fun upLoadHotelIconMsg(
        iconString: String,
        hotelId: Int,
        hotelDesc: String,
        hotelMinPrice: String
    ) {
        val sql =
            "update $HOTEL_TABLE_NAME set $COL_HOTEL_ICON = '$iconString', $COL_HOTEL_DESC = '$hotelDesc', $COL_HOTEL_MIN_PRICE = '$hotelMinPrice' " +
                    "where $COL_HOTEL_ID = $hotelId"
        ta.execute(sql)
    }

    override fun hotelOrderStatusChange(orderId: String, status: Int, reason: String) {
        val sql =
            "update $ORDER_TABLE_NAME set $COL_ORDER_STATUS = $status, $COL_ORDER_CASE = '$reason' where $COL_ORDER_ID = '$orderId'"
        ta.execute(sql)
    }

    override fun hotelGoodBehavior(hotelId: Int, orderId: String) {
        val sql0 = "SELECT $COL_HOTEL_BAD_CNT FROM $HOTEL_TABLE_NAME where $COL_HOTEL_ID = $hotelId"
        val sql1  = "SELECT $COL_USER_BAD_CNT FROM $USER_TABLE_NAME where $COL_USER_ID in (\n" +
                "SELECT $COL_USER_ID FROM $ORDER_TABLE_NAME where $COL_ORDER_ID = '$orderId')"

        var ans = 0
        ta.query(sql0) {
            ans = it.getInt(COL_HOTEL_BAD_CNT)
        }
        if (ans != 0) {
            val sql =
                "update $HOTEL_TABLE_NAME set $COL_HOTEL_BAD_CNT = $COL_HOTEL_BAD_CNT - 1 where $COL_HOTEL_ID = $hotelId"
            ta.execute(sql)
        }
        ans = 0
        ta.query(sql1) {
            ans = it.getInt(COL_USER_BAD_CNT)
        }
        if (ans != 0) {
            val sql = "update $USER_TABLE_NAME set $COL_USER_BAD_CNT = $COL_USER_BAD_CNT - 1 where $COL_USER_ID in (" +
                    "SELECT $COL_USER_ID FROM $ORDER_TABLE_NAME where $COL_ORDER_ID = '$orderId')"
            ta.execute(sql)
        }
    }

    override fun hotelBadBehavior(hotelId: Int) {
        val sql =
            "update $HOTEL_TABLE_NAME set $COL_HOTEL_BAD_CNT = $COL_HOTEL_BAD_CNT + 1 where $COL_HOTEL_ID = $hotelId"
        ta.execute(sql)
    }

    override fun hotelRoomConfirm(roomId: String) {
        val sql = "update $ROOM_TABLE_NAME set $COL_HOTEL_CONFIRM_ROOM = 1 where $COL_ROOM_ID = '$roomId'"
        ta.execute(sql)
    }

    override fun getOrderLength(hotelId: Int): String? {
        val sql = "select count(*) cnt from $ORDER_TABLE_NAME where $COL_HOTEL_ID = '$hotelId'"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt("cnt")
        }
        return ans.toString()
    }

    override fun getHotelOrderList(hotelId: Int, offset: Int?): MutableList<OrderListData> {
        val sql =
            "SELECT DISTINCT a.$COL_ORDER_ID, a.$COL_USER_ID,c.$COL_USER_ICON, c.$COL_USER_NAME, c.$COL_USER_LOCATION, c.$COL_USER_PHONE, a.$COL_ROOM_ID, a.$COL_ROOM_PRICE,b.$COL_HOTEL_NAME, " +
                    "b.$COL_HOTEL_LOCATION,b.$COL_HOTEL_PHONE,b.$COL_HOTEL_ID, a.$COL_ORDER_TIME, a.$COL_START_TIME, a.$COL_END_TIME, a.$COL_ORDER_STATUS, d.$COL_ROOM_NAME, d.$COL_ROOM_DESC, d.$COL_ROOM_FEATURE,d.$COL_ROOM_ICON " +
                    "FROM $ORDER_TABLE_NAME a " +
                    "JOIN $HOTEL_TABLE_NAME b on a.$COL_HOTEL_ID = b.$COL_HOTEL_ID " +
                    "JOIN $USER_TABLE_NAME c on a.$COL_USER_ID = c.$COL_USER_ID " +
                    "JOIN $ROOM_TABLE_NAME d on d.$COL_ROOM_ID = a.$COL_ROOM_ID " +
                    " where a.$COL_HOTEL_ID = '$hotelId' order by a.$COL_ORDER_TIME desc limit 5 offset ${offset?:0}"

        val ans = mutableListOf<OrderListData>()
        ta.query(sql) {
            ans.add(
                OrderListData(
                    orderId = it.getString(COL_ORDER_ID),
                    userId = it.getString(COL_USER_ID),
                    userName = it.getString(COL_USER_NAME),
                    roomId = it.getString(
                        COL_ROOM_ID
                    ),
                    roomPrice = it.getString(COL_ROOM_PRICE),
                    hotelName = it.getString(
                        COL_HOTEL_NAME
                    ),
                    hotelLocation = it.getString(COL_HOTEL_LOCATION),
                    orderTime = it.getString(
                        COL_ORDER_TIME
                    ),
                    startTime = it.getString(COL_START_TIME),
                    endTime = it.getString(
                        COL_END_TIME
                    ),
                    status = it.getInt(COL_ORDER_STATUS),
                    userLocation = it.getString(COL_USER_LOCATION),
                    userPhone = it.getString(COL_USER_PHONE),
                    hotelPhone = it.getString(COL_HOTEL_PHONE),
                    roomName = it.getString(COL_ROOM_NAME),
                    roomDesc = it.getString(COL_ROOM_DESC),
                    roomFeature = it.getString(COL_ROOM_FEATURE),
                    hotelId = it.getString(COL_HOTEL_ID),
                    userIcon = it.getString(COL_USER_ICON),
                    roomIcon = it.getString(COL_ROOM_ICON)
                )
            )
        }
        return ans
    }
}