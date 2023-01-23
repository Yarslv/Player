package com.yprodan.player.network.service

import com.yprodan.player.network.response.Either
import retrofit2.Response

class ApiAnswerService() {

    fun <T, E> extractAnswer(response: Response<E>): Either<T> {
        val body = response.body()

        if (!response.isSuccessful || body == null) {
            return Either.failure(Exception())
        }
        return Either.success(body as T)
    }
}