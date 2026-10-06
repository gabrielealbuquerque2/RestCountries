package br.unifor.restcountries.data.repository

import br.unifor.restcountries.data.model.Country
import br.unifor.restcountries.data.remote.CountriesApiService

class CountriesRepository {

    private val api = CountriesApiService.create()

    suspend fun getCountries(): List<Country> {
        return api.getAllCountries()
    }

}

