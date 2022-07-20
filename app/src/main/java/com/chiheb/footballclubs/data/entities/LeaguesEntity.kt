package com.chiheb.footballclubs.data.entities

import com.google.gson.annotations.SerializedName

data class LeaguesEntity(
    @SerializedName("leagues")
    val leagues: List<LeagueEntity>
)

data class LeagueEntity(
    @SerializedName("strLeague")
    val leagueName: String,
    @SerializedName("strSport")
    val leagueSport: String?
)