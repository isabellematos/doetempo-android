package br.senai.sp.jandira.doetempo

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.doetempo.ui.theme.DoetempoTheme
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

abstract class StorageActivity : ComponentActivity() {

    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage = Firebase.storage

        includesForCreateReference()
    }
}

private fun includesForCreateReference() {

    //val storage = Firebase.storage

    val storage = FirebaseStorage.getInstance()

// Create a storage reference from our app
    val storageRef = storage.reference.child("documents/document/").child("image%")



// Create a reference with an initial file path and name
    val pathReference = storageRef.child("images/stars.jpg")

// Create a reference to a file from a Google Cloud Storage URI
    val gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg")

// Create a reference from an HTTPS URL
// Note that in the URL, characters are URL escaped!
    val httpsReference = storage.getReferenceFromUrl(
        "https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg")


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    DoetempoTheme {

    }
}