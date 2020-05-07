package com.example.a27299.cats.home

interface PermissionListener {
    fun requestPermission(description:String,permissions: Array<String>,block:()->Unit)
}