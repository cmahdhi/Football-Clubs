package com.chiheb.footballclubs.presentation.presenters

import com.chiheb.footballclubs.domain.interactors.GetTeamDetailsUseCase
import com.chiheb.footballclubs.presentation.models.Team
import com.chiheb.footballclubs.presentation.models.TeamDetails
import com.chiheb.footballclubs.presentation.ui.contracts.ClubDetailsView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class ClubDetailsPresenterTest {

    private val getTeamDetailsUseCase: GetTeamDetailsUseCase = mockk()

    private val view: ClubDetailsView = mockk {
        every { toggleLoading(any()) } returns Unit
        every { handleResult(any()) } returns Unit
        every { handleError() } returns Unit
    }

    private lateinit var presenter: ClubDetailsPresenter

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        presenter = ClubDetailsPresenter(getTeamDetailsUseCase)
        presenter.attachView(view)
    }

    @AfterEach
    fun tearDown() {
        presenter.detachView()
        Dispatchers.resetMain()
    }

    @Test
    fun `get team details failure`(): Unit = runTest {
        // Given
        coEvery { getTeamDetailsUseCase("1234") } returns Result.failure(Throwable("Team Details not available"))
        // When
        presenter.fetchTeamDetails(Team("1234", "Real Madrid", "badgeImage"))
        // Then
        coVerify(exactly = 1) { view.toggleLoading(true) }
        coVerify(exactly = 1) { view.toggleLoading(false) }
        coVerify(exactly = 1) { view.handleError() }
        coVerify(exactly = 0) { view.handleResult(any()) }
    }

    @Test
    fun `get team details success`(): Unit = runTest {
        // Given
        val teamDetails = TeamDetails(
            "Real Madrid",
            "bannerImage",
            "Spain",
            "LaLiga",
            "derscritpion"
        )

        coEvery { getTeamDetailsUseCase("1234") } returns Result.success(teamDetails)
        // When
        presenter.fetchTeamDetails(Team("1234", "Real Madrid", "badgeImage"))
        // Then
        coVerify(exactly = 1) { view.toggleLoading(true) }
        coVerify(exactly = 1) { view.toggleLoading(false) }
        coVerify(exactly = 1) { view.handleResult(teamDetails) }
        coVerify(exactly = 0) { view.handleError() }
    }
}