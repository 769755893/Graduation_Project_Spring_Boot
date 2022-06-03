package com.example.demo1.model


data class HotelCommentData(
    var commentId: String? = null,
    var userId: String? = null,
    var userIcon: String? = null,
    var hotelId: String? = null,
    var userName: String? = null,
    var userComment: String? = null,
    var userCommentScore: Int? = null,
    var roomId: String? = null,
    var roomName: String? = null,
    var startTime: String? = null,//入住时间
    var goodCnt: String? = null
)