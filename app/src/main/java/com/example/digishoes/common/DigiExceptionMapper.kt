package com.example.digishoes.common

import com.example.digishoes.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

class DigiExceptionMapper {
    companion object {
        fun map(throwable: Throwable): DigiException {
            if (throwable is HttpException) {
                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorJsonObject.getString("message")
                    return DigiException(if (throwable.code() == 401) DigiException.Type.AUTH else DigiException.Type.SIMPLE,serverMessage = errorMessage)
                } catch (exception: Exception) {
                    Timber.e(exception)
                }
            }
            return DigiException(DigiException.Type.SIMPLE, R.string.unknown_error)
        }
    }
}