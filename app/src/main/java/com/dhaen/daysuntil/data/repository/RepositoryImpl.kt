package com.dhaen.daysuntil.data.repository

import com.dhaen.daysuntil.data.CountDownEvent
import com.dhaen.daysuntil.data.toCountDownEventModel
import com.dhaen.daysuntil.domain.countdownevent.CountDownEventModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmInstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.BsonObjectId
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localRealm: Realm,
) : Repository {

    override fun readCountDownEvents(): Flow<List<CountDownEventModel>> {
        return localRealm
            .query<CountDownEvent>()
            .find()
            .asFlow()
            .map { resultsChange -> resultsChange.list.map { it.toCountDownEventModel() } }
    }

    // TODO: log errors that shouldn't happen
    // TODO: add a safe fail strategy?
    override suspend fun upsertCountDownEvent(
        idHexString: String?,
        name: String?,
        dateEpochSecondsUTC: Long?
    ): CountDownEventModel {
        var existingCountDownEvent: CountDownEvent? = null
        idHexString?.let {
            existingCountDownEvent = localRealm.query<CountDownEvent>(
                "_id == $0",
                BsonObjectId(it)
            ).first().find()
        }
        return localRealm.writeBlocking {
            if (idHexString == null) { // NEW OBJECT
                val latest: CountDownEvent = copyToRealm(CountDownEvent().apply {
                    this.name = name!!
                    this.dateEpochSecondsUTC = RealmInstant.from(dateEpochSecondsUTC!!, 0)
                })
                return@writeBlocking latest
            } else { // UPDATE
                val latest: CountDownEvent = findLatest(existingCountDownEvent!!)!!
                latest.let { e ->
                    name?.let {
                        e.name = it
                    }
                    dateEpochSecondsUTC?.let {
                        e.dateEpochSecondsUTC = RealmInstant.from(it, 0)
                    }
                }
                return@writeBlocking latest
            }
        }.toCountDownEventModel()
    }

    // TODO: log errors that shouldn't happen
    // TODO: add a safe fail strategy?
    override suspend fun deleteCountDownEvent(idHexString: String) {
        val existingCountDownEvent = localRealm.query<CountDownEvent>(
            "_id == $0",
            BsonObjectId(idHexString)
        ).first().find()
        localRealm.writeBlocking {
            delete(findLatest(existingCountDownEvent!!)!!)
        }
    }

}