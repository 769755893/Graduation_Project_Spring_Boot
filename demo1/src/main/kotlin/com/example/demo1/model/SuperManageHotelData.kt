package com.app.project.hotel.data.connection.model


data class SuperManageHotelData(
    var hotelName: String? = null,
    var hotelLocation: String? = null,
    var hotelPhone: String? = null,

    var hotelBadCnt: Int = 0,
    var hotelState: Int = 0,//待审核，良好，封禁

    var hotelIcon: String? = null,
    var hotelPass: String? = null,

    var isParent: Boolean? = null,
    var foldState: Boolean = false,

    var childSize: Int = 0,

    var hotelId: String? = null,
    var roomIcon: String? = null,
    var roomName: String? = null,//温馨舒适房
    var roomDescription: String? = null,//1张 1.8米大床，2人入住，
    var roomFeature: String? = null, //无早餐
    var roomPrice: String? = null,//77
    var room_id: String? = null,
    var room_published: Int? = 0,
    var room_upLoad: Boolean = false
)