package com.example.workingwithretrofit.data

import coil.network.HttpException
import com.example.workingwithretrofit.data.Result
import com.example.workingwithretrofit.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class ProductsRepositoryImpl(
    private val api: Api
):ProductsRepository {
    override suspend fun getProductsList(): Flow<Result<List<Product>>> {
        return flow {
            val productsFromApi = try{
                    api.getProductsList()

            }catch (e: IOException){
                e.printStackTrace()
                emit(Result.Error(data=null,message = "Error loading product"))
                return@flow
            }catch (e: HttpException){
                emit(Result.Error(data=null,message = "Error loading product"))
                return@flow
            }catch (e: Exception){
                emit(Result.Error(data=null,message = "Error loading product"))
                return@flow
            }
            emit(Result.Success(productsFromApi.products))
        }

    }

}