package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StakeQuizOne(
    var nameCompany: String = "",
    var addressCompany: String  = "",
    var phoneCompany: String = "",
    var faxCompany: String? = null,
    var emailCompany: String = "",
    var typeCompany: String = "",
    var numberWorkerCompany: String = "",
    var numberAviatorCompany: String? = null,
    var numberPoltekbangAviatorCompany: Int = -1
) : Parcelable