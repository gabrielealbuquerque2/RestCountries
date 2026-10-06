package br.unifor.restcountries.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.unifor.restcountries.data.model.Country
import br.unifor.restcountries.data.repository.CountriesRepository
import kotlinx.coroutines.launch

data class CountriesUiState(

    val loading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val error: String? = null
)

class CountriesViewModel : ViewModel() {
    private val repository = CountriesRepository()
    var uiState by mutableStateOf(
        CountriesUiState()
    )
        private set

    var selectedContinent by mutableStateOf("Todos")
        private set

    val continents = listOf(
        "Todos",
        "Africa",
        "Americas",
        "Asia",
        "Europe",
        "Oceania",
        "Antarctic"
    )

    val filteredCountries: List<Country>
        get() = if (selectedContinent == "Todos") {
            uiState.countries
        } else {
            uiState.countries.filter {
                it.region.equals(selectedContinent, ignoreCase = true)
            }
        }

    init {
        loadCountries()
    }

    fun onContinentSelected(continent: String) {
        selectedContinent = continent
    }

    fun loadCountries() {

        viewModelScope.launch {

            uiState = uiState.copy(   // começa a carregar
                loading = true,
                error = null
            )

            try {

                val countries =
                    repository.getCountries()

                uiState = uiState.copy(
                    loading = false,
                    countries = countries
                )

            } catch (e: Exception) {

                println("ERRO = ${e.message}")

                uiState = uiState.copy(
                    loading = false,
                    error = e.toString()
                )
            }
        }
    }
}