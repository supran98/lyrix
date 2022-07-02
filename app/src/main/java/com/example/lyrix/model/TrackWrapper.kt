package com.example.lyrix.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TrackWrapper(
    val track: @RawValue Track
) : Parcelable