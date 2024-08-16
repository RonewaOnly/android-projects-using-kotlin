package com.example.workingwithretrofit.data

import com.example.workingwithretrofit.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsList(): Flow<Result<List<Product>>>
}