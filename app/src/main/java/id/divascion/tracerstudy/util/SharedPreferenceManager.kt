package id.divascion.tracerstudy.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.annotation.SuppressLint
import android.util.Log.e
import id.divascion.tracerstudy.data.model.AlumniQuizOne

class SharedPreferenceManager {

    @SuppressLint("CommitPrefEdits")
    fun saveAlumniOne(context: Context, item: AlumniQuizOne, uid: String): Boolean {
        return try {
            val sharedPreferences = context.getSharedPreferences("ALUMNI_$uid", 0)
            val gson = Gson()
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(item)
            prefsEditor.apply{
                putString("ONE", jsonSave)
                apply()
            }
            saveListRole(context, "alumni", 1, uid)
        } catch(e: Exception) {
            e("Alumni Part One failed", e.message)
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
            } catch(e: Exception) {
                val items : ArrayList<String> = ArrayList()
                json = gson.toJson(items, type)
                itemList = gson.fromJson(json, type)
            }
            val list = if(role.equals("alumni", true)) {
                6
            } else {
                4
            }
            for(i in 1..list) {
                if(isAlready) {
                    if(i==number) {
                        itemList.add("done")
                    }
                } else {
                    if(i==number) {
                        itemList.add("done")
                    } else {
                        itemList.add("none")
                    }
                }
            }
            val prefsEditor = sharedPreferences.edit()
            val jsonSave = gson.toJson(itemList)
            prefsEditor.apply{
                putString(role.toUpperCase(), jsonSave)
                apply()
            }
            true
        } catch(e: Exception) {
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