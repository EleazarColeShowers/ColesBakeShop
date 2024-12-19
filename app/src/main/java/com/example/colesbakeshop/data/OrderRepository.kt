package com.example.colesbakeshop.data

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepository(private val orderDao: OrderDao) {
    val allOrders: LiveData<List<Order>> = orderDao.getAllOrders()

    suspend fun insert(order: Order) {
        withContext(Dispatchers.IO) {
            orderDao.insertOrder(order)
            Log.d("OrderRepository", "Inserting order: ${order.itemName}")

        }
    }
    suspend fun updateOrderStatus(orderNumber: String, status: OrderStatus) {
        withContext(Dispatchers.IO) {
            orderDao.updateOrderStatus(orderNumber, status)
            Log.d("OrderRepository", "Updated order status for order: $orderNumber to $status")
        }
    }
}