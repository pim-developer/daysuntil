package com.dhaen.daysuntil.data

import com.dhaen.daysuntil.domain.countdownevent.CountDownEventModel
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class CountDownEvent : RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()

    var name: String = ""

    //    https://www.mongodb.com/docs/realm-sdks/kotlin/latest/library-base/-realm%20-kotlin%20-s-d-k/io.realm/-realm-instant/index.html
    var dateEpochSecondsUTC: RealmInstant = RealmInstant.now()
}

fun CountDownEventModel.toCountDownEvent(): CountDownEvent {
    val domainModel = this
    return CountDownEvent().apply {
        this._id = ObjectId(domainModel.idHexString)
        this.name = domainModel.name
        this.dateEpochSecondsUTC = RealmInstant.from(domainModel.dateEpochSecondsUTC, 0)
    }
}

fun CountDownEvent.toCountDownEventModel(): CountDownEventModel {
    return CountDownEventModel(
        idHexString = _id.toHexString(),
        name = name,
        dateEpochSecondsUTC = dateEpochSecondsUTC.epochSeconds
    )
}