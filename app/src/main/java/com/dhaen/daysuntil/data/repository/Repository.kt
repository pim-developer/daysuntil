package com.dhaen.daysuntil.data.repository

import com.dhaen.daysuntil.domain.countdownevent.CountDownEventModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun readCountDownEvents(): Flow<List<CountDownEventModel>>

    suspend fun upsertCountDownEvent(
        idHexString: String?,
        name: String?,
        dateEpochSecondsUTC: Long?
    ): CountDownEventModel

    suspend fun deleteCountDownEvent(idHexString: String)
}