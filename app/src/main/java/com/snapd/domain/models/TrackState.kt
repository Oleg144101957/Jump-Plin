package com.snapd.domain.models

const val EMPTY = "EMPTY"
data class TrackState(
    val adbState: String = EMPTY,
    val ref: String = EMPTY,
    val gaid: String = EMPTY
)
