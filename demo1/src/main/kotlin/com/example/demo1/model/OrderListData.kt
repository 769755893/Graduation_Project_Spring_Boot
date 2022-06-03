package com.app.project.hotel.data.connection.model

data class OrderListData (
    var orderId: String? = null,
    var userId: String? = null,
    var userIcon: String? = null,
    var userName: String? = null,
    var userLocation: String? = null,
    var userPhone: String? = null,
    var roomId: String? = null,
    var roomIcon: String? = null,
    var roomName: String? = null,
    var roomDesc: String? = null,
    var roomFeature: String? = null,
    var roomPrice: String? = null,
    var hotelId: String? = null,
    var hotelName: String? = null,
    var hotelLocation: String? = null,
    var hotelPhone: String? = null,
    var orderTime: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var userCommentScore: Int = 5,
    var userComment: String? = null,
    var status: Int? = null,
    var userOrderConfirm: String? = null,
    var reason: String? = null
)