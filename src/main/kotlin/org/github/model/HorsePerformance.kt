package org.github.model

import io.micronaut.core.annotation.Creator
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.Nullable
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonProperty

@Introspected
data class HorsePerformance @Creator @BsonCreator constructor(
    @BsonProperty("horseName")
    var horseName: String,
    @BsonProperty("startNumber")
    var startNumber: Int,
    @BsonProperty("startTrack")
    var startTrack: Int,
    @BsonProperty("distance")
    var distance: Int,
    @Nullable
    @BsonProperty("driverName")
    var driverName: String?,
    @BsonProperty("coachName")
    var coachName: String?,
    @BsonProperty("winner")
    var winner: Boolean,
    @BsonProperty("time")
    var time: Float,
    @BsonProperty("rear_shoes")
    var rearShoes: Boolean,
    @BsonProperty("front_shoes")
    var frontShoes: Boolean,
    @BsonProperty("year")
    var year: Int,
    @BsonProperty("month")
    var month: Int,
    @BsonProperty("day")
    var day: Int,
    @BsonProperty("breed")
    var breed: String,
    @BsonProperty("car_start")
    var carStart: Boolean,
    @BsonProperty("track")
    var track: String
)
