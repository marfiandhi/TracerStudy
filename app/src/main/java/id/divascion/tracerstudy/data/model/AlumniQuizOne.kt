package id.divascion.tracerstudy.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlumniQuizOne(
    var name: String = "",
    var gender: String = "",
    var schoolName: String? = null,
    var schoolProvince: String? = null,
    var schoolCity: String? = null,
    var schoolPostCode: String? = null,
    var bornProvince: String = "",
    var bornCity: String = "",
    var bornDate: String = "",
    var officeAddress: String? = null,
    var officePhone: String? = null,
    var officePostCode: String? = null,
    var addressProvince: String = "",
    var addressCity: String = "",
    var addressDistrict: String = "",
    var addressVillage: String = "",
    var addressPostCode: String? = null,
    var addressStreet: String = "",
    var addressPhone: String? = null,
    var addressMobilePhone: String = "",
    var email: String = "",
    var facebook: String? = null
) : Parcelable