package br.senai.sp.jandira.doetempo.model

data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var cpf: String = "",
    var birthdate: String = "",
    var postal_code: String = "",
    var number: String = "",
    //var gender: Gender,
){
    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email', password='$password', cpf='$cpf', birthdate='$birthdate', postal_code='$postal_code', number='$number')"
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