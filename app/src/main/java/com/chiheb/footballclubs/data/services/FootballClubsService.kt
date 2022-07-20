package com.chiheb.footballclubs.data.services

import com.chiheb.footballclubs.data.entities.LeaguesEntity
import com.chiheb.footballclubs.data.entities.TeamDetailsListEntity
import com.chiheb.footballclubs.data.entities.TeamsEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FootballClubsService {

    @GET("api/v1/json/50130162/search_all_teams.php")
    suspend fun searchTeams(@Query("l") query: String): Response<TeamsEntity>

    @GET("api/v1/json/50130162/lookupteam.php")
    suspend fun getTeamDetails(@Query("id") teamId: String): Response<TeamDetailsListEntity>

    @GET("api/v1/json/50130162/all_leagues.php")
    suspend fun getLeagues(): Response<LeaguesEntity>
}