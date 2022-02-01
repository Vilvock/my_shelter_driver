package br.com.app5m.appshelterdriver.ui.fragment.drawer.user

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.util.DialogMessages
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.email_et
import kotlinx.android.synthetic.main.fragment_user_profile.name_et
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class UserProfileFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var preferences: Preferences
    private lateinit var userControl: UserControl
    private lateinit var validation: Validation

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    companion object {
        private var DOCUMENT_TYPE = 0
        private const val CNPJ_TYPE = 0
        private const val CPF_TYPE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        preferences = Preferences(requireContext())
        validation = Validation(requireContext())
        userControl = UserControl(requireContext(), this, useful)

        useful.openLoading()
        userControl.listId()

        loadClicks()

    }

    override fun uResponse(list: List<User>, type: String) {

        val user = list[0]

        if (type != "listId") {

            if (user.status.equals("01")) {

                SingleToast.INSTANCE.show(context, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT)
                userControl.listId()
            }

            //list id
        }  else {
            //falta avatar
            Glide.with(requireContext()).load(WSConstants.AVATAR_USER + user.avatar).into(avatar_iv)

            name_et.setText(user.name)
            email_et.setText(user.email)
            cellphone_et.setText(user.cellphone)
            birth_et.setText(user.birth)
            document_et.setText(user.document)

            DOCUMENT_TYPE = if (user.document!!.length > 11) {
                CNPJ_TYPE
            } else {
                CPF_TYPE
            }

            preferences.setUserData(user)

        }
        useful.closeLoading()


    }

    private fun loadClicks() {

        save_bt.setOnClickListener {

            if (!validate()) return@setOnClickListener

            val user = User()

            user.name = name_et.text.toString()
            user.email = email_et.text.toString()
            user.cellphone = cellphone_et.text.toString()
            user.cpf = document_et.text.toString()
            user.birth = birth_et.text.toString()

            useful.openLoading()
            userControl.updateUserData(user)

        }

        changePassword_tv.setOnClickListener {

            useful.startFragmentOnBack(UpdatePasswordFrag(), requireActivity().supportFragmentManager)

        }

        updateAvatar_fab.setOnClickListener {
            openDialogGalleryCamera()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {

            try {
                when (requestCode) {
                    0 -> {
                        val photo: Bitmap = data?.extras?.get("data") as Bitmap

                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        val tempUri = getImageUri(requireContext(), photo)

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        val finalFile = File(getRealPathFromURI(tempUri))

                        val id: String = preferences.getUserData()?.id.toString()

                        useful.openLoading()
                        userControl.updateAvatar(id, finalFile)

                    }
                    1 -> {

                        val selectedImage: Uri? = data?.data

                        val bitmap =
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                        val bytes = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                        val imgPath = getRealPathFromURI(selectedImage)
                        val finalFile = File(imgPath.toString())

                        val id: String = preferences.getUserData()?.id.toString()

                        useful.openLoading()
                        userControl.updateAvatar(id, finalFile)

                    }
                }


            }catch (e: Exception) {

                SingleToast.INSTANCE.show(context, "Algo deu errado, tente novamente mais tarde!",
                    Toast.LENGTH_LONG)
            }


        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray){
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission!!)) {
                //denied

                Toast.makeText(requireContext(),
                    "Por favor, aceite todas as permissões necessárias!", Toast.LENGTH_SHORT).show()
                return
            } else {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(), permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    //never ask again
                    Toast.makeText(
                        requireContext(),
                        "Por favor, aceite todas as permissões necessárias!", Toast.LENGTH_SHORT
                    ).show()
                    startInstalledAppDetailsActivity(requireActivity())
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
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    private fun openDialogGalleryCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            this.requestPermissions(permissions, 1)

        } else {

            DialogMessages(requireContext()).multimedia(object : DialogMessages.AnswerString {
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
        if (requireActivity().contentResolver != null) {
            val cursor: Cursor? = requireActivity().contentResolver.query(uri!!,
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

    private fun validate(): Boolean {
        if (!validation.name(name_et)) return false
        if (!validation.email(email_et)) return false
        if (!validation.cellphone(cellphone_et)) return false
        if (!validation.date(birth_et)) return false

        if (DOCUMENT_TYPE == CNPJ_TYPE) {
            if (!validation.cnpj(document_et)) return false
        } else {
            if (!validation.cpf(document_et)) return false
        }

        return true
    }
}