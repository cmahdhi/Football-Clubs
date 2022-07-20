package com.chiheb.footballclubs.domain.repositories

import com.chiheb.footballclubs.domain.models.LeagueDomain
import com.chiheb.footballclubs.domain.models.TeamDetailsDomain
import com.chiheb.footballclubs.domain.models.TeamDomain

interface IFootballClubsRepository {

    suspend fun searchTeams(query: String): Result<List<TeamDomain>>

    suspend fun getTeamDetails(teamId: String): Result<TeamDetailsDomain>

    suspend fun getLeagues(query: String): Result<List<LeagueDomain>>
}