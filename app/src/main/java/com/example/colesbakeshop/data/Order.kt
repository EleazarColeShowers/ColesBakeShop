package com.example.colesbakeshop.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemName: String,
    val itemPrice: String,
    val itemDescription: String,
    val itemImage: Int,
    val orderNumber: String = generateOrderNumber(),
    val orderStatus: OrderStatus = OrderStatus.ONGOING
)

fun generateOrderNumber(): String {
    val randomNumber = (100000..999999).random()
    return "ORN-$randomNumber"
}

enum class OrderStatus {
    ONGOING, DELIVERED, CANCELED
}

class Converters {
    @TypeConverter
    fun fromOrderStatus(value: OrderStatus): String {
        return value.name
    }

    @TypeConverter
    fun toOrderStatus(value: String): OrderStatus {
        return OrderStatus.valueOf(value)
    }
}