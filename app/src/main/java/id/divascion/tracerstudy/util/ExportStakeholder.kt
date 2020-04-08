package id.divascion.tracerstudy.util

import id.divascion.tracerstudy.data.model.StakeQuiz
import id.divascion.tracerstudy.ui.main.MainView
import jxl.write.WriteException
import java.util.*
import kotlin.collections.ArrayList


class ExportStakeholder(var view: MainView) {

    private lateinit var excel: Excel
    private lateinit var date: String
    private var calendar = Calendar.getInstance()

    fun export(list: List<StakeQuiz>) {
        view.showLoading()
        excel = Excel()
        val millis = calendar.timeInMillis
        val date = calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        this.date = "$year-$month-$date-$millis"
        val workBook = excel.createWorkbook("Data Stakeholder-${this.date}.xlsx")
        if (workBook != null) {
            val workSheet = excel.createSheet(workBook, "stakeholder", 1)
            val question = question()
            val answer = answer(list)
            var col = 0
            var row = 0
            for (values in question) {
                excel.writeCell(col, row, values, true, workSheet)
                col++
            }
            for (array in answer) {
                row++
                col = 0
                for (values in array) {
                    excel.writeCell(col, row, values, false, workSheet)
                    col++
                }
            }
            workBook.write()
            try {
                workBook.close()
                view.hideLoading("Telah berhasil mengekspor data di folder TracerStudy")
            } catch (e: WriteException) {
                e.printStackTrace()
                view.hideLoading("${e.localizedMessage}, ${e.message}")
            }
        } else {
            view.hideLoading("Gagal membuat file excel")
        }
    }

