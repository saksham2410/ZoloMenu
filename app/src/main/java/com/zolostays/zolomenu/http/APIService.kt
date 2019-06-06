package com.zolostays.zolomenu.http

import com.zolostays.zolomenu.db.models.Item
import com.zolostays.zolomenu.db.models.Kitchen
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface APIService {

    @GET("Zolo_city/userdata")
    fun getKitchenList(): Observable<List<Kitchen>>

    @FormUrlEncoded
    @POST("date")
    fun createUser(
        @Field("todo") date: String,
        @Field("todo2") mealType: String,
        @Field("todo1") property: String
    ): Observable<String>

    @GET("Kitchen_menu/userdatacity")
    fun getItems(): Observable<List<Item>>
}