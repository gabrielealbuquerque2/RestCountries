package br.unifor.restcountries.data.remote

import br.unifor.restcountries.data.model.Country
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountriesApiService {

    @GET("all?fields=name,capital,population,area,region,continents,flags,currencies,languages")
    suspend fun getAllCountries(): List<Country>

    companion object {
        fun create(): CountriesApiService {
            return Retrofit.Builder()
                .baseUrl("https://restcountries.com/v3.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountriesApiService::class.java)
        }
    }
}