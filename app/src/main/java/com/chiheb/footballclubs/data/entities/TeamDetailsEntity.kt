package com.chiheb.footballclubs.data.entities

import com.google.gson.annotations.SerializedName

data class TeamDetailsListEntity(
    @SerializedName("teams")
    val teams: List<TeamDetailsEntity>
)

data class TeamDetailsEntity(
    @SerializedName("strTeam")
    val teamName: String,
    @SerializedName("strTeamBanner")
    val bannerImage: String?,
    @SerializedName("strCountry")
    val country: String,
    @SerializedName("strLeague")
    val league: String,
    @SerializedName("strDescriptionEN")
    val description: String
)