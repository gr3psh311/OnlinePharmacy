package com.example.tdmproject


import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.tdmproject.entity.Command
import com.example.tdmproject.retrofit.RetrofitService
import kotlinx.android.synthetic.main.fragment_command.*
import kotlinx.android.synthetic.main.fragment_command.view.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class CommandFragment : Fragment() {

//    private val mMediaUri: Uri? = null
    private var filename: String? =null
    private var fileUri: Uri? = null

    private var mediaPath: String? = null

    private var mImageFileLocation = ""
    private var postPath: String? = null





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_command, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (shouldAskPermissions()) {
            askPermissions()
        }


        view.upload.setOnClickListener {
            val alertDialog= AlertDialog.Builder(activity!!)
            alertDialog.setTitle("Choisir une photo")
            val items= arrayOf("A partir de la galerie", "Prendre une photo")
            alertDialog.setItems(items){
                    dialog, which ->
                when (which){
                    0->pickFromGallery()
                    1->captureImage()
                }
            }
            alertDialog.show()

        }

        pickImage.setOnClickListener {
            uploadFile()

            if (postPath != null && postPath != "") {
                TimeUnit.SECONDS.sleep(2L)

                view.findNavController().popBackStack(R.id.main_fragment, false)
//                view.findNavController().navigate(R.id.action_main_fragment_to_browseCommandsFragment)
            }



        }

    }


    private fun uploadFile(){
        if (postPath == null || postPath == "") {
            Toast.makeText(context,"Choose a picture!", Toast.LENGTH_SHORT).show()
            return
        }
        else {

            // Map is used to multipart the file using okhttp3.RequestBody
            val map = HashMap<String, RequestBody>()
            val file = File(postPath!!)

            Toast.makeText(context,postPath!!, Toast.LENGTH_SHORT).show()

            // Parsing
            val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
            val name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            map.put("file\"; filename=\"" + file.name + "\"", requestBody)
            val multipartBody= MultipartBody.Part.createFormData("photo",file.name,requestBody)
            Toast.makeText(context,file.name, Toast.LENGTH_SHORT).show()
            Log.d("youcef","pic:  "+file.name)

            val call = RetrofitService.endpoint.uploadPicture(multipartBody)
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            val serverResponse = response.body()
                            filename = serverResponse!!

                            val pref = activity!!.getSharedPreferences("fileName", Context.MODE_PRIVATE)
                            val nss= pref.getInt("nss",1)
                            Log.d("youcef","nss found: "+nss)

                            val sdf = SimpleDateFormat("dd/M/yyyy")
                            val date = sdf.format(Date()).toString()
                            //val pharmacy_address= arguments!!.getString("pharmacy_address")
                            val pharmacy_address = "pharmacy_address"
                            val etat= "waiting"
                            val cmd_id=0
                            val path= "/uploads/images/"+filename
                            val tmpCommand = Command(cmd_id,path,nss,pharmacy_address,etat,date)
                            val call = RetrofitService.endpoint.addCommand(tmpCommand)
                            call.enqueue(object : Callback<String> {
                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Toast.makeText(context,t.toString(), Toast.LENGTH_SHORT).show()
                                    Log.d("youcef","cmdFail: "+t.toString())
                                }

                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    if (response?.isSuccessful!!) {
                                        val resp = response.body()!!.toString()
                                        Log.d("youcef","success: "+resp)
//                                        val cmd= Command(cmd_id,path,nss,pharmacy_address,etat,date)
//                                        val cmdctrl= CmdsController()
//                                        cmdctrl.addCommande(activity!! as Activity,cmd)
                                        //view!!.findNavController().navigate(R.id.action_addCommandeFragment_to_commandesFragment)
                                    } else {
                                        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                                        Log.d("youcef","notSuccess "+ response.body())
                                    }

                                }
                            })

                        }
                    } else {
                        Toast.makeText(context,"error"+response.body(), Toast.LENGTH_SHORT).show()
                        Log.d("youcef","erroor "+response.body())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(context,"response is: "+ t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }




    private fun captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            val callCameraApplicationIntent = Intent()
            callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE

            // We give some instruction to the intent to save the image
            var photoFile: File? = null

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile()
                Log.d("youcef","photo: "+photoFile)

                // Here we call the function that will try to catch the exception made by the throw function
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            val outputUri = FileProvider.getUriForFile(
                activity!!,
                BuildConfig.APPLICATION_ID + ".provider",
                photoFile!!)
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)


            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST)
        }


    }
    fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }



    @Throws(IOException::class)
    internal fun createImageFile(): File {

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMAGE_" + timeStamp
        // Here we specify the environment location and the exact path where we want to save the so-created file
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app")

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir()

        // Here we create the file using a prefix, a suffix and a directory
        val image = File(storageDirectory, imageFileName + ".jpg")
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation

        mImageFileLocation = image.absolutePath
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image
    }

    private fun pickFromGallery(){
        val intent : Intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, REQUEST_PICK_PHOTO)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==Activity.RESULT_OK){
            if ((requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO)){
                val selectedImage = data!!.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val act=activity
                val cursor = act!!.contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
              //  assert(cursor != null)
                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                mediaPath = cursor.getString(columnIndex)
                // Set the Image in ImageView for Previewing the Media
                preview.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                cursor.close()


                postPath = mediaPath
                // photo.setImageBitmap(bitmap)

            }
            else if (requestCode == CAMERA_PIC_REQUEST) {
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(preview)
                    postPath = mImageFileLocation

                } else {
                    Glide.with(this).load(fileUri).into(preview)
                    postPath = fileUri!!.path

                }

            }

        }
    }

    protected fun  shouldAskPermissions(): Boolean {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
    }
    @TargetApi(23)
    protected fun askPermissions() {
        val permissions = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.CAMERA"
        )
        val requestCode = 200
        requestPermissions(permissions, requestCode)
    }
    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111

        val TAG = MainActivity::class.java.simpleName
//        val TAG = MainActivity::class.java.getSimpleName()

        val CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100

        val MEDIA_TYPE_IMAGE = 1
        val IMAGE_DIRECTORY_NAME = "Android File Upload"

        /**
         * returning image / video
         */
        fun getOutputMediaFile(type: Int): File? {

            // External sdcard location
            val mediaStorageDir = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME)

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {

                    return null
                }
            }

            // Create a media file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())
            val mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator
                        + "IMG_" + ".jpg")
            } else {
                return null
            }

            return mediaFile
        }
    }






}
