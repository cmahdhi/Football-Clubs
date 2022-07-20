package com.chiheb.footballclubs.presentation.models

data class TeamDetails(
    val teamName: String,
    val bannerImage: String?,
    val country: String,
    val league: String,
    val description: String
)