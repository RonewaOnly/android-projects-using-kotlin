package com.example.jetfilesapplication

data class JetFile(
    val name: String,
    val modifierAt: Long =0,
    val size: Long =0,
    val path: String =""
)
