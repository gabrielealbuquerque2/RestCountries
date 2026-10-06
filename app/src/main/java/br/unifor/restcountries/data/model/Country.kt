package br.unifor.restcountries.data.model

data class Country(
    val name: Name,
    val capital: List<String>?,
    val population: Long,
    val area: Double,
    val region: String,
    val continents: List<String>,
    val flags: Flags,
    val currencies: Map<String, Currency>?,
    val languages: Map<String, String>?
)

data class Name(
    val common: String,
    val official: String
)

data class Flags(
    val png: String
)

data class Currency(
    val name: String,
    val symbol: String?
)


