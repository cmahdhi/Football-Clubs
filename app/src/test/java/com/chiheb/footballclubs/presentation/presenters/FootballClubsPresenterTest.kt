package com.chiheb.footballclubs.presentation.presenters

import com.chiheb.footballclubs.domain.interactors.GetLeaguesUseCase
import com.chiheb.footballclubs.domain.interactors.SearchTeamsUseCase
import com.chiheb.footballclubs.presentation.models.League
import com.chiheb.footballclubs.presentation.models.Team
import com.chiheb.footballclubs.presentation.ui.contracts.ClubsListView
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
internal class FootballClubsPresenterTest {

    private val teamsUseCase: SearchTeamsUseCase = mockk()
    private val leaguesUseCase: GetLeaguesUseCase = mockk()

    private val view: ClubsListView = mockk {
        every { toggleLoading(any()) } returns Unit
        every { handleResult(any()) } returns Unit
        every { handleLeaguesResult(any()) } returns Unit
        every { handleError() } returns Unit
    }

    private lateinit var presenter: FootballClubsPresenter

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        presenter = FootballClubsPresenter(teamsUseCase, leaguesUseCase)
        presenter.attachView(view)
    }

    @AfterEach
    fun tearDown() {
        presenter.detachView()
        Dispatchers.resetMain()
    }

    @Test
    fun `search teams failure`(): Unit = runTest {
        // Given
        coEvery { teamsUseCase(any()) } returns Result.failure(Throwable("Teams not available"))
        // When
        presenter.searchTeams("LaLiga")
        // Then
        coVerify(exactly = 1) { view.toggleLoading(true) }
        coVerify(exactly = 1) { view.toggleLoading(false) }
        coVerify(exactly = 1) { view.handleError() }
        coVerify(exactly = 0) { view.handleResult(any()) }
    }

    @Test
    fun `search teams success`(): Unit = runTest {
        // Given
        val list = listOf(
            Team("1234", "Real Madrid", "badgeImage"),
            Team("9876", "Barcelona", "badgeImage")
        )
        coEvery { teamsUseCase(any()) } returns Result.success(list)
        // When
        presenter.searchTeams("LaLiga")
        // Then
        coVerify(exactly = 1) { view.toggleLoading(true) }
        coVerify(exactly = 1) { view.toggleLoading(false) }
        coVerify(exactly = 1) { view.handleResult(list) }
        coVerify(exactly = 0) { view.handleError() }
    }

    @Test
    fun `get leagues failure`(): Unit = runTest {
        // Given
        coEvery { leaguesUseCase(any()) } returns Result.failure(Throwable("Leagues not available"))
        // When
        presenter.getLeagues("spanish")
        // Then
        coVerify(exactly = 0) { view.toggleLoading(any()) }
        coVerify(exactly = 0) { view.handleError() }
        coVerify(exactly = 0) { view.handleResult(any()) }
        coVerify(exactly = 0) { view.handleLeaguesResult(any()) }
    }

    @Test
    fun `get leagues success`(): Unit = runTest {
        // Given
        val list = listOf(
            League("Spanish La Liga"),
            League("Spanish La Liga 2")
        )
        coEvery { leaguesUseCase(any()) } returns Result.success(list)
        // When
        presenter.getLeagues("spanish")
        // Then
        coVerify(exactly = 0) { view.toggleLoading(any()) }
        coVerify(exactly = 0) { view.handleError() }
        coVerify(exactly = 0) { view.handleResult(any()) }
        coVerify(exactly = 1) { view.handleLeaguesResult(list) }
    }
}