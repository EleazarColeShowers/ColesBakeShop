package com.example.colesbakeshop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemName: String,
    val itemPrice: String,
    val itemDescription: String,
    val itemImage: Int,
    val orderNumber: String = generateOrderNumber()
)

// Function to generate a unique order number
fun generateOrderNumber(): String {
    val randomNumber = (100000..999999).random()
    return "ORN-$randomNumber"
}