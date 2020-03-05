package com.firstkotlin.mauriciofeijo.firstkotlin.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(val name: String,
                      val description: String) : Parcelable