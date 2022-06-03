package com.example.demo1.dao.myimplement

import com.app.project.hotel.data.connection.model.OrderListData
import com.app.project.hotel.data.connection.model.ProFileData
import com.app.project.hotel.data.connection.model.UserHotelRoomData
import com.example.demo1.base.util.Utils
import com.example.demo1.base.util.log
import com.example.demo1.dao.myinterface.UserDao
import com.example.demo1.model.HotelCommentData
import com.example.demo1.model.HotelListData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.text.DateFormat
import java.text.SimpleDateFormat

@Repository
class UserDaoImpl : UserDao {

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
    private val COL_HOTEL_ID = "hotel_id"
    private val COL_HOTEL_NAME = "hotel_name"
    private val COL_HOTEL_PASS = "hotel_pass"
    private val COL_HOTEL_ICON = "hotel_icon"
    private val COL_HOTEL_LOCATION = "hotel_location"
    private val COL_HOTEL_DESC = "hotel_desc"
    private val COL_HOTEL_PHONE = "hotel_phone"
    private val COL_HOTEL_MIN_PRICE = "hotel_min_price"
    private val COL_HOTEL_STATE = "hotel_state"
    private val COL_HOTEL_BAD_CNT = "hotel_bad_cnt"
    private val COL_HOTEL_AVG_SCORE = "hotel_avg_score"


    private val ROOM_TABLE_NAME = "hotel_rooms"
    private val COL_ROOM_ICON = "room_icon"
    private val COL_ROOM_NAME = "room_name"
    private val COL_ROOM_DESC = "room_desc"
    private val COL_ROOM_FEATURE = "room_feature"
    private val COL_ROOM_PRICE = "room_price"
    private val COL_ROOM_ID = "room_id"
    private val COL_ROOM_PUBLISHED = "room_published"

    private val ORDER_TABLE_NAME = "user_order"
    private val COL_ORDER_TIME = "order_time"
    private val COL_START_TIME = "start_time"
    private val COL_END_TIME = "end_time"
    private val COL_ORDER_STATUS = "status"
    private val COL_ORDER_ID = "order_id"
    private val COL_USER_CONFIRM = "user_confirm"
    private val COL_ORDER_REASON = "reason"

    private val USER_COMMENT_TABLE_NAME = "user_comment"
    private val COL_COMMENT_ID = "comment_id"
    private val COL_USER_COMMENT = "user_comment"
    private val COL_USER_COMMENT_SCORE = "user_comment_score"
    private val COL_GOOD_CNT = "good_cnt"

    private val TABLE_USER_ACCOUNT = "pay_account"
    private val COL_USER_MONEY = "user_money"

    private val TABLE_RECHARGE_TYPE = "recharge_type"
    private val COL_RECHAREGE_KEY = "recharge_key"
    private val COL_RECHAREGE_MONEY = "money"
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @Autowired
    private lateinit var ta: JdbcTemplate

