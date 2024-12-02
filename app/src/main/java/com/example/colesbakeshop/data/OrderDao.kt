package com.example.colesbakeshop.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert
     fun insertOrder(order: Order)

    @Query("SELECT * FROM orders")
     fun getAllOrders(): LiveData<List<Order>>
}