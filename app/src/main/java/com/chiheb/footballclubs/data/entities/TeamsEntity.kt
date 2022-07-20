package com.chiheb.footballclubs.data.entities

import com.google.gson.annotations.SerializedName

data class TeamsEntity(
    @SerializedName("teams")
    val teams: List<TeamEntity>
)

data class TeamEntity(
    @SerializedName("idTeam")
    val teamId: String,
    @SerializedName("strTeam")
    val teamName: String,
    @SerializedName("strTeamBadge")
    val badgeImage: String
)