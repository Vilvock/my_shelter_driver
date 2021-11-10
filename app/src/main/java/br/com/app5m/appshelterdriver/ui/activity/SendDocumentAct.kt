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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.DocumentControl
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Document
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.util.DialogMessages
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

class SendDocumentAct : AppCompatActivity(), WSResult {


    private lateinit var useful: Useful
    private lateinit var documentControl: DocumentControl

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_document)

        useful = Useful(this)
        documentControl = DocumentControl(this, this, useful)


    }

    override fun dResponse(list: List<Document>, type: String) {

        val docInfo = list[0]

        SingleToast.INSTANCE.show(this, docInfo.msg!!, Toast.LENGTH_LONG)

        if (docInfo.status == "01") {
            finish()
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
                        val finalFile = File(getRealPathFromURI(tempUri))

                        useful.openLoading()
                        documentControl.updateDocument(finalFile)

                    }
                    1 -> {

                        val selectedImage: Uri? = data?.data

                        val bitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                        val bytes = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                        val imgPath = getRealPathFromURI(selectedImage)
                        val finalFile = File(imgPath.toString())

                        useful.openLoading()
                        documentControl.updateDocument(finalFile)

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