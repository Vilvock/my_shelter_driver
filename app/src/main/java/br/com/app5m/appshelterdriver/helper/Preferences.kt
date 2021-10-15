package br.com.app5m.appshelterdriver.helper

import android.content.Context
import com.google.gson.Gson


class Preferences(context: Context?) {

    /**
     * Preferences
     *
     * @author Android version: Wesley Costa
     * @since Version 1.0.3
     * @Created  06/2020 - 02/2021
     */

    private var preferences = context?.getSharedPreferences("high.preference", 0)
    private var editor = preferences?.edit()

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

    fun setMoip(user: User?) {
        val data = Gson().toJson(user)
        editor!!.putString("moip", data)
        editor!!.commit()
    }

    fun getMoip(): User? {
        val user: User
        val gson = Gson()
        val data = preferences!!.getString("moip", "")
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
        editor!!.remove("getEnderecoCobranca")
        editor!!.remove("getData")
        editor!!.remove("login")
        editor!!.remove("moip")
        editor!!.remove("count")
        editor!!.remove("active")
        editor!!.remove("valorSaque")
        editor!!.remove("ad")
        editor!!.commit()
    }

    fun clearUserActiveAddress(){
        editor!!.remove("active")
        editor!!.commit()
    }

    fun firstUser(): String? {
        return preferences!!.getString("user", "0")
    }

    fun setActiveAddress(address: UAddress?) {
        val data = Gson().toJson(address)
        editor!!.putString("active", data)
        editor!!.commit()
    }

    fun getActiveAddress(): UAddress? {
        val user: UAddress
        val gson = Gson()
        val data = preferences!!.getString("active", "")
        return if (data!!.isNotEmpty()) {
            user = gson.fromJson(data, UAddress::class.java)
            user
        } else null
    }

    fun setEnderecoCobranca(enderecoCobranca: UAddress?) {
        val dados = Gson().toJson(enderecoCobranca)
        editor!!.putString("getEnderecoCobranca", dados)
        editor!!.commit()
    }

    fun getEnderecoCobranca(): UAddress? {
        val user: UAddress
        val gson = Gson()
        val data = preferences!!.getString("getEnderecoCobranca", "")
        return if (!data!!.isEmpty()) {
            user = gson.fromJson(data, UAddress::class.java)
            user
        } else null
    }

    fun setValorSaque(value: String){
        editor!!.putString("valorSaque", value)
        editor!!.commit()
    }

    fun getValorSaque(): String{
        return preferences?.getString("valorSaque", "")!!
    }

    fun setCartItemCount(value: Int){
        editor!!.putInt("count", value)
        editor!!.commit()
    }

    fun getCartItemCount(): Int{
        return preferences?.getInt("count", 0)!!
    }

}