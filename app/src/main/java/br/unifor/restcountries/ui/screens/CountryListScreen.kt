package br.unifor.restcountries.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unifor.restcountries.data.model.Country
import br.unifor.restcountries.viewmodel.CountriesViewModel
import coil.compose.AsyncImage

@Composable
fun CountryListScreen(
    onCountryClick: (Country) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CountriesViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val filteredCountries = viewModel.filteredCountries

    Column(modifier = modifier.fillMaxSize()) {

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.continents) { continent ->
                FilterChip(
                    selected = viewModel.selectedContinent == continent,
                    onClick = { viewModel.onContinentSelected(continent) },
                    label = { Text(continent) }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                uiState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Erro: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.loadCountries() }) {
                            Text("Tentar novamente")
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        items(filteredCountries) { country ->
                            CountryItem(
                                country = country,
                                onClick = { onCountryClick(country) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountryItem(country: Country, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = country.flags.png,
                contentDescription = "Bandeira de ${country.name.official}",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = country.name.official,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
