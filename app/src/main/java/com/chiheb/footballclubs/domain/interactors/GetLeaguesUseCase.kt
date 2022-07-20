package com.chiheb.footballclubs.domain.interactors

import com.chiheb.footballclubs.core.bases.BaseUseCase
import com.chiheb.footballclubs.domain.models.extensions.toLeagueModel
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import com.chiheb.footballclubs.presentation.models.League
import javax.inject.Inject

class GetLeaguesUseCase @Inject constructor(
    private val repository: IFootballClubsRepository
) : BaseUseCase<String, List<League>> {

    override suspend fun invoke(param: String): Result<List<League>> {
        return repository.getLeagues(param).mapCatching { it.toLeagueModel() }
    }
}