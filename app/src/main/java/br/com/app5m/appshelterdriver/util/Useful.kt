package br.com.app5m.appshelterdriver.util

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.dialog.DefaultBottomSheetContainerFragDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class Useful (private val context: Context) {

    /**
     * Useful
     *
     * @Requered Androidx, daimajia
     */

    fun startFragment(frag: Fragment, fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.containerView, frag)/*.addToBackStack(null)*/.commit()
    }

    fun startFragmentOnBack(frag: Fragment, fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.containerView, frag).addToBackStack(null).commit()
    }

    fun openLoading(context: Context, alertDialog: AlertDialog) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.loading, null)
        alertDialog.setView(view)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun closeLoading(alertDialog: AlertDialog) {
        if (alertDialog.isShowing) alertDialog.dismiss()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setActionBar(activity: Activity, bar: androidx.appcompat.app.ActionBar, title: String, type: Int){

        val view = activity.layoutInflater.inflate(R.layout.toolbar, null)
        val params = androidx.appcompat.app.ActionBar.LayoutParams(
            androidx.appcompat.app.ActionBar.LayoutParams.WRAP_CONTENT,
            androidx.appcompat.app.ActionBar.LayoutParams.WRAP_CONTENT, Gravity.START)

//        val textTitle = view.findViewById<TextView>(R.id.textTitle)
//        val avatarIv = view.findViewById<ImageView>(R.id.avatar_iv)
//        textTitle.text = title
        bar.setCustomView(view, params)

        when (type) {
            0 -> {
                bar.setDisplayHomeAsUpEnabled(true)
                bar.setHomeAsUpIndicator(activity.resources.getDrawable(R.drawable.ic_arrow_back, null))
            }
            2 -> {
//                avatarIv.visibility = View.VISIBLE
                bar.setDisplayHomeAsUpEnabled(true)
//                bar.setHomeAsUpIndicator(activity.resources.getDrawable(R.drawable.ic_arrow_left_green, null))
            }
            else -> {
                bar.setDisplayHomeAsUpEnabled(false)
            }
        }

        bar.setDisplayShowTitleEnabled(false)
        bar.setDisplayShowCustomEnabled(true)

    }

    @SuppressLint("SetTextI18n")
    fun showDefaultDialogView(fragmentManager: FragmentManager, fragment: Fragment) {
        val bottomSheetDialog = DefaultBottomSheetContainerFragDialog(fragment)
        bottomSheetDialog.show(fragmentManager, "dialog")
    }


    //CRIAR CLASSE ANIMATIONS

//    fun shake(view: View) {
//        YoYo.with(Techniques.Shake).duration(400).repeat(1).playOn(view);
//        view.requestFocus();
//    }
//
//    fun pulse(view: View) {
//        YoYo.with(Techniques.Pulse).duration(450).playOn(view);
//    }

}