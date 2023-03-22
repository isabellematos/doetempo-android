package br.senai.sp.jandira.doetempo.model

import androidx.compose.runtime.MutableState
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class User(
    var id: String? = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var cpf: String = "",
    var birthdate: String,
    var address: Address?,
    var rg: String? = "",
    var gender: String = "e180d522-f176-4c44-9005-160aa1d9ecf1",
    @SerializedName("id_type") var idType: String? = ""
){
    override fun toString(): String {
       return "User(id=$id, name='$name', email='$email', password='$password', cpf='$cpf')"
    }
}

//"cpf": "414214124142",
//            "birthdate": "2023-02-15T00:00:00.000Z",
//            "email": "teste2@teste.com",
//            "id": "0febaeaa-2cda-4ffc-b012-cf32c8756bda",
//            "name": "teste",
//            "password": "$2a$08$Kiqx02UsVbuCcFm/nzl9R.jwMNg/9uyQAzcIGrX/FWKHAqNIMSynu",
//            "tbl_user_phone": [
//                {
//                    "tbl_phone": {
//                        "number": null
//                    }
//                }
//            ],
//            "gender": {
//                "abbreviation": "M"
//            }
//        }