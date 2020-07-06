package com.gallosalocin.calorie_counter.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey
    var id: Int = 1,
    var checkedGender: Int = 0,
    var age: Int = 0,
    var height: Int = 0,
    var weight: Int = 0,
    var checkedActivity: Int = 0,
    var fatPercent: Int = 0,
    var carbPercent: Int = 0,
    var protPercent: Int = 0,
    var dailyCalories: Float = 0F,
    var fatResult: Int = 0,
    var carbResult: Int = 0,
    var protResult: Int = 0,
    var bmrResult: Float = 0F
) : Serializable