package com.example.stock_fact.ui.validate_account

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stock_fact.Login
import com.example.stock_fact.MainActivity
import com.example.stock_fact.R
import com.example.stock_fact.domain.User
import com.example.stock_fact.repository.ApiUtils
import com.example.stock_fact.repository.SprintPayServicesRemote
import kotlinx.android.synthetic.main.activity_validate_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ValidateAccount : AppCompatActivity() {
    lateinit var userName: String
    private var mSPServicesRemote: SprintPayServicesRemote? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_account)
        mSPServicesRemote = ApiUtils.getSprintPayService();
        try {
            userName = intent.getStringExtra("username")
            user_validate_user_name.setText(userName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setOnclickListener()
    }

    private fun setOnclickListener() {
        validate_account.setOnClickListener {
            if (!validate()) {
                performRegisterCommand(
                    "landry",
                    "1234",
                    user_validate_user_name.text.toString(),
                    user_validate_phone_number.text.toString().toInt()
                )
            }
        }
    }

    private fun validate(): Boolean {
        var result = false
        if (user_validate_phone_number.text.isNullOrEmpty()) {
            result = true
        }
        if (user_validate_user_name.text.isNullOrEmpty()) {
            result = true
        }
        return result
    }

    fun performRegisterCommand(
        edLogin: String,
        edEmail: String,
        username: String,
        validateMessage: Int
    ) {
        val progressDialogEvaluate = ProgressDialog(applicationContext)
        progressDialogEvaluate.setMessage(applicationContext.getString(R.string.login))
        progressDialogEvaluate.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialogEvaluate.setCancelable(false)
        progressDialogEvaluate.setCanceledOnTouchOutside(false)
        //progressDialogEvaluate.setProgressDrawable(resources.getDrawable(R.drawable.circular_progress_view))
//        progressDialogEvaluate.show()
        val base = edLogin + ":" + edEmail
        val authHeader: String = "Basic " + Base64.encodeToString(
            base.toByteArray(),
            Base64.NO_WRAP
        )
        val compte: Call<User?>? =
            mSPServicesRemote!!.validateUser(authHeader, validateMessage, username)
        try {
            compte!!.enqueue(object : Callback<User?> {
                override fun onResponse(
                    call: Call<User?>?,
                    response: Response<User?>
                ) {
                    if (response.isSuccessful()) {
                        val productCreated: User = response.body()!!
                        Log.e("user", productCreated.toString())
                      //  progressDialogEvaluate.dismiss()
                        val i = Intent(applicationContext, Login::class.java)
                        startActivity(i)

                    } else {
                        //progressDialogEvaluate.dismiss()
                        Toast.makeText(
                            applicationContext,
                            response.errorBody().string(),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("echec:", response.message())
                    }
                }

                override fun onFailure(call: Call<User?>?, t: Throwable) {
                    Log.e("echec:", t.message)
                   // progressDialogEvaluate.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "invialid username or password",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        } catch (e: Exception) {
            progressDialogEvaluate.dismiss()
            e.printStackTrace()
        }
    }
}