    override fun queryUserMoeny(userId: String): Int {
        val sql = "select $COL_USER_MONEY from $TABLE_USER_ACCOUNT where $COL_USER_ID = '$userId'"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt(COL_USER_MONEY)
        }
        return ans
    }

    override fun userRecharge(userId: String, money: Int) {
        val sql =
            "update $TABLE_USER_ACCOUNT set $COL_USER_MONEY = $COL_USER_MONEY + $money where $COL_USER_ID = '$userId'"
        ta.execute(sql)
    }

    override fun getHotelList(sortType: Int, offset: Int, keyWord: String): MutableList<HotelListData> {
        var sql =
            "select DISTINCT a.* from $HOTEL_TABLE_NAME a join $ROOM_TABLE_NAME b on a.$COL_HOTEL_ID = b.$COL_HOTEL_ID and b.$COL_ROOM_PUBLISHED = 1 and (a.$COL_HOTEL_NAME like '%$keyWord%' or a.$COL_HOTEL_LOCATION like '%$keyWord%') where a.id > 1"
        if (sortType == 1) {
            sql =
                "select DISTINCT a.* from $HOTEL_TABLE_NAME a join $ROOM_TABLE_NAME b on a.$COL_HOTEL_ID = b.$COL_HOTEL_ID" +
                        " and b.$COL_ROOM_PUBLISHED = 1 and (a.$COL_HOTEL_NAME like '%$keyWord%' or a.$COL_HOTEL_LOCATION like '%$keyWord%') where a.id > 1 order by $COL_HOTEL_MIN_PRICE"
        } else if (sortType == 2) {
            sql =
                "select DISTINCT a.* from $HOTEL_TABLE_NAME a join $ROOM_TABLE_NAME b on a.$COL_HOTEL_ID = b.$COL_HOTEL_ID" +
                        " and b.$COL_ROOM_PUBLISHED = 1 and (a.$COL_HOTEL_NAME like '%$keyWord%' or a.$COL_HOTEL_LOCATION like '%$keyWord%') where a.id > 1 order by $COL_HOTEL_AVG_SCORE desc"
        }

        sql.log()
        var ans = mutableListOf<HotelListData>()
        ta.query(sql) {
            ans.add(
                HotelListData(
                    hotelId = it.getInt(COL_HOTEL_ID).toString(),
                    hotelName = it.getString(COL_HOTEL_NAME),
                    hotelPass = it.getString(COL_HOTEL_PASS),
                    hotelIcon = it.getString(COL_HOTEL_ICON),
                    hotelLocation = it.getString(COL_HOTEL_LOCATION),
                    hotelPhone = it.getString(COL_HOTEL_PHONE),
                    hotelDesc = it.getString(COL_HOTEL_DESC),
                    hotelMinPrice = it.getString(COL_HOTEL_MIN_PRICE),
                    hotelAvgScore = it.getInt(COL_HOTEL_AVG_SCORE)
                )
            )
        }
        ans.forEach { data ->
            sql =
                "SELECT $COL_USER_COMMENT FROM user_comment where hotel_id = ${data.hotelId} ORDER BY start_time DESC LIMIT 1"
            ta.query(sql) {
                data.newComment = it.getString(COL_USER_COMMENT)
            }
            if (data.newComment == null) {
                data.newComment = " "
            }
            sql = "select count(*) cnt from $ORDER_TABLE_NAME where $COL_HOTEL_ID = '${data.hotelId}'"
            ta.query(sql) {
                data.salesCnt = it.getInt("cnt").toString()
            }
            if (data.salesCnt == null) {
                data.salesCnt = "0"
            }
        }
        if (sortType == 1) {
            ans.sortBy {
                (it.hotelMinPrice)?.toInt()
            }
        } else if (sortType == 2) {
            ans.sortByDescending {
                it.hotelAvgScore
            }
        } else if (sortType == 3)
            ans.sortByDescending {
                (it.salesCnt)?.toInt()
            }
        val HOTEL_CNT = 8
        if (ans.size >= 1)
            ans = ans.subList(offset, if (offset + HOTEL_CNT >= ans.size) ans.size - 1 else offset + HOTEL_CNT)

        return ans
    }

    override fun getHotelRoomList(
        hotelId: Int
    ): MutableList<UserHotelRoomData> {
        val sql =
            "select a.* FROM $ROOM_TABLE_NAME a join $HOTEL_TABLE_NAME b on a.$COL_HOTEL_ID = b.$COL_HOTEL_ID" +
                    " where a.$COL_ROOM_PUBLISHED = 1 and a.$COL_HOTEL_ID = $hotelId"
        val ans = mutableListOf<UserHotelRoomData>()
        ta.query(sql) {
            ans.add(
                UserHotelRoomData(
                    roomIcon = it.getString(COL_ROOM_ICON),
                    roomName = it.getString(COL_ROOM_NAME),
                    roomDesc = it.getString(COL_ROOM_DESC),
                    roomFeature = it.getString(COL_ROOM_FEATURE),
                    roomPrice = it.getString(COL_ROOM_PRICE),
                    roomId = it.getString(COL_ROOM_ID)
                )
            )
        }
        return ans
    }

    override fun getUserMsg(userId: String): ProFileData {
        val sql = "select * from $USER_TABLE_NAME where $COL_USER_ID = '$userId'"
        sql.log()
        val ans = ProFileData()
        ta.query(sql) {
            ans.apply {
                this.userId = it.getString(COL_USER_ID)
                this.userName = it.getString(COL_USER_NAME)
                this.userPass = it.getString(COL_USER_PASS)
                this.userDate = it.getString(COL_USER_DATE)
                this.userIcon = it.getString(COL_USER_ICON)
                this.userLocation = it.getString(COL_USER_LOCATION)
                this.userState = it.getInt(COL_USER_STATUS)
                this.userPhone = it.getString(COL_USER_PHONE)
                this.userBz = it.getString(COL_USER_BZ)
            }
        }
        return ans
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
        val sql = "update $USER_TABLE_NAME set $COL_USER_ICON = '$userIcon'," +
                "$COL_USER_NAME = '$userName'," +
                "$COL_USER_PASS = '$userPass'," +
                "$COL_USER_LOCATION = '$userLocation'," +
                "$COL_USER_PHONE = '$userPhone'," +
                "$COL_USER_BZ = '$userBz' where $COL_USER_ID = '$userId'"
        ta.execute(sql)
    }

    override fun updateOrderState(orderId: String, state: Int) {
        val sql =
            "update $ORDER_TABLE_NAME set $COL_ORDER_STATUS = $state where $COL_ORDER_ID = '$orderId'"
        ta.execute(sql)
    }

    override fun orderPay(
        hotelId: String,
        userId: String,
        roomId: String,
        starTime: String,
        endTime: String,
        roomPrice: String,
        nowTime: String,
        orderTimeID: String
    ) {
        val sql =
            "insert into $ORDER_TABLE_NAME($COL_HOTEL_ID, $COL_ORDER_ID, $COL_USER_ID, $COL_ROOM_ID, $COL_ROOM_PRICE, $COL_ORDER_TIME," +
                    "$COL_START_TIME, $COL_END_TIME, $COL_ORDER_STATUS) values ($hotelId," +
                    "'${userId + roomId + orderTimeID}', '$userId','$roomId','$roomPrice','$nowTime','$starTime','$endTime',0)"
        sql.log()
        ta.execute(sql)
    }

    override fun getUserOrderList(userId: String, offset: Int?, orderTimeType: Int): MutableList<OrderListData> {
        val ORDER_PER_PAGE_CNT = 5
        val sql =
            "SELECT DISTINCT a.$COL_ORDER_ID, a.$COL_ORDER_REASON, a.$COL_USER_CONFIRM, a.$COL_USER_ID,c.$COL_USER_ICON, c.$COL_USER_NAME, c.$COL_USER_LOCATION, c.$COL_USER_PHONE, a.$COL_ROOM_ID, a.$COL_ROOM_PRICE,b.$COL_HOTEL_NAME, " +
                    "b.$COL_HOTEL_LOCATION,b.$COL_HOTEL_PHONE,b.$COL_HOTEL_ID, a.$COL_ORDER_TIME, a.$COL_START_TIME, a.$COL_END_TIME, a.$COL_ORDER_STATUS, d.$COL_ROOM_NAME, d.$COL_ROOM_DESC, d.$COL_ROOM_FEATURE,d.$COL_ROOM_ICON " +
                    "FROM $ORDER_TABLE_NAME a " +
                    "JOIN $HOTEL_TABLE_NAME b on a.$COL_HOTEL_ID = b.$COL_HOTEL_ID " +
                    "JOIN $USER_TABLE_NAME c on a.$COL_USER_ID = c.$COL_USER_ID " +
                    "JOIN $ROOM_TABLE_NAME d on d.$COL_ROOM_ID = a.$COL_ROOM_ID " +
                    " where a.$COL_USER_ID = '$userId' order by a.$COL_ORDER_TIME desc limit $ORDER_PER_PAGE_CNT offset ${offset ?: 0}"
        sql.log("query Order List Sql")
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
                    roomIcon = it.getString(COL_ROOM_ICON),
                    userOrderConfirm = it.getString(COL_USER_CONFIRM),
                    reason = it.getString(COL_ORDER_REASON)
                )
            )
        }

        return ans.filter {
            val endDate = simpleDateFormat.parse(it.endTime)
            val nowDate = simpleDateFormat.parse(Utils.getNowFormatDate("yyyy-MM-dd"))
            if (orderTimeType == 1)
                endDate.compareTo(nowDate) == orderTimeType || endDate.compareTo(nowDate) == 0
            else
                endDate.compareTo(nowDate) == orderTimeType

        }.toMutableList()
    }

    override fun userCancelOrder(orderId: String?) {
        val sql =
            "update $ORDER_TABLE_NAME set $COL_ORDER_STATUS = -1 where $COL_ORDER_ID = '$orderId'"
        ta.execute(sql)
    }

    override fun goodClick(commentId: String) {
        val sql =
            "update $USER_COMMENT_TABLE_NAME set $COL_GOOD_CNT = $COL_GOOD_CNT + 1 where $COL_COMMENT_ID = '$commentId'"
        ta.execute(sql)
    }

    override fun hotelOrderDone(orderId: String) {
        val sql = "update $ORDER_TABLE_NAME set $COL_ORDER_STATUS = 3 where $COL_ORDER_ID = '$orderId'"
        ta.execute(sql)
    }

    override fun badBehavior(userId: String) {
        val sql = "update $USER_TABLE_NAME set $COL_USER_BAD_CNT = $COL_USER_BAD_CNT + 1 where $COL_USER_ID = '$userId'"
        ta.execute(sql)
    }

    override fun goodBehavior(userId: String) {
        val sql = "update $USER_TABLE_NAME set $COL_USER_BAD_CNT = $COL_USER_BAD_CNT - 1 where $COL_USER_ID = '$userId'"
        ta.execute(sql)
    }

    override fun userOrderConfirm(orderId: String) {
        val sql = "update $ORDER_TABLE_NAME set $COL_USER_CONFIRM = '1' where $COL_ORDER_ID = '$orderId'"
        ta.execute(sql)
    }

    override fun getOrderLength(userId: String): String? {
        val nowTime = Utils.getNowFormatDate("yyyy-MM-dd")
        val sql =
            "select count(*) cnt from $ORDER_TABLE_NAME where $COL_USER_ID = '$userId' and $COL_ORDER_TIME >= '$nowTime'"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt("cnt")
        }
        return ans.toString()
    }

    override fun getTypeMoney(rechargeKey: String): Int {
        val sql = "select $COL_RECHAREGE_MONEY from $TABLE_RECHARGE_TYPE where $COL_RECHAREGE_KEY = '$rechargeKey'"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt(COL_RECHAREGE_MONEY)
        }
        return ans
    }

    override fun getUserMoney(userId: String): Int {
        val sql = "select $COL_USER_MONEY from $TABLE_USER_ACCOUNT where $COL_USER_ID = '$userId'"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt(COL_USER_MONEY)
        }
        return ans
    }

    override fun userPay(userId: String, money: Int) {
        val sql =
            "update $TABLE_USER_ACCOUNT set $COL_USER_MONEY = $COL_USER_MONEY - $money where $COL_USER_ID = '$userId'"
        ta.execute(sql)
    }

    override fun getHotelCount(): String {
        val sql =
            "SELECT count(DISTINCT a.$COL_HOTEL_ID) cnt from $HOTEL_TABLE_NAME a LEFT JOIN $ROOM_TABLE_NAME b on a.$COL_HOTEL_ID = b.$COL_HOTEL_ID where b.$COL_ROOM_PUBLISHED = 1"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt("cnt")
        }
        return ans.toString()
    }

    override fun getPassOrderLength(userId: String): String? {
        val nowTime = Utils.getNowFormatDate("yyyy-MM-dd")
        val sql =
            "select count(*) cnt from $ORDER_TABLE_NAME where $COL_USER_ID = '$userId' and $COL_ORDER_TIME < '$nowTime'"
        var ans = 0
        ta.query(sql) {
            ans = it.getInt("cnt")
        }
        return ans.toString()
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
        val sql =
            "insert into $USER_COMMENT_TABLE_NAME ($COL_COMMENT_ID, $COL_HOTEL_ID, $COL_USER_ID,$COL_USER_COMMENT,$COL_USER_COMMENT_SCORE," +
                    "$COL_START_TIME, $COL_ROOM_ID, $COL_GOOD_CNT) values(" +
                    "'$commentId',$hotelId,'$userId','$userComment','$userCommentScore','$startTime','$roomId',0)"
        val sql1 = "update $ORDER_TABLE_NAME set $COL_ORDER_STATUS = 4 where $COL_ORDER_ID = '$orderId'"
        val sql2 =
            "update $HOTEL_TABLE_NAME set $COL_HOTEL_AVG_SCORE = ($COL_HOTEL_AVG_SCORE + $userCommentScore)/2 where $COL_HOTEL_ID = $hotelId"
        ta.execute(sql)
        ta.execute(sql1)
        ta.execute(sql2)
    }

    override fun getHotelCommentList(hotelId: Int): MutableList<HotelCommentData> {
        val sql =
            "select a.*, b.user_name,b.user_icon, c.room_name from $USER_COMMENT_TABLE_NAME a join $USER_TABLE_NAME b on a.user_id = b.user_id join $ROOM_TABLE_NAME c on c.room_id = a.room_id where a.$COL_HOTEL_ID = $hotelId"
        val ans = mutableListOf<HotelCommentData>()
        ta.query(sql) {
            ans.add(HotelCommentData().apply {
                this.userId = it.getString(COL_USER_ID)
                this.userName = it.getString(COL_USER_NAME)//
                this.userIcon = it.getString(COL_USER_ICON)
                this.userComment = it.getString(COL_USER_COMMENT)
                this.userCommentScore = it.getInt(COL_USER_COMMENT_SCORE)
                this.commentId = it.getString(COL_COMMENT_ID)
                this.hotelId = it.getString(COL_HOTEL_ID)
                this.goodCnt = it.getInt(COL_GOOD_CNT).toString()
                this.roomId = it.getString(COL_ROOM_ID)
                this.roomName = it.getString(COL_ROOM_NAME)//
                this.startTime = it.getString(COL_START_TIME)
            })
        }
        return ans
    }
}