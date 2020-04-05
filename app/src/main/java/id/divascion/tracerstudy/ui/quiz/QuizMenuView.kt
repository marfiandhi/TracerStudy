package id.divascion.tracerstudy.ui.quiz

import id.divascion.tracerstudy.data.model.AlumniQuiz
import id.divascion.tracerstudy.data.model.StakeQuiz

interface QuizMenuView {
    fun showLoading()
    fun hideLoading(message: String)
    fun getData(data: AlumniQuiz?)
    fun getData(data: StakeQuiz?)
}