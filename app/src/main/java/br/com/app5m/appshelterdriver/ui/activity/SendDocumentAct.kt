package br.com.app5m.appshelterdriver.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.DocumentControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Document
import br.com.app5m.appshelterdriver.util.DialogMessages
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import kotlinx.android.synthetic.main.activity_send_document.*
import kotlinx.android.synthetic.main.toolbar_yellow.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

class SendDocumentAct : AppCompatActivity(), WSResult {


    private lateinit var useful: Useful
    private lateinit var documentControl: DocumentControl

    private var finalFileCrlv: File? = null
    private var finalFileCnh: File? = null

    private var documentType: Int? = null

    private lateinit var docInfo1: Document
    private lateinit var docInfo2: Document

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    companion object {
        private const val CNH_TYPE = 0
        private const val CRLV_TYPE = 1
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_document)
        setSupportActionBar(toolbar)

        useful = Useful(this)

        useful.setActionBar(this, supportActionBar!!, "", 0)

        documentControl = DocumentControl(this, this, useful)
        documentControl.listDocDriver()

        addPhotoCnh_bt.setOnClickListener {
            openDialogGalleryCamera()
            documentType = CNH_TYPE
        }

        sendCnh_bt.setOnClickListener {

            if (finalFileCnh == null) return@setOnClickListener

            useful.openLoading()
            documentControl.updateDocument(finalFileCnh!!, docInfo1.id!!)
        }


        addPhotoCrlv_bt.setOnClickListener {
            openDialogGalleryCamera()
            documentType = CRLV_TYPE
        }

        sendDocumentCrlv_bt.setOnClickListener {

            if (finalFileCrlv == null) return@setOnClickListener

            useful.openLoading()
            documentControl.updateDocument(finalFileCrlv!!, docInfo2.id!!)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {

                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun dResponse(list: List<Document>, type: String) {

        useful.closeLoading()

        docInfo1 = list[0]

        if (type == "listDoc") {

            docInfo2 = list[1]
            if (docInfo1.rows != "0") {

                //pegar url e glid
                statusCnh_tv.text = docInfo1.status
                msg_tv2.visibility = View.GONE
            }

            if (docInfo2.rows != "0") {

                //pegar url e glid
                statusCrlv_tv.text = docInfo2.status
                msg_tv2.visibility = View.GONE
            }

        } else {

            SingleToast.INSTANCE.show(this, docInfo1.msg!!, Toast.LENGTH_LONG)

            if (docInfo1.status == "01") {
                documentControl.listDocDriver()
//                finish()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {

            try {
                when (requestCode) {
                    0 -> {
                        val photo: Bitmap = data?.extras?.get("data") as Bitmap

                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        val tempUri = getImageUri(this, photo)

                        // CALL THIS METHOD TO GET THE ACTUAL PATH

                        if (documentType == CRLV_TYPE) {

                            finalFileCrlv = File(getRealPathFromURI(tempUri))
                            msg_tv2.visibility = View.GONE
                            documentCrlv_iv.setImageURI(tempUri)
                        } else {

                            finalFileCnh = File(getRealPathFromURI(tempUri))
                            msg_tv.visibility = View.GONE
                            documentCnh_iv.setImageURI(tempUri)
                        }

                    }
                    1 -> {

                        val selectedImage: Uri? = data?.data

                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                        val bytes = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                        val imgPath = getRealPathFromURI(selectedImage)

                        if (documentType == CRLV_TYPE) {

                            finalFileCrlv = File(imgPath.toString())
                            msg_tv2.visibility = View.GONE
                            documentCrlv_iv.setImageURI(selectedImage)
                        } else {

                            finalFileCnh = File(imgPath.toString())
                            msg_tv.visibility = View.GONE
                            documentCnh_iv.setImageURI(selectedImage)
                        }

                    }
                }


            }catch (e: Exception) {

                SingleToast.INSTANCE.show(this, "Algo deu errado, tente novamente mais tarde!",
                    Toast.LENGTH_LONG)
            }


        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission!!)) {
                //denied

                Toast.makeText(this,
                    "Por favor, aceite todas as permissões necessárias!", Toast.LENGTH_SHORT).show()
                return
            } else {
                if (ActivityCompat.checkSelfPermission(
                        this, permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    //never ask again
                    Toast.makeText(
                        this,
                        "Por favor, aceite todas as permissões necessárias!", Toast.LENGTH_SHORT
                    ).show()
                    startInstalledAppDetailsActivity(this)
                    return
                }
            }
        }
        openDialogGalleryCamera()
    }

    private fun startInstalledAppDetailsActivity(context: Activity?) {
        if (context == null) {
            return
        }

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", this.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    private fun openDialogGalleryCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            this.requestPermissions(permissions, 1)

        } else {

            DialogMessages(this).multimedia(object : DialogMessages.AnswerString {
                override fun setOnClickListener(choose: String) {

                    if (choose == "camera") {

                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, 0)
                    } else {

                        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, 1)


                    }

                }

            })

        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver,
            inImage, "image", null)
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (this.contentResolver != null) {
            val cursor: Cursor? = this.contentResolver.query(uri!!,
                null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

}