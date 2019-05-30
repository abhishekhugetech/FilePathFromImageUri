package mtapp.demo.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.app.Activity
import android.content.Context
import android.util.Log
import android.provider.MediaStore
import android.widget.Toast
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    val IMAGE_PICKER_SELECT = 23232
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pickImage.setOnClickListener {
            val i = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, IMAGE_PICKER_SELECT)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) {
            try {
                val path = getPathFromCameraData(data as Intent, this )
                Log.i("PICTURE", "Path: " + path!!)
                if (path != null) {
                    filePath.text = path
                }
            }catch (e:Exception){
                Toast.makeText( this , e.message , Toast.LENGTH_SHORT ).show()
                e.printStackTrace()
            }
        }
    }

    fun getPathFromCameraData(data: Intent, context: Context): String {
        val selectedImage = data.data
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.getContentResolver().query(
            selectedImage,
            filePathColumn, null, null, null
        )
        cursor.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()
        return picturePath
    }
}
