package br.com.app5m.appshelterdriver.util

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import android.app.Activity
import br.com.app5m.appshelterdriver.R


class DialogMessages (private val context: Context) {

    fun click(title: String?, msg: String?, answer: Answer) {

        val builder = AlertDialog.Builder(context)
        val alertDialog = builder.create()
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog, null)

        alertDialog.setView(view)
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.show()

        val confirm = view.findViewById<TextView>(R.id.confirm_tv)
        val cancel = view.findViewById<TextView>(R.id.cancel_tv)
        val titleTxt = view.findViewById<TextView>(R.id.title_tv)
        val msgTxt = view.findViewById<TextView>(R.id.msg_tv)

        titleTxt.text = title
        msgTxt.text = msg

        confirm.setOnClickListener {
            answer.setOnClickListener()
            alertDialog.dismiss()
        }
        cancel.setOnClickListener { alertDialog.dismiss() }
    }

    fun booleanClick(title: String, msg: String, answerBoolean: AnswerBoolean) {

        val builder = AlertDialog.Builder(context)
        val alertDialog = builder.create()
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog, null)

        alertDialog.setView(view)
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.show()

        val confirm = view.findViewById<TextView>(R.id.confirm_tv)
        val cancel = view.findViewById<TextView>(R.id.cancel_tv)
        val titleTxt = view.findViewById<TextView>(R.id.title_tv)
        val msgTxt = view.findViewById<TextView>(R.id.msg_tv)

        titleTxt.text = title
        msgTxt.text = msg

        confirm.setOnClickListener {
            answerBoolean.setOnClickListener(boolean = true)
            alertDialog.dismiss()
        }
        cancel.setOnClickListener {
            answerBoolean.setOnClickListener(boolean = false)
            alertDialog.dismiss()
        }
    }

    fun loadMsg(title: String?, msg: String?, answer: Answer) {

        val builder = AlertDialog.Builder(context)
        val alertDialog = builder.create()
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog, null)

        alertDialog.setView(view)
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)
        alertDialog.show()

        val confirm = view.findViewById<TextView>(R.id.confirm_tv)
        val cancel = view.findViewById<TextView>(R.id.cancel_tv)
        val titleTxt = view.findViewById<TextView>(R.id.title_tv)
        val msgTxt = view.findViewById<TextView>(R.id.msg_tv)

        titleTxt.text = title
        msgTxt.text = msg

        confirm.setOnClickListener {
            answer.setOnClickListener()
            alertDialog.dismiss()
        }

        cancel.visibility = View.GONE
    }


    fun multimedia(answerString: AnswerString) {
        val builder = AlertDialog.Builder(context)
        val alertDialog = builder.create()
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.dialog_gallery_camera, null)

        alertDialog.setView(view)
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.setCancelable(true)
        alertDialog.show()

        val cameraIb = view.findViewById<ImageButton>(R.id.camera_ib)
        val galleryIb = view.findViewById<ImageButton>(R.id.gallery_ib)
        val cancelTv = view.findViewById<TextView>(R.id.cancel_tv)


        cameraIb.setOnClickListener {
            answerString.setOnClickListener("camera")
            alertDialog.dismiss()
        }

        galleryIb.setOnClickListener {
            answerString.setOnClickListener("gallery")
            alertDialog.dismiss()
        }

        cancelTv.setOnClickListener {

            alertDialog.dismiss()
        }
    }

    fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Seu GPS parece estar desativado, é necessário ativá-lo para prosseguir.")
            .setCancelable(false)
            .setPositiveButton(
                "Confirmar"
            ) { dialog, id ->
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(
                "Cancelar"
            ) { dialog, id ->
                (context as Activity).finishAffinity()
            }

        val alert = builder.create()
        alert.show()
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
            ContextCompat.getColor(context,
                R.color.colorAccent))
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
            ContextCompat.getColor(context,
                R.color.gray400))
    }

    interface Answer {
        fun setOnClickListener()
    }

    interface AnswerBoolean {
        fun setOnClickListener(boolean: Boolean)
    }


    interface AnswerString {
        fun setOnClickListener(choose: String)
    }

}