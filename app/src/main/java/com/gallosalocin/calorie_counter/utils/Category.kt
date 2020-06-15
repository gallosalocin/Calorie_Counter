package com.gallosalocin.calorie_counter.utils

data class Category(
    val category: String,
    val categoryColor: Int
) {

    override fun toString(): String {
        return category
    }
}