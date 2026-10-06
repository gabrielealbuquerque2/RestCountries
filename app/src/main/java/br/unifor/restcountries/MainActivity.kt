package br.unifor.restcountries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.restcountries.ui.navigation.AppNavigation
import br.unifor.restcountries.ui.theme.RestCountriesTheme
import br.unifor.restcountries.viewmodel.CountriesViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            RestCountriesTheme {

                val countriesViewModel: CountriesViewModel =
                    viewModel()

                AppNavigation(
                    viewModel = countriesViewModel
                )
            }
        }
    }
}