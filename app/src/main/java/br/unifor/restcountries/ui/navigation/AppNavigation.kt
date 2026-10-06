package br.unifor.restcountries.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.unifor.restcountries.ui.screens.CountryDetailScreen
import br.unifor.restcountries.ui.screens.CountryListScreen
import br.unifor.restcountries.viewmodel.CountriesViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation(
    viewModel: CountriesViewModel,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier
    ) {

        composable("list") {

            CountryListScreen(
                viewModel = viewModel,
                onCountryClick = { country ->

                    val encodedName =
                        URLEncoder.encode(
                            country.name.official,
                            StandardCharsets.UTF_8.toString()
                        )

                    navController.navigate(
                        "details/$encodedName"
                    )
                }
            )
        }

        composable(
            route = "details/{countryName}",
            arguments = listOf(
                navArgument("countryName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val countryName =
                URLDecoder.decode(
                    backStackEntry.arguments?.getString("countryName") ?: "",
                    StandardCharsets.UTF_8.toString()
                )

            val country =
                viewModel.uiState.countries.find {
                    it.name.official == countryName
                }

            if (country != null) {

                CountryDetailScreen(
                    country = country,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}