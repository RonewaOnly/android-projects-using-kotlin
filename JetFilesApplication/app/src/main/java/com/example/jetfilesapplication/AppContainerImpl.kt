package com.example.jetfilesapplication

//Dependency Injection container at the application level
interface AppContainer{
    val fileRepository:FileRepositoryImpl
}
class AppContainerImpl: AppContainer {
    override val fileRepository: FileRepositoryImpl by lazy{
        FileRepositoryImpl()
    }
}