package com.chiheb.footballclubs.domain.interactors

import com.chiheb.footballclubs.core.bases.BaseUseCase
import com.chiheb.footballclubs.domain.models.extensions.toModel
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import com.chiheb.footballclubs.presentation.models.Team
import javax.inject.Inject

class SearchTeamsUseCase @Inject constructor(
    private val repository: IFootballClubsRepository
) : BaseUseCase<String, List<Team>> {

    override suspend fun invoke(param: String): Result<List<Team>> {
        return repository.searchTeams(param).mapCatching { it.toModel() }
    }
}