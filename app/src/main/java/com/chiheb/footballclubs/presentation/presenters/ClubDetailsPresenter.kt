package com.chiheb.footballclubs.presentation.presenters

import com.chiheb.footballclubs.core.bases.BasePresenter
import com.chiheb.footballclubs.domain.interactors.GetTeamDetailsUseCase
import com.chiheb.footballclubs.presentation.models.Team
import com.chiheb.footballclubs.presentation.models.TeamDetails
import com.chiheb.footballclubs.presentation.ui.contracts.ClubDetailsView
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClubDetailsPresenter @Inject constructor(
    private val useCase: GetTeamDetailsUseCase
) : BasePresenter<ClubDetailsView>() {

    fun fetchTeamDetails(team: Team) {
        notifyView { view?.toggleLoading(true) }
        launch {
            useCase(team.teamId)
                .onSuccess { teamDetails ->
                    handleResult(teamDetails)
                }
                .onFailure {
                    handleError()
                }
        }
    }

    private fun handleResult(teamDetails: TeamDetails) {
        notifyView {
            view?.apply {
                toggleLoading(false)
                handleResult(teamDetails)
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
}