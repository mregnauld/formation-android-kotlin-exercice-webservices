package com.formationandroid.webservices.ws

import retrofit2.Call
import retrofit2.http.GET

interface WSInterface
{

    // appel get :
    @GET("/api/planets")
    fun getPlanets(): Call<RetourWSPlanetes>

}