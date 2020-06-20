package com.gallosalocin.calorie_counter.utils

data class Category(
    val name: String,
    val color: Int
) {

    override fun toString(): String {
        return name
    }
}