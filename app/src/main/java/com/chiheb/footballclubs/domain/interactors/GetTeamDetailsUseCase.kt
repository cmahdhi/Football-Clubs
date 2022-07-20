package com.chiheb.footballclubs.domain.interactors

import com.chiheb.footballclubs.core.bases.BaseUseCase
import com.chiheb.footballclubs.domain.models.extensions.toModel
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import com.chiheb.footballclubs.presentation.models.TeamDetails
import javax.inject.Inject

class GetTeamDetailsUseCase @Inject constructor(
    private val repository: IFootballClubsRepository
) : BaseUseCase<String, TeamDetails> {

    override suspend fun invoke(param: String): Result<TeamDetails> {
        return repository.getTeamDetails(param).mapCatching { it.toModel() }
    }
}