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
}