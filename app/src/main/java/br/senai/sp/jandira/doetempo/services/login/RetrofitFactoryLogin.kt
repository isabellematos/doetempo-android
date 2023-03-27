import br.senai.sp.jandira.doetempo.constants.Constants.Companion.BASE_URL
import br.senai.sp.jandira.doetempo.services.login.AuthApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//package br.senai.sp.jandira.doetempo.services.login
//
////import br.senai.sp.jandira.doetempo.BuildConfig
//import br.senai.sp.jandira.doetempo.constants.Constants
//import com.google.gson.GsonBuilder
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//class RetrofitFactoryLogin {
//
//    object RetrofitHelper {
//        private val retrofit: Retrofit
//        init {
//            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
//                val builder = Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .writeTimeout(0, TimeUnit.MILLISECONDS)
//                .writeTimeout(2, TimeUnit.MINUTES)
//                .connectTimeout(1, TimeUnit.MINUTES).build()
//            retrofit = builder.client(okHttpClient).build()
//        }
//        fun getAuthService() : AuthApiService {
//            return retrofit.create(AuthApiService::class.java)
//        }
//    }
//}

class RetrofitFactoryLogin () {
    companion object {
        private lateinit var instance: Retrofit

        fun getRetrofit(): Retrofit {
            if (!Companion::instance.isInitialized) {
                instance = Retrofit
                    .Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
                return instance
            } else {
                return instance
            }
        }

        fun retrofitService() : AuthApiService {
            return instance.create(AuthApiService::class.java)
        }
    }
}