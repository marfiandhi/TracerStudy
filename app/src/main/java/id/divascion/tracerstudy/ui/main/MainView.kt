package id.divascion.tracerstudy.ui.main

import id.divascion.tracerstudy.data.model.StakeQuiz
import id.divascion.tracerstudy.data.model.User

interface MainView {
    fun getData(list: ArrayList<StakeQuiz>)
    fun getUser(user: User)
    fun showLoading()
    fun hideLoading(message: String)
}