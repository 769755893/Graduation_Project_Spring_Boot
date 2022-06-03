package com.example.demo1.service.myimplement

import com.example.demo1.dao.myinterface.LoginDao
import com.example.demo1.service.myinterface.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LoginServiceImpl: LoginService {
    @Autowired
    private lateinit var dao: LoginDao

    override fun userSign(userId: String, userPass: String, userDate: String) {
        return dao.insertUser(userId, userPass,userDate)
    }

    override fun queryAllUser(): MutableList<String?> {
        return dao.queryAllUser()
    }

    override fun forgetQuery(userId: String, userDate: String): Any {
        val ans = dao.forgetQuery(userId, userDate)
        if (ans.first == userDate) {
            return ans.second
        }
        return false
    }

    override fun userLogin(userId: String, userPass: String): Any {
        val ans = dao.queryPassword(userId)
        if (ans.second == "1") {
            return "封禁中"
        } else {
            return ans.first == userPass
        }
    }

    override fun loginHotel(hotelId: Int, hotelCode: String): Any {
        val ans = dao.loginHotel(hotelId, hotelCode)
        if (ans.second == "1") {
            return "封号中"
        }else {
            return hotelCode == ans.first
        }
    }


    override fun hotelSign(hotelName: String, hotelCode: String, hotelLocation: String, hotelPhone: String): String {
        return dao.signHotel(hotelName = hotelName, hotelCode = hotelCode, hotelLocation = hotelLocation, hotelPhone = hotelPhone)
    }

    override fun getUserIcon(userId: String): String {
        return dao.getUserIcon(userId)
    }
}