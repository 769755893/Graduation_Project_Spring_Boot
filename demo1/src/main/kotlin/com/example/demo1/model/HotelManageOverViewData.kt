package com.app.project.hotel.data.connection.model


data class HotelManageOverViewData(
    var hotelId: Int? = null,
    var roomIcon: String? = null,
    var roomName: String? = null,//温馨舒适房
    var roomDescription: String? = null,//1张 1.8米大床，2人入住，
    var roomFeature: String? = null, //无早餐
    var roomPrice: String? = null,//77
    var room_id: String? = null,
    var room_published: Int? = 0,
    var room_upLoad: Boolean = false,
    var reason: String? = null,
    var hotelConfirm: Int? = null
)