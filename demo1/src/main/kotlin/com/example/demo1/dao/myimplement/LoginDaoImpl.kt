package com.example.demo1.dao.myimplement

import com.example.demo1.dao.myinterface.LoginDao
import com.example.demo1.base.util.log
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class LoginDaoImpl : LoginDao {
    private val USER_TABLE_NAME = "user"
    private val COL_USER_ID = "user_id"
    private val COL_USER_NAME = "user_name"
    private val COL_USER_ICON = "user_icon"
    private val COL_USER_PASS = "user_pass"
    private val COL_USER_DATE = "user_date"
    private val COL_USER_STATE = "user_state"

    private val HOTEL_TABLE_NAME = "hotel_user"
    private val COL_HOTEL_NAME = "hotel_name"
    private val COL_HOTEL_LOCATION = "hotel_location"
    private val COL_HOTEL_PHONE = "hotel_phone"
    private val COL_HOTEL_ID = "hotel_id"
    private val COL_HOTEL_PASS = "hotel_pass"
    private val COL_HOTEL_STATE = "hotel_state"


    private val TABLE_PAY_ACCOUNT = "pay_account"
    private val COL_USER_MONEY = "user_money"

    @Autowired
    private lateinit var ta: JdbcTemplate

    override fun insertUser(userId: String, userPass: String, userDate: String) {
        var sql =
            "INSERT INTO $USER_TABLE_NAME ($COL_USER_ID, $COL_USER_PASS, $COL_USER_DATE) values ('$userId','$userPass','$userDate')"
        ta.execute(sql)
        sql = "insert into $TABLE_PAY_ACCOUNT($COL_USER_ID, $COL_USER_MONEY) values ('$userId', 0)"
        ta.execute(sql)
    }

    override fun queryAllUser(): MutableList<String?> {
        val sql = "select $COL_USER_ID from $USER_TABLE_NAME"
        sql.log()
        val ans = mutableListOf<String?>()

        ta.query(sql) { rs, _ -> ans.add(rs.getString(COL_USER_ID)) }
        return ans
    }

    override fun queryPassword(userId: String): Pair<String, String> {
        val sql = "select $COL_USER_PASS, $COL_USER_STATE from $USER_TABLE_NAME where $COL_USER_ID = '$userId'"
        sql.log()
        var ans = Pair("","")
        ta.query(sql) { it, _ ->
            ans = Pair(it.getString(COL_USER_PASS), it.getString(COL_USER_STATE))
        }
        return ans
    }

    override fun forgetQuery(userId: String, userDate: String): Pair<String, String> {
        val sql =
            "select $COL_USER_PASS,$COL_USER_DATE from $USER_TABLE_NAME where $COL_USER_ID='$userId'"
        sql.log()
        var ans = Pair("", "")
        ta.query(sql) { it, _ ->
            ans = Pair(it.getString(COL_USER_DATE), it.getString(COL_USER_PASS))
        }
        return ans
    }

    override fun loginHotel(hotelId: Int, hotelCode: String): Pair<String, String> {
        val sql =
            "select $COL_HOTEL_PASS, $COL_HOTEL_STATE from $HOTEL_TABLE_NAME where $COL_HOTEL_ID = '$hotelId'"
        sql.log()
        var ans = Pair("","")
        ta.query(sql) {
            ans = Pair(it.getString(COL_HOTEL_PASS), it.getString(COL_HOTEL_STATE))
        }
        return ans
    }

    override fun signHotel(hotelName: String, hotelCode: String, hotelLocation: String, hotelPhone: String): String {
        val sql =
            "insert into hotel_user($COL_HOTEL_NAME, $COL_HOTEL_LOCATION, $COL_HOTEL_PHONE, $COL_HOTEL_ID, $COL_HOTEL_PASS)" +
                    " values('$hotelName', '$hotelLocation', '$hotelPhone', " +
                    "(SELECT a.ans FROM (SELECT $COL_HOTEL_ID+1 ans FROM $HOTEL_TABLE_NAME where id IN (" +
                    "SELECT max(id) FROM $HOTEL_TABLE_NAME)) a" +
                    ") , '$hotelCode')"
        val sql2 = "SELECT $COL_HOTEL_ID FROM $HOTEL_TABLE_NAME where id IN (\n" +
                "SELECT max(id) FROM $HOTEL_TABLE_NAME)"

        sql.log()
        sql2.log()

        var ans = 0
        ta.execute(sql)
        ta.query(sql2) { it, _ ->
            ans = it.getInt(COL_HOTEL_ID)
        }
        return ans.toString()
    }

    override fun getUserIcon(userId: String): String {
        val sql = "select IFNULL($COL_USER_ICON,'') $COL_USER_ICON from $USER_TABLE_NAME where $COL_USER_ID = '$userId'"
        var ans = ""
        ta.query(sql) {
            ans = it.getString(COL_USER_ICON)
        }
        return ans
    }
}