package com.vhpg.practica2.application

import android.app.Application
import com.vhpg.practica2.data.ProductRepository
import com.vhpg.practica2.data.remote.RetrofitHelper

class ClothsShopApp : Application(){

    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }

    val repository by lazy{
        ProductRepository(retrofit)
    }

}