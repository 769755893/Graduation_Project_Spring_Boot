package com.app.project.hotel.data.connection.model


data class SuperManageUserData(
    var userId: String? = null,
    var userName: String? = "",
    var userPass: String? = "",
    var userIcon: String? = null,
    var userStatus: Int? = null, // 0 良好，1：封禁
    var userDate: String? = null,
    var userPhone: String? = "",
    var userLocation: String? = null,
    var userBadCnt: Int? = null
)