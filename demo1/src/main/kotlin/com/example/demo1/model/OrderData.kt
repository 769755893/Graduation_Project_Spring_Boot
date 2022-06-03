package com.app.project.hotel.data.connection.model

data class OrderData(
    var userId: String? = null,
    var userName: String? = null,
    var userPhone: String? = null,
    var starTime: String? = null,
    var endTime: String? = null,
    var hotelId: String? = null,
    var hotelName: String? = null,
    var hotelLocation: String? = null,
    var roomId: String? = null,
    var roomName: String? = null,
    var roomDesc: String? = null,
    var roomFeature: String? = null,
    var roomPrice: String? = null
)