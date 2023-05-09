package br.senai.sp.jandira.doetempo.model

import com.google.gson.annotations.SerializedName

data class User(
    var id: String? = null,
    var name: String? = null,
    var email: String = "",
    var password: String? = "",
    var cpf: String = "",
    var birthdate: String,
    var description: String?  = "",
    @SerializedName("user_address")
    var address: Address?,
    var gender: Gender,
    var photo_url: String? = "",
    @SerializedName("id_type") var idType: String? = "",
    //var type: Type?,
    @SerializedName("user_phone")
    var userPhone: UserPhone? = null,
)
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