package br.senai.sp.jandira.doetempo.services
import android.net.Uri
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class UriTypeAdapter : TypeAdapter<Uri>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Uri?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value.toString())
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Uri? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        val uriString = `in`.nextString()
        return Uri.parse(uriString)
    }
}