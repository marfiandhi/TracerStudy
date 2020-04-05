package id.divascion.tracerstudy.ui.alumni

import id.divascion.tracerstudy.data.model.AlumniList

interface DataAlumniView {
    fun showLoading()
    fun hideLoading(message: String)
    fun getData(listAlumni: ArrayList<AlumniList>)
}