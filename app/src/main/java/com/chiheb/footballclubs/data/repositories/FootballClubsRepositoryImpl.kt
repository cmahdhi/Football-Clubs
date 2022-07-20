package com.chiheb.footballclubs.data.repositories

import com.chiheb.footballclubs.data.datasources.FootballClubsDataSource
import com.chiheb.footballclubs.domain.models.LeagueDomain
import com.chiheb.footballclubs.domain.models.TeamDetailsDomain
import com.chiheb.footballclubs.domain.models.TeamDomain
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import javax.inject.Inject

class FootballClubsRepositoryImpl @Inject constructor(private val dataSource: FootballClubsDataSource) :
    IFootballClubsRepository {

    override suspend fun searchTeams(query: String): Result<List<TeamDomain>> =
        dataSource.searchTeams(query)

    override suspend fun getTeamDetails(teamId: String): Result<TeamDetailsDomain> =
        dataSource.getTeamDetails(teamId)

    override suspend fun getLeagues(query: String): Result<List<LeagueDomain>> {
        val leagues = dataSource.getLeagues().getOrNull()
        val result = leagues?.filter { it.leagueName.contains(query, true) }.orEmpty()
        return Result.success(result)
    }
}