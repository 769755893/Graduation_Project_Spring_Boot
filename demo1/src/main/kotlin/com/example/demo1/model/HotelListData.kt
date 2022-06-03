package com.example.demo1.model


data class HotelListData(
    var hotelId: String? = null,
    var hotelName: String? = null,
    var hotelPass: String? = null,
    var hotelIcon: String? = null,
    var hotelLocation: String? = null,
    var hotelPhone: String? = null,
    var hotelDesc: String? = null,
    var hotelMinPrice: String? = null,
    var hotelAvgScore: Int? = null,
    var newComment: String? = null,
    var salesCnt: String? = null
)