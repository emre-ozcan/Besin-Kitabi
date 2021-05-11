package com.emreozcan.besinkitabi.service

import com.emreozcan.besinkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

//https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

interface BesinAPI {
    @GET("/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin(): Single<List<Besin>>
}