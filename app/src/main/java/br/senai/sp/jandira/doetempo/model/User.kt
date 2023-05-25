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
    var address: Address?,
    var gender: String = "",
    var photo_url: String? = "",


    var type: Type?,
    @SerializedName("user_phone")
    var userPhone: UserPhone? = null,
    @SerializedName("user_address")
    val userAddress: UserAddress? = null,
    @SerializedName("attached_link")
    val attachedLink: List<AttachedLink>? = null,
    @SerializedName("_count")
    val count: Count? = null
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