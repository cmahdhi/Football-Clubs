package com.chiheb.footballclubs.domain.interactors

import com.chiheb.footballclubs.domain.models.TeamDomain
import com.chiheb.footballclubs.domain.repositories.IFootballClubsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class SearchTeamsUseCaseTest {

    private val repository: IFootballClubsRepository = mockk()

    private lateinit var useCase: SearchTeamsUseCase

    @BeforeEach
    fun setup() {
        useCase = SearchTeamsUseCase(repository)
    }

    @Test
    fun `search teams fails`(): Unit = runTest {
        // Given
        coEvery { repository.searchTeams(any()) } returns Result.failure(Throwable("Teams not available"))
        // When
        val result = useCase("LaLiga")
        // Then
        assertTrue(result.isFailure)
        assertEquals("Teams not available", result.exceptionOrNull()?.message)
    }

    @Test
    fun `search teams returns success`(): Unit = runTest {
        // Given
        coEvery { repository.searchTeams(any()) } returns Result.success(
            listOf(
                TeamDomain("1234", "Real Madrid", "badgeImage"),
                TeamDomain("9876", "Barcelona", "badgeImage")
            )
        )
        // When
        val result = useCase("LaLiga")
        // Then
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.size, 2)
    }
}