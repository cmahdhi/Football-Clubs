package com.chiheb.footballclubs.core.bases

interface BaseUseCase<P, R> {
    suspend operator fun invoke(param: P): Result<R>
}