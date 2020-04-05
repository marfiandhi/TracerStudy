package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniList(
    var uid: String = "",
    var name: String = "",
    var gender: String = "",
    var major: String = "",
    var entrance: String = "",
    var graduate: String = ""
) : Parcelable