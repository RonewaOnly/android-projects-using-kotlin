package com.example.jetfilesapplication

import kotlinx.coroutines.flow.Flow
import java.io.File


interface FileRepository {

    /**
     * Get the files from whatever the directory provided
     * */
    suspend fun getJetFiles(parentFile: File): Result<List<JetFile>>

    /**
     * Observe the current favorites
     */
    fun observeFavorites(): Flow<Set<String>>

    /**
     * Toggle a fileUri to be a favorite or not.
     */
    suspend fun toggleFavorite(fileUri: String)
}