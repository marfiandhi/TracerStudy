package id.divascion.tracerstudy.ui.main

import id.divascion.tracerstudy.data.model.StakeQuiz

interface MainView {
    fun getData(list: ArrayList<StakeQuiz>)
    fun showLoading()
    fun hideLoading(message: String)
}