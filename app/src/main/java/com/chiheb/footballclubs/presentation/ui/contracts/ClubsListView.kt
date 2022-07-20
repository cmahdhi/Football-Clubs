package com.chiheb.footballclubs.presentation.ui.contracts

import com.chiheb.footballclubs.core.bases.BaseView
import com.chiheb.footballclubs.presentation.models.League
import com.chiheb.footballclubs.presentation.models.Team

interface ClubsListView : BaseView {

    fun toggleLoading(isLoading: Boolean)

    fun handleResult(teams: List<Team>)

    fun handleError()

    fun handleLeaguesResult(leagues: List<League>)
}