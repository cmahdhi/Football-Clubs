package com.chiheb.footballclubs.data.repositories

import com.chiheb.footballclubs.data.datasources.FootballClubsDataSource
import com.chiheb.footballclubs.domain.models.LeagueDomain
import com.chiheb.footballclubs.domain.models.TeamDetailsDomain
import com.chiheb.footballclubs.domain.models.TeamDomain
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class FootballClubsRepositoryTest {

    private val dataSource: FootballClubsDataSource = mockk()

    private lateinit var repository: IFootballClubsRepository

    @BeforeEach
    fun setup() {
        repository = FootballClubsRepositoryImpl(dataSource)
    }


    @Test
    fun `get leagues returns error`() = runTest {
        // Given
        coEvery { dataSource.getLeagues() } returns Result.failure(Throwable())
        // When
        val result = repository.getLeagues("Spanish")
        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `get leagues returns success`(): Unit = runTest {
        // Given
        coEvery { dataSource.getLeagues() } returns Result.success(
            listOf(
                LeagueDomain("Spanish La Liga"),
                LeagueDomain("Spanish La Liga 2"),
                LeagueDomain("French Ligue 1"),
            )
        )
        // When
        val result = repository.getLeagues("spanish")
        // Then
        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertNotNull(list)
        assertTrue(list?.size == 2)
    }

    @Test
    fun `search teams returns error`() = runTest {
        // Given
        coEvery { dataSource.searchTeams(any()) } returns Result.failure(Throwable("Teams not available"))
        // When
        val result = repository.searchTeams("LaLiga")
        // Then
        assertTrue(result.isFailure)
        val throwable = result.exceptionOrNull()
        assertNotNull(throwable)
        assertEquals("Teams not available", throwable?.message)
    }

    @Test
    fun `search teams returns success`() = runTest {
        // Given
        coEvery { dataSource.searchTeams(any()) } returns Result.success(
            listOf(
                TeamDomain("1234", "Real Madrid", "badgeImage"),
                TeamDomain("9876", "Barcelona", "badgeImage")
            )
        )
        // When
        val result = repository.searchTeams("LaLiga")
        // Then
        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertNotNull(list)
        assertTrue(list?.size == 2)
    }

    @Test
    fun `get team details returns error`() = runTest {
        // Given
        coEvery { dataSource.getTeamDetails(any()) } returns Result.failure(Throwable("Team Details not available"))
        // When
        val result = repository.getTeamDetails("1234")
        // Then
        assertTrue(result.isFailure)
        val throwable = result.exceptionOrNull()
        assertNotNull(throwable)
        assertEquals("Team Details not available", throwable?.message)
    }

    @Test
    fun `get team details returns success`() = runTest {
        // Given
        coEvery { dataSource.getTeamDetails(any()) } returns Result.success(
            TeamDetailsDomain(
                "Real Madrid",
                "bannerImage",
                "Spain",
                "LaLiga",
                "derscritpion"
            )
        )
        // When
        val result = repository.getTeamDetails("1234")
        // Then
        assertTrue(result.isSuccess)
        val details = result.getOrNull()
        assertNotNull(details)
    }
}