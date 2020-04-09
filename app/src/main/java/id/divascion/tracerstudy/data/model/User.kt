package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var email: String = "",
    var role: String = "",
    var createAt: String = ""
) : Parcelable