package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class User(
    var id: String? = "",
    var name: String = "",
    var email: String = "",
    var password: String? = "",
    var cpf: String = "",
    var birthdate: String,
    var address: Address?,
    var rg: String? = "",
    var gender: Gender,
    var id_type: String = "",
    var photo_url: String? = "",
    @SerializedName("id_type") var idType: String? = "",
    var type: Type?
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