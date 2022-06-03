package com.example.demo1.dao.myinterface

interface LoginDao {

    fun insertUser(userId: String, userPass: String, userDate: String)

    fun queryAllUser(): MutableList<String?>

    fun queryPassword(userId: String): Pair<String, String>

    fun forgetQuery(userId: String, userDate: String): Pair<String, String>

    fun loginHotel(hotelId: Int, hotelCode: String): Pair<String, String>

    fun signHotel(
        hotelName: String,
        hotelCode: String,
        hotelLocation: String,
        hotelPhone: String
    ): String

    fun getUserIcon(userId: String): String
}