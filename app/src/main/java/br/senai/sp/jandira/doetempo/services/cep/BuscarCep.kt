import android.util.Log
import br.senai.sp.jandira.doetempo.model.Cep
import br.senai.sp.jandira.doetempo.services.RetrofitFactory
import br.senai.sp.jandira.doetempo.services.cep.RetrofitFactoryCep
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun buscarCep(cep: String, onComplete: (Cep) -> Unit) {
    val call = RetrofitFactoryCep()
        .retrofitService().getCep(cep)

    call.enqueue(object : Callback<Cep> {
        override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
            if(response.body()!!.cep.isNullOrEmpty()) {
                return
            } else {
                var stringResult = "${response.body()!!.logradouro}, ${response.body()!!.bairro}, ${response.body()!!.cidade} - ${response.body()!!.cep} - ${response.body()!!.estado}"

                onComplete.invoke(response.body()!!)
            }

        }

        override fun onFailure(call: Call<Cep>, t: Throwable) {
            Log.i("ds3m", t.message.toString())
        }
    })
}