package com.chiheb.footballclubs.presentation.presenters

import com.chiheb.footballclubs.core.bases.BasePresenter
import com.chiheb.footballclubs.domain.interactors.GetLeaguesUseCase
import com.chiheb.footballclubs.domain.interactors.SearchTeamsUseCase
import com.chiheb.footballclubs.presentation.models.League
import com.chiheb.footballclubs.presentation.models.Team
import com.chiheb.footballclubs.presentation.ui.contracts.ClubsListView
import kotlinx.coroutines.launch
import javax.inject.Inject

class FootballClubsPresenter @Inject constructor(
    private val teamsUseCase: SearchTeamsUseCase,
    private val leaguesUseCase: GetLeaguesUseCase
) : BasePresenter<ClubsListView>() {

    fun searchTeams(query: String) {
        notifyView { view?.toggleLoading(true) }
        launch {
            teamsUseCase(query)
                .onSuccess { teams ->
                    if (teams.isNotEmpty()) {
                        handleResult(teams)
                    } else {
                        handleError()
                    }
                }
                .onFailure {
                    handleError()
                }
        }
    }

    fun getLeagues(query: String) {
        launch {
            leaguesUseCase(query)
                .onSuccess { leagues ->
                    handleLeaguesResult(leagues)
                }
        }
    }

    private fun handleResult(teams: List<Team>) {
        notifyView {
            view?.apply {
                toggleLoading(false)
                handleResult(teams)
            }
        }
    }

    private fun handleError() {
        notifyView {
            view?.apply {
                toggleLoading(false)
                handleError()
            }
        }
    }

    private fun handleLeaguesResult(leagues: List<League>) {
        notifyView {
            view?.handleLeaguesResult(leagues)
        }
    }
}