package com.gallosalocin.calorie_counter.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "food_table")
data class Food(
    var name: String,
    var category: String,
    var color: Int,
    var note: String = "",
    var calorie: Int,
    var weight: Int = 100,
    var fat: Float,
    var carb: Float,
    var prot: Float
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}