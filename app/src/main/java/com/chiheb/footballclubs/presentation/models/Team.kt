package com.chiheb.footballclubs.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val teamId: String,
    val teamName: String,
    val badgeImage: String
) : Parcelable