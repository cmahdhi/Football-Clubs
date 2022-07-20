package com.chiheb.footballclubs.data.datasources

import com.chiheb.footballclubs.data.entities.*
import com.chiheb.footballclubs.data.services.FootballClubsService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class FootballClubsDataSourceTest {

    private val apiService: FootballClubsService = mockk()

    private lateinit var dataSource: FootballClubsDataSource

    @BeforeEach
    fun setup() {
        dataSource = FootballClubsDataSource(apiService)
    }

    @Test
    fun `get leagues returns error`() = runTest {
        // Given
        coEvery { apiService.getLeagues() } returns Response.error(500, "Error".toResponseBody())
        // When
        val result = dataSource.getLeagues()
        // Then
        assertTrue(result.isFailure)
        val throwable = result.exceptionOrNull()
        assertNotNull(throwable)
        assertEquals("Leagues not available", throwable?.message)
    }

    @Test
    fun `get leagues returns success`() = runTest {
        // Given
        coEvery { apiService.getLeagues() } returns Response.success(
            LeaguesEntity(
                leagues = listOf(
                    LeagueEntity("LaLiga", "Soccer"),
                    LeagueEntity("NBA", "Basketball"),
                )
            )
        )
        // When
        val result = dataSource.getLeagues()
        // Then
        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertNotNull(list)
        assertTrue(list?.size == 1)
    }

    @Test
    fun `search teams returns error`() = runTest {
        // Given
        coEvery { apiService.searchTeams(any()) } returns Response.error(
            500,
            "Error".toResponseBody()
        )
        // When
        val result = dataSource.searchTeams("LaLiga")
        // Then
        assertTrue(result.isFailure)
        val throwable = result.exceptionOrNull()
        assertNotNull(throwable)
        assertEquals("Teams not available", throwable?.message)
    }

    @Test
    fun `search teams returns success`() = runTest {
        // Given
        coEvery { apiService.searchTeams(any()) } returns Response.success(
            TeamsEntity(
                teams = listOf(
                    TeamEntity("1234", "Real Madrid", "badgeImage"),
                    TeamEntity("9876", "PSG", "badgeImage")
                )
            )
        )
        // When
        val result = dataSource.searchTeams("LaLiga")
        // Then
        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertNotNull(list)
        assertTrue(list?.size == 2)
    }

    @Test
    fun `get team details returns error`() = runTest {
        // Given
        coEvery { apiService.getTeamDetails(any()) } returns Response.error(
            500,
            "Error".toResponseBody()
        )
        // When
        val result = dataSource.getTeamDetails("1234")
        // Then
        assertTrue(result.isFailure)
        val throwable = result.exceptionOrNull()
        assertNotNull(throwable)
        assertEquals("Team Details not available", throwable?.message)
    }

    @Test
    fun `get team details returns success`() = runTest {
        // Given
        coEvery { apiService.getTeamDetails(any()) } returns Response.success(
            TeamDetailsListEntity(
                teams = listOf(
                    TeamDetailsEntity(
                        "Real Madrid",
                        "bannerImage",
                        "Spain",
                        "LaLiga",
                        "derscritpion"
                    ),
                )
            )
        )
        // When
        val result = dataSource.getTeamDetails("1234")
        // Then
        assertTrue(result.isSuccess)
        val details = result.getOrNull()
        assertNotNull(details)
    }
}