package com.gallosalocin.calorie_counter.models

import java.io.Serializable

data class Food(
    var name: String,
    var category: String,
    var note: String = "",
    var calorie: Int,
    var weight: Int = 100,
    var fat: Float,
    var carb: Float,
    var prot: Float
) : Serializable