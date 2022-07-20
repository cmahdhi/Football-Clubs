package com.chiheb.footballclubs.domain.interactors

import com.chiheb.footballclubs.domain.models.TeamDetailsDomain
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetTeamDetailsUseCaseTest {

    private val repository: IFootballClubsRepository = mockk()

    private lateinit var useCase: GetTeamDetailsUseCase

    @BeforeEach
    fun setup() {
        useCase = GetTeamDetailsUseCase(repository)
    }

    @Test
    fun `get team details fails`(): Unit = runTest {
        // Given
        coEvery { repository.getTeamDetails(any()) } returns Result.failure(Throwable("Team Details not available"))
        // When
        val result = useCase("1234")
        // Then
        assertTrue(result.isFailure)
        assertEquals("Team Details not available", result.exceptionOrNull()?.message)
    }

    @Test
    fun `get team details returns success`(): Unit = runTest {
        // Given
        coEvery { repository.getTeamDetails(any()) } returns Result.success(
            TeamDetailsDomain(
                "Real Madrid",
                "bannerImage",
                "Spain",
                "LaLiga",
                "derscritpion"
            )
        )
        // When
        val result = useCase("1234")
        // Then
        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull())
    }
}