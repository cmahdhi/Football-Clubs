package com.chiheb.footballclubs.presentation.ui.contracts

import com.chiheb.footballclubs.core.bases.BaseView
import com.chiheb.footballclubs.presentation.models.TeamDetails

interface ClubDetailsView : BaseView {

    fun toggleLoading(isLoading: Boolean)

    fun handleResult(teamDetails: TeamDetails)

    fun handleError()
}