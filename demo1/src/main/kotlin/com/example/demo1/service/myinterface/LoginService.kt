package com.example.demo1.service.myinterface

interface LoginService {
    fun userSign(userId: String, userPass: String, userDate: String)

    fun queryAllUser(): MutableList<String?>

    fun forgetQuery(userId: String, userDate: String): Any

    fun userLogin(userId: String, userPass: String): Any

    fun loginHotel(hotelId: Int, hotelCode: String): Any

    fun hotelSign(
        hotelName: String,
        hotelCode: String,
        hotelLocation: String,
        hotelPhone: String
    ): String

    fun getUserIcon(userId: String): String
}