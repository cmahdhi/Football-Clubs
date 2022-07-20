package com.chiheb.footballclubs.domain.models

data class TeamDetailsDomain(
    val teamName: String,
    val bannerImage: String?,
    val country: String,
    val league: String,
    val description: String
)