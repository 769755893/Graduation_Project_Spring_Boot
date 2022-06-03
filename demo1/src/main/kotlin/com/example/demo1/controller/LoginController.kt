package com.example.demo1.controller

import com.alibaba.fastjson.JSON
import com.example.demo1.base.model.BaseResponse
import com.example.demo1.base.util.log
import com.example.demo1.service.myinterface.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController {
    @Autowired
    private lateinit var service: LoginService

    @RequestMapping("/userLogin")
    @ResponseBody
    fun userLogin(userId: String, userPass: String): Any? {
        val ans = service.userLogin(userId, userPass)
        if (ans is String) {
            return JSON.toJSON(
                BaseResponse(
                    msg = "封号中"
                )
            )
        } else {
            return if (ans as Boolean) {
                JSON.toJSON(
                    BaseResponse(
                        msg = "true"
                    )
                )
            }else {
                JSON.toJSON(
                    BaseResponse(
                        msg = "false"
                    )
                )
            }
        }
    }

    @RequestMapping("/getUserIcon")
    @ResponseBody
    fun getUserIcon(userId: String): Any? {
        val ans = service.getUserIcon(userId)
        return JSON.toJSON(
            BaseResponse(
                msg = if (ans.length>0) "true" else "false",
                data = ans
            )
        )
    }

    @RequestMapping("/loginHotel")
    @ResponseBody
    fun loginHotel(hotelId: Int, hotelPass: String): Any? {
        val ans = service.loginHotel(hotelId = hotelId, hotelCode = hotelPass)
        if (ans is String) {
            return JSON.toJSON(
                BaseResponse(
                    msg = "封号中"
                )
            )
        } else {
            return if (ans as Boolean) {
                JSON.toJSON(
                    BaseResponse(
                        msg = "true"
                    )
                )
            }else {
                JSON.toJSON(
                    BaseResponse(
                        msg = "false"
                    )
                )
            }
        }

    }

    @RequestMapping("/userSign")
    @ResponseBody
    fun insertUser(userId: String, userPass: String, userDate: String): Any? {
        val allUser = service.queryAllUser()
        return if (userId in allUser) {
            JSON.toJSON(
                BaseResponse(
                    msg = "用户已存在"
                )
            )
        } else {
            service.userSign(userId, userPass, userDate)
            JSON.toJSON(
                BaseResponse(
                    msg = "成功"
                )
            )
        }
    }
    @RequestMapping("/forgetQuery")
    @ResponseBody
    fun forgetQuery(userId: String, userDate: String): Any? {
        val ans = service.forgetQuery(userId, userDate)
        return if (ans is Boolean)
            JSON.toJSON(
                BaseResponse(
                    msg = "密保错误"
                )
            )
        else {
            JSON.toJSON(
                BaseResponse(
                    msg = "正确",
                    data = ans as String
                )
            )
        }
    }
    @RequestMapping("/signHotel")
    @ResponseBody
    fun signHotel(hotelName: String, hotelCode: String, hotelPhone: String, hotelLocation: String): Any? {

        return JSON.toJSON(
            BaseResponse(
                msg = "注册成功，认证编号如下~",
                data = service.hotelSign(hotelName = hotelName, hotelCode = hotelCode, hotelLocation = hotelLocation, hotelPhone = hotelPhone)
            )
        )
    }

}