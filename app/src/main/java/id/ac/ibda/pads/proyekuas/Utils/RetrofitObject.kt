package id.ac.ibda.pads.proyekuas.Utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private const val BASE_URL: String = "http://127.0.0.1:5000/"

    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

}