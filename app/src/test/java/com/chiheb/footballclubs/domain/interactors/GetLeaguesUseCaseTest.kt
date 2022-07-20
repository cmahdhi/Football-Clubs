package com.chiheb.footballclubs.domain.interactors

import com.chiheb.footballclubs.domain.models.LeagueDomain
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
internal class GetLeaguesUseCaseTest {

    private val repository: IFootballClubsRepository = mockk()

    private lateinit var useCase: GetLeaguesUseCase

    @BeforeEach
    fun setup() {
        useCase = GetLeaguesUseCase(repository)
    }

    @Test
    fun `get leagues fails`(): Unit = runTest {
        // Given
        coEvery { repository.getLeagues(any()) } returns Result.failure(Throwable("Leagues not available"))
        // When
        val result = useCase("spanish")
        // Then
        assertTrue(result.isFailure)
        assertEquals("Leagues not available", result.exceptionOrNull()?.message)
    }

    @Test
    fun `get leagues returns success`(): Unit = runTest {
        // Given
        coEvery { repository.getLeagues(any()) } returns Result.success(
            listOf(
                LeagueDomain("Spanish La Liga"),
                LeagueDomain("Spanish La Liga 2")
            )
        )
        // When
        val result = useCase("spanish")
        // Then
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()?.size, 2)
    }
}