    private fun answer(list: List<StakeQuiz>): ArrayList<Array<String>> {
        val newList = ArrayList<Array<String>>()
        for (i in list.indices) {
            var quiz = arrayOf<String>()
            quiz += list[i].stakeQuizOne!!.nameCompany
            quiz += list[i].stakeQuizOne!!.addressCompany
            quiz += list[i].stakeQuizOne!!.phoneCompany
            quiz += list[i].stakeQuizOne!!.faxCompany.toString()
            quiz += list[i].stakeQuizOne!!.emailCompany
            quiz += list[i].stakeQuizOne!!.typeCompany
            quiz += list[i].stakeQuizOne!!.numberWorkerCompany
            quiz += list[i].stakeQuizOne!!.numberAviatorCompany.toString()
            quiz += list[i].stakeQuizOne!!.numberPoltekbangAviatorCompany.toString()

            quiz += list[i].stakeQuizTwo!!.infoDissemination
            quiz += list[i].stakeQuizTwo!!.companySelection
            quiz += list[i].stakeQuizTwo!!.isPeriodicRecruitment
            quiz += list[i].stakeQuizTwo!!.recruitmentIntensity.toString()
            quiz += list[i].stakeQuizTwo!!.majorSuitabilityExisting
            quiz += list[i].stakeQuizTwo!!.majorSuitabilitySuitable
            quiz += list[i].stakeQuizTwo!!.attitudeExisting
            quiz += list[i].stakeQuizTwo!!.attitudeSuitable
            quiz += list[i].stakeQuizTwo!!.achievementExisting
            quiz += list[i].stakeQuizTwo!!.achievementSuitable
            quiz += list[i].stakeQuizTwo!!.practicalInCampusExisting
            quiz += list[i].stakeQuizTwo!!.practicalInCampusSuitable
            quiz += list[i].stakeQuizTwo!!.practicalOutCampusExisting
            quiz += list[i].stakeQuizTwo!!.practicalOutCampusSuitable
            quiz += list[i].stakeQuizTwo!!.campusFameExisting
            quiz += list[i].stakeQuizTwo!!.campusFameSuitable
            quiz += list[i].stakeQuizTwo!!.workExperienceExisting
            quiz += list[i].stakeQuizTwo!!.workExperienceSuitable
            quiz += list[i].stakeQuizTwo!!.foreignLanguageExisting
            quiz += list[i].stakeQuizTwo!!.foreignLanguageSuitable
            quiz += list[i].stakeQuizTwo!!.computerExisting
            quiz += list[i].stakeQuizTwo!!.computerSuitable
            quiz += list[i].stakeQuizTwo!!.recommendationThirdPartyExisting
            quiz += list[i].stakeQuizTwo!!.recommendThirdPartySuitable
            quiz += list[i].stakeQuizTwo!!.testResultExisting
            quiz += list[i].stakeQuizTwo!!.testResultSuitable
            quiz += list[i].stakeQuizTwo!!.appearanceInterviewExisting
            quiz += list[i].stakeQuizTwo!!.appearanceInterviewSuitable
            quiz += list[i].stakeQuizTwo!!.personalityExisting
            quiz += list[i].stakeQuizTwo!!.personalitySuitable
            quiz += list[i].stakeQuizTwo!!.placeOfOriginExisting
            quiz += list[i].stakeQuizTwo!!.placeOfOriginSuitable
            quiz += list[i].stakeQuizTwo!!.otherAspect.toString()
            quiz += list[i].stakeQuizTwo!!.otherAspectExisting.toString()
            quiz += list[i].stakeQuizTwo!!.otherAspectSuitable.toString()

            quiz += list[i].stakeQuizThree!!.aviatorKnowledgeBasedOnMajorExisting
            quiz += list[i].stakeQuizThree!!.aviatorKnowledgeBasedOnMajorSuitable
            quiz += list[i].stakeQuizThree!!.workSkillsExisting
            quiz += list[i].stakeQuizThree!!.worksSkillsSuitable
            quiz += list[i].stakeQuizThree!!.professionalEthicsExisting
            quiz += list[i].stakeQuizThree!!.professionalEthicsSuitable
            quiz += list[i].stakeQuizThree!!.fitnessExisting
            quiz += list[i].stakeQuizThree!!.fitnessSuitable
            quiz += list[i].stakeQuizThree!!.moralExisting
            quiz += list[i].stakeQuizThree!!.moralSuitable
            quiz += list[i].stakeQuizThree!!.senseOfManagerialExisting
            quiz += list[i].stakeQuizThree!!.senseOfManagerialSuitable
            quiz += list[i].stakeQuizThree!!.senseOfLeadershipExisting
            quiz += list[i].stakeQuizThree!!.senseOfLeadershipSuitable
            quiz += list[i].stakeQuizThree!!.communicateExisting
            quiz += list[i].stakeQuizThree!!.communicateSuitable
            quiz += list[i].stakeQuizThree!!.foreignLanguageCommunicateExisting
            quiz += list[i].stakeQuizThree!!.foreignLanguageCommunicateSuitable
            quiz += list[i].stakeQuizThree!!.usingTechnologyExisting
            quiz += list[i].stakeQuizThree!!.usingTechnologySuitable
            quiz += list[i].stakeQuizThree!!.selfDevelopmentExisting
            quiz += list[i].stakeQuizThree!!.selfDevelopmentSuitable
            quiz += list[i].stakeQuizThree!!.creativityExisting
            quiz += list[i].stakeQuizThree!!.creativitySuitable
            quiz += list[i].stakeQuizThree!!.initiativeExisting
            quiz += list[i].stakeQuizThree!!.initiativeSuitable
            quiz += list[i].stakeQuizThree!!.workUnderPressureExisting
            quiz += list[i].stakeQuizThree!!.workUnderPressureSuitable
            quiz += list[i].stakeQuizThree!!.independenceExisting
            quiz += list[i].stakeQuizThree!!.independenceSuitable
            quiz += list[i].stakeQuizThree!!.problemSolvingExisting
            quiz += list[i].stakeQuizThree!!.problemSolvingSuitable
            quiz += list[i].stakeQuizThree!!.visionaryExisting
            quiz += list[i].stakeQuizThree!!.visionarySuitable
            quiz += list[i].stakeQuizThree!!.loyaltyExisting
            quiz += list[i].stakeQuizThree!!.loyaltySuitable
            quiz += list[i].stakeQuizThree!!.otherAspect.toString()
            quiz += list[i].stakeQuizThree!!.otherAspectExisting.toString()
            quiz += list[i].stakeQuizThree!!.otherAspectSuitable.toString()
            quiz += list[i].stakeQuizThree!!.satisfactionPoltekbangGraduate

            quiz += list[i].stakeQuizFour!!.poltekbangGraduateQuality
            quiz += list[i].stakeQuizFour!!.numberAviatorNeededExpectation.toString()
            quiz += list[i].stakeQuizFour!!.desiredCriteria
            quiz += list[i].stakeQuizFour!!.suggestion

            newList += quiz
        }
        return newList
    }

    private fun question(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Nama Perusahaan/Instansi")
        list.add("Alamat Perusahaan/Instansi")
        list.add("No. Telp Perusahaan/Instansi")
        list.add("Fax Perusahaan/Instansi")
        list.add("Email Perusahaan/Instansi")
        list.add("Jenis instansi/perusahaan")
        list.add("Berapa jumlah tenaga kerja di instansi/perusahaan ini? (termasuk di perwakilan/cabang)")
        list.add("Berapa presentasi pegawai dari alumni Lemdik penerbangan yang bekerja di instansi/perusahaan ini?")
        list.add("Berapa orang jumlah pegawai dari alumni Lemdik penerbangan lulusan Poltekbang Jayapura yang bekerja di instansi/perusahaan ini?")

        list.add("Cara penyebaran informasi untuk penerimaan tenaga kerja alumni Lemdik penerbangan di instansi ini")
        list.add("Bagaimana instansi/perusahaan Bapak/Ibu melakukan seleksi penerimaan tenaga baru?")
        list.add("Apakah instansi Bapak/Ibu melakukan rekrutmen tenaga kerja baru secara berkala?")
        list.add("Bagaimana intensitas rekrutmen tenaga kerja baru?")
        list.add("Seberapa penting Kesesuaian Bidang Studi bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Kesesuaian Bidang Studi bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Attitude/Tingkah Laku bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Attitude/Tingkah Laku bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Prestasi Akademik (Transkrip) bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Prestasi Akademik (Transkrip) bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Keterampilan Praktis yang diperoleh Semasa Kuliah bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Keterampilan Praktis yang diperoleh Semasa Kuliah bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Keterampilan Praktis yang diperoleh di luar Kuliah bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Keterampilan Praktis yang diperoleh di luar Kuliah bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Reputasi Almamater/Pendidikan Asal bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Reputasi Almamater/Pendidikan Asal bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Pengalaman Kerja bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Pengalaman Kerja bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Kemampuan Bahasa Asing bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Kemampuan Bahasa Asing bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Keterampilan Komputer bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Keterampilan Komputer bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Rekomendasi/Pengantar dari Pihak Ketiga bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Rekomendasi/Pengantar dari Pihak Ketiga bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Hasil tes penerimaan bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Hasil tes penerimaan bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Penampilan selama Wawancara bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Penampilan selama Wawancara bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Kepribadian bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Kepribadian bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Seberapa penting Provinsi/Daerah Asal bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Existing")
        list.add("Seberapa penting Provinsi/Daerah Asal bagi pegawai dari Lemdik penerbangan dalam penerimaan pegawai Kondisi Diinginkan")
        list.add("Aspek Lainnya")
        list.add("Lainnya Kondisi Existing")
        list.add("Lainnya Kondisi Diinginkan")

        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Pengetahuan Bidang Penerbangan sesuai dengan Prodi yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Pengetahuan Bidang Penerbangan sesuai dengan Prodi yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Keterampilan dalam Bekerja yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Keterampilan dalam Bekerja yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Etika Profesi yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Etika Profesi yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kebugaran yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kebugaran yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Moral yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Moral yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Jiwa Managerial (Sense of Managerial) yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Jiwa Managerial (Sense of Managerial) yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Jiwa Kepemimpinan (Sense of Leadership) yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Jiwa Kepemimpinan (Sense of Leadership) yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Keterampilan Komunikasi yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Keterampilan Komunikasi yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemampuan Berkomunikasi dalam Bahasa Asing yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemampuan Berkomunikasi dalam Bahasa Asing yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Penggunaan Teknologi Informasi yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Penggunaan Teknologi Informasi yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Pengembangan Diri yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Pengembangan Diri yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kreativitas yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kreativitas yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Inisiatif yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Inisiatif yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemampuan Bekerja dibawah Tekanan yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemampuan Bekerja dibawah Tekanan yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemandirian yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemandirian yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemampuan Memecahkan Persoalan yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Kemampuan Memecahkan Persoalan yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Visioner yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Visioner yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Loyalitas dan Komitmen yang bekerja di instansi/perusahaan Kondisi Existing")
        list.add("Kualitas lulusan Politeknik Penerbangan Jayapura dalam Loyalitas dan Komitmen yang bekerja di instansi/perusahaan Kondisi Diinginkan")
        list.add("Aspek Lainnya")
        list.add("Lainnya Kondisi Existing")
        list.add("Lainnya Kondisi Diinginkan")
        list.add("Secara keseluruhan, bagaimana tingkat kepuasan Bapak/Ibu terhadap lulusan Politeknik Penerbangan Jayapura?")

        list.add("Menurut pendapat Bapak/Ibu, bagaimanakah kualitas lulusan Politeknik Pelayaran Surabaya yang bekerja di instansi/perusahaan Bapak/Ibu?")
        list.add("Dalam 5-10 tahun mendatang, berapakah jumlah lulusan di bidang penerbangan yang diperlukan?")
        list.add("Kriteria lulusan penerbangan seperti apa yang diinginkan oleh instansi/perusahaan kini?")
        list.add("Saran dan hal-hal lain yang ingin disampaikan")
        return list
    }
}