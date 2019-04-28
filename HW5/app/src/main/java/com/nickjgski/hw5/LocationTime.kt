package com.nickjgski.hw5

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LocationTime(
    var latitude: String? = "",
    var longitude: String? = "",
    var address: String? = "",
    var time: String? = ""
)