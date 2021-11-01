package br.com.app5m.appshelterdriver.util.visual

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo


class Animation (private val context: Context) {

    /**
     * Useful
     *
     * @Requered Androidx, daimajia
     */


    fun shake(view: View) {
        YoYo.with(Techniques.Shake).duration(400).repeat(1).playOn(view);
        view.requestFocus();
    }

    fun pulse(view: View) {
        YoYo.with(Techniques.Pulse).duration(450).playOn(view);
    }

}