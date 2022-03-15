package br.com.app5m.appshelterdriver.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.dialog.others.DefaultBottomSheetContainerFragDialog
import br.com.app5m.appshelterdriver.ui.dialog.ride_flow.RideFlowContainerBottomFrag
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class Useful (private val context: Context) {

    /**
     * Useful
     *
     * @Requered Androidx, daimajia
     */

    private var bottomSheetDialog: DefaultBottomSheetContainerFragDialog? = null

    private val builder = AlertDialog.Builder(context)
    val alertDialog = builder.create()

    fun startFragment(frag: Fragment, fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.containerView, frag).commitAllowingStateLoss()
    }

    fun startFragmentOnBack(frag: Fragment, fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        transaction.replace(R.id.containerView, frag).addToBackStack(null).commitAllowingStateLoss()
    }

    fun openLoading() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.loading, null)
        alertDialog.setView(view)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun closeLoading() {
        if (alertDialog.isShowing) alertDialog.dismiss()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setActionBar(activity: Activity, bar: ActionBar, title: String, type: Int) {

        val view = activity.layoutInflater.inflate(R.layout.toolbar_custom, null)
        val params = ActionBar.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT, Gravity.START
        )

        bar.setCustomView(view, params)
        bar.title = title

        when (type) {
            0 -> {
                bar.setDisplayShowTitleEnabled(true)
                bar.setDisplayHomeAsUpEnabled(true)
            }
            2 -> {
                bar.setDisplayShowTitleEnabled(false)
            }
        }

        bar.setDisplayShowCustomEnabled(true)
        bar.setHomeAsUpIndicator(activity.resources.getDrawable(R.drawable.ic_arrow_back, null))

    }

    @SuppressLint("SetTextI18n")
    fun showRideFlowFrag(fragmentManager: FragmentManager, tag: String) {
//        dismissRideFlowFrag(fragmentManager)

        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_bottom, // quando inicia
            R.anim.exit_to_left, // quando entra a proxima
            R.anim.enter_from_left, // quando volta do pop
            R.anim.exit_from_top// quando fecha (n chama)
        )
        transaction.replace(R.id.containerView, RideFlowContainerBottomFrag(), tag).commitAllowingStateLoss()
    }

    @SuppressLint("SetTextI18n")
    fun dismissRideFlowFrag(fragmentManager: FragmentManager) {
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment.tag != "maps") {

                    val transaction = fragmentManager.beginTransaction()
                    transaction.setCustomAnimations(
                        R.anim.enter_from_bottom,
                        R.anim.exit_from_top
                    )
                    transaction.remove(fragment).commitAllowingStateLoss()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun showDefaultDialogView(fragmentManager: FragmentManager, tag: String) {
        bottomSheetDialog = DefaultBottomSheetContainerFragDialog()
        bottomSheetDialog?.show(fragmentManager, tag)
    }

    @SuppressLint("SetTextI18n")
    fun dismissDefaultDialogView() {
        bottomSheetDialog?.dismiss()
    }


    fun bitmapDescriptorFromVector(
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun openWebPage(url: String?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    fun createLink(
        targetTextView: TextView, completeString: String,
        partToClick: String, clickableAction: ClickableSpan?
    ): TextView {
        val spannableString = SpannableString(completeString)

        // make sure the String is exist, if it doesn't exist
        // it will throw IndexOutOfBoundException
        val startPosition = completeString.indexOf(partToClick)
        val endPosition = completeString.lastIndexOf(partToClick) + partToClick.length
        spannableString.setSpan(
            clickableAction, startPosition, endPosition,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        targetTextView.text = spannableString
        targetTextView.movementMethod = LinkMovementMethod.getInstance()
        return targetTextView
    }

}