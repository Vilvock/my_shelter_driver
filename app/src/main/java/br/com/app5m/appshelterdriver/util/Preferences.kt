package br.com.app5m.appshelterdriver.util

import android.content.Context
import br.com.app5m.appshelterdriver.models.LocationUser
import br.com.app5m.appshelterdriver.models.User
import com.google.gson.Gson

class Preferences(context: Context?) {

    private var preferences = context?.getSharedPreferences("high.preference", 0)
    private var editor = preferences?.edit()

    fun saveInstanceTokenFcm(key: String?, value: String) {
        editor?.putString(key, value)
        editor!!.commit()
    }

    fun getInstanceTokenFcm(): String {
        return preferences!!.getString("token", "")!!
    }

    fun storeInt(key: String?, value: Int) {
        editor?.putInt(key, value)
        editor!!.commit()
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return preferences!!.getInt(key, defaultValue)
    }


    fun setUserData(user: User?) {
        val data = Gson().toJson(user)
        editor!!.putString("getData", data)
        editor!!.commit()
    }

    fun getUserData(): User? {
        val user: User
        val gson = Gson()
        val data = preferences!!.getString("getData", "")
        return if (data != null && data.isNotEmpty()) {
            user = gson.fromJson(data, User::class.java)
            user
        } else null
    }

    fun setLogin(enable: Boolean){
        editor!!.putBoolean("login", enable)
        editor!!.commit()
    }

    fun getLogin(): Boolean{
        return preferences?.getBoolean("login", false)!!
    }

    fun clearUserData(){
        editor?.remove("getData")
        editor?.remove("login")
        editor?.remove("token")
        editor!!.commit()
    }

    fun clearUserLocation(){
        editor?.remove("location")
        editor!!.commit()
    }

    fun setUserLocation(location: LocationUser?) {
        val dados = Gson().toJson(location)
        editor!!.putString("location", dados)
        editor!!.commit()
    }

    fun getUserLocation(): LocationUser? {
        val location: LocationUser
        val gson = Gson()
        val data = preferences!!.getString("location", "")
        return if (data!!.isNotEmpty()) {
            location = gson.fromJson(data, LocationUser::class.java)
            location
        } else null
    }

    companion object {
        /**
         * Preferences
         */

        const val ENTERING_FIRST_TIME = "EnteringFirstTime"
    }
}