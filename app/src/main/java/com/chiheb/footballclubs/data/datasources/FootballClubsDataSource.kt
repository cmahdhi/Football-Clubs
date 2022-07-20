package com.chiheb.footballclubs.data.datasources

import com.chiheb.footballclubs.core.network.runSafeApiCall
import com.chiheb.footballclubs.data.entities.extensions.toDomain
import com.chiheb.footballclubs.data.services.FootballClubsService
import com.chiheb.footballclubs.domain.models.LeagueDomain
import com.chiheb.footballclubs.domain.models.TeamDetailsDomain
import com.chiheb.footballclubs.domain.models.TeamDomain
import javax.inject.Inject

class FootballClubsDataSource @Inject constructor(private val apiService: FootballClubsService) {

    suspend fun searchTeams(query: String): Result<List<TeamDomain>> = runSafeApiCall {
        apiService.searchTeams(query).let { response ->
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body.teams.toDomain())
            } else {
                Result.failure(Throwable("Teams not available"))
            }
        }
    }

    suspend fun getLeagues(): Result<List<LeagueDomain>> = runSafeApiCall {
        apiService.getLeagues().let { response ->
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body.toDomain())
            } else {
                Result.failure(Throwable("Leagues not available"))
            }
        }
    }

    suspend fun getTeamDetails(teamId: String): Result<TeamDetailsDomain> = runSafeApiCall {
        apiService.getTeamDetails(teamId).let { response ->
            val body = response.body()
            if (response.isSuccessful && body != null && body.teams.isNotEmpty()) {
                Result.success(body.teams.first().toDomain())
            } else {
                Result.failure(Throwable("Team Details not available"))
            }
        }
    }
}