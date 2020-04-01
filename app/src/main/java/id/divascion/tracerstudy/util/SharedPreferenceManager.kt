package id.divascion.tracerstudy.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log.e
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.divascion.tracerstudy.data.model.*

class SharedPreferenceManager {

    @SuppressLint("CommitPrefEdits")
    fun saveAlumni(context: Context, item: AlumniQuizOne, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("ONE", jsonSave)
                apply()
            }
            saveListRole(context, "alumni", 1, uid)
        } catch (e: Exception) {
            e("Alumni Part 1 failed", e.message)
            false
        }
    }

    fun getAlumniOne(context: Context, uid: String): AlumniQuizOne? {
        val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<AlumniQuizOne>() {}.type
        val json = sharedPreferences.getString("ONE", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveAlumni(context: Context, item: AlumniQuizTwo, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("TWO", jsonSave)
                apply()
            }
            saveListRole(context, "alumni", 2, uid)
        } catch (e: Exception) {
            e("Alumni Part 2 failed", e.message)
            false
        }
    }

    fun getAlumniTwo(context: Context, uid: String): AlumniQuizTwo? {
        val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<AlumniQuizTwo>() {}.type
        val json = sharedPreferences.getString("TWO", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveAlumni(context: Context, item: AlumniQuizThree, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("THREE", jsonSave)
                apply()
            }
            saveListRole(context, "alumni", 3, uid)
        } catch (e: Exception) {
            e("Alumni Part 3 failed", e.message)
            false
        }
    }

    fun getAlumniThree(context: Context, uid: String): AlumniQuizThree? {
        val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<AlumniQuizThree>() {}.type
        val json = sharedPreferences.getString("THREE", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveAlumni(context: Context, item: AlumniQuizFour, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("FOUR", jsonSave)
                apply()
            }
            saveListRole(context, "alumni", 4, uid)
        } catch (e: Exception) {
            e("Alumni Part 4 failed", e.message)
            false
        }
    }

    fun getAlumniFour(context: Context, uid: String): AlumniQuizFour? {
        val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<AlumniQuizFour>() {}.type
        val json = sharedPreferences.getString("FOUR", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveAlumni(context: Context, item: AlumniQuizFive, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("FIVE", jsonSave)
                apply()
            }
            saveListRole(context, "alumni", 5, uid)
        } catch (e: Exception) {
            e("Alumni Part 5 failed", e.message)
            false
        }
    }

    fun getAlumniFive(context: Context, uid: String): AlumniQuizFive? {
        val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<AlumniQuizFive>() {}.type
        val json = sharedPreferences.getString("FIVE", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveAlumni(context: Context, item: AlumniQuizSix, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("SIX", jsonSave)
                apply()
            }
            saveListRole(context, "alumni", 6, uid)
        } catch (e: Exception) {
            e("Alumni Part 6 failed", e.message)
            false
        }
    }

    fun getAlumniSix(context: Context, uid: String): AlumniQuizSix? {
        val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<AlumniQuizSix>() {}.type
        val json = sharedPreferences.getString("SIX", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveStake(context: Context, item: StakeQuizOne, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("ONE", jsonSave)
                apply()
            }
            saveListRole(context, "stakeholder", 1, uid)
        } catch (e: Exception) {
            e("Stake Part 1 failed", e.message)
            false
        }
    }

    fun getStakeOne(context: Context, uid: String): StakeQuizOne? {
        val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<StakeQuizOne>() {}.type
        val json = sharedPreferences.getString("ONE", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveStake(context: Context, item: StakeQuizTwo, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("TWO", jsonSave)
                apply()
            }
            saveListRole(context, "stakeholder", 2, uid)
        } catch (e: Exception) {
            e("Stake Part 2 failed", e.message)
            false
        }
    }

    fun getStakeTwo(context: Context, uid: String): StakeQuizTwo? {
        val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<StakeQuizTwo>() {}.type
        val json = sharedPreferences.getString("TWO", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveStake(context: Context, item: StakeQuizThree, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("THREE", jsonSave)
                apply()
            }
            saveListRole(context, "stakeholder", 3, uid)
        } catch (e: Exception) {
            e("Stake Part 3 failed", e.message)
            false
        }
    }

    fun getStakeThree(context: Context, uid: String): StakeQuizThree? {
        val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<StakeQuizThree>() {}.type
        val json = sharedPreferences.getString("THREE", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveStake(context: Context, item: StakeQuizFour, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply {
                putString("FOUR", jsonSave)
                apply()
            }
            saveListRole(context, "stakeholder", 4, uid)
        } catch (e: Exception) {
            e("Stake Part 4 failed", e.message)
            false
        }
    }

    fun getStakeFour(context: Context, uid: String): StakeQuizFour? {
        val sharedPreferences = context.getSharedPreferences("STAKEHOLDER_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<StakeQuizFour>() {}.type
        val json = sharedPreferences.getString("FOUR", "")
        return gson.fromJson(json, type)
    }

    @SuppressLint("DefaultLocale", "CommitPrefEdits")
    fun saveListRole(context: Context, role: String, number: Int, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("QUIZ_LIST_$uid", 0)
            val gson = Gson()
            val type = object : TypeToken<List<String>>() {}.type
            val data = sharedPreferences.getString(role.toUpperCase(), "")
            val json: String
            var itemList: ArrayList<String>
            var isAlready = false
            try {
                itemList = gson.fromJson(data, type)
                isAlready = true
            } catch (e: Exception) {
                val items: ArrayList<String> = ArrayList()
                json = gson.toJson(items, type)
                itemList = gson.fromJson(json, type)
            }
            val list = if (role.equals("alumni", true)) {
                5
            } else {
                3
            }
            if (isAlready) {
                itemList[number-1] = "done"
            } else {
                for (i in 0..list) {
                    if (i == number-1) {
                        itemList.add("done")
                    } else {
                        itemList.add("none")
                    }
                }
            }
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(itemList)
            prefsEditor.apply {
                putString(role.toUpperCase(), jsonSave)
                apply()
            }
            true
        } catch (e: Exception) {
            e("$role list saved failed", e.message)
            false
        }
    }

    @SuppressLint("DefaultLocale")
    fun getListRole(context: Context, role: String, uid: String): ArrayList<String>? {
        val sharedPreferences = context.getSharedPreferences("QUIZ_LIST_$uid", 0)
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        val json = sharedPreferences.getString(role.toUpperCase(), "")
        return gson.fromJson(json, type)
    }

}