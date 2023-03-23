package br.senai.sp.jandira.doetempo.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.POST

//data class Login(
//    var email: String = "",
//    var password: String = "",
//    var type: String = ""
//){
//    override fun toString(): String {
//        return "LoginCall(email='$email', password='$password')"
//    }
//}

@Entity(tableName = "login")
 data class Login(

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "userId")
    var userId: Long,

    @ColumnInfo(name = "userEmail")
    var email: String,

    @ColumnInfo(name = "userPassword")
    var password: String,
)
