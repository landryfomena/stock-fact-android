package com.example.stock_fact.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stock_fact.Login
import com.example.stock_fact.R
import com.example.stock_fact.domain.User
import com.example.stock_fact.domain.UserRequest
import com.example.stock_fact.domain.UserType
import com.example.stock_fact.repository.ApiUtils
import com.example.stock_fact.repository.SprintPayServicesRemote
import com.example.stock_fact.ui.validate_account.ValidateAccount
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private var mSPServicesRemote: SprintPayServicesRemote? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register)
        mSPServicesRemote = ApiUtils.getSprintPayService();
        setOnClickListener()
    }


    private fun setOnClickListener() {
        RegisterUser.setOnClickListener {
            if (!validateField()) {
                var userRequest = UserRequest(
                    user_first_name.text.toString(),
                    user_last_name.text.toString(),
                    user_user_name.text.toString(),
                    user_password.text.toString(),
                    user_mail.text.toString(),
                    user_phon_number.text.toString(),
                    UserType = UserType.ADMIN.toString()

                )
                performRegisterCommand("landry","1234",userRequest)
            }
        }
        login_register.setOnClickListener {
            val i= Intent(this,Login::class.java)
            intent.putExtra("username",user_user_name.text.toString())
            startActivity(i)
        }

    }

    private fun validateField(): Boolean {
        var result = false
        if (user_first_name.text.isNullOrEmpty()) {
            result = true
            user_first_name.error = getString(R.string.fill_this_field)
        }
        if (user_mail.text.isNullOrEmpty()) {
            result = true
            user_mail.error = getString(R.string.fill_this_field)
        }
        if (user_phon_number.text.isNullOrEmpty()) {
            result = true
            user_phon_number.error = getString(R.string.fill_this_field)
        }
        if (user_user_name.text.isNullOrEmpty()) {
            result = true
            user_user_name.error = getString(R.string.fill_this_field)
        }
        if (user_password.text.isNullOrEmpty()) {
            result = true
            user_password.error = getString(R.string.fill_this_field)
        }
        return result
    }

    fun performRegisterCommand(edLogin: String, edEmail: String, user: UserRequest) {
        val progressDialogEvaluate = ProgressDialog(applicationContext)
        progressDialogEvaluate.setMessage(applicationContext.getString(R.string.login))
        progressDialogEvaluate.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialogEvaluate.setCancelable(false)
        progressDialogEvaluate.setCanceledOnTouchOutside(false)
        //progressDialogEvaluate.setProgressDrawable(resources.getDrawable(R.drawable.circular_progress_view))
        //progressDialogEvaluate.show()
        val base = edLogin + ":" + edEmail
        val authHeader: String = "Basic " + Base64.encodeToString(
            base.toByteArray(),
            Base64.NO_WRAP
        )
        val compte: Call<User?>? = mSPServicesRemote!!.createUser(authHeader, user)
        try {
            compte!!.enqueue(object : Callback<User?> {
                override fun onResponse(
                    call: Call<User?>?,
                    response: Response<User?>
                ) {
                    if (response.isSuccessful()) {
                        val productCreated: User = response.body()!!
                        Log.e("user", productCreated.toString())
                        //progressDialogEvaluate.dismiss()
                        val i= Intent(applicationContext,ValidateAccount::class.java)
                        startActivity(i)

                    } else {
                       // progressDialogEvaluate.dismiss()
                        val i= Intent(applicationContext,ValidateAccount::class.java)
                        startActivity(i)
                        Toast.makeText(
                            applicationContext,
                            response.errorBody().string(),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("echec:", response.message())
                    }
                }

                override fun onFailure(call: Call<User?>?, t: Throwable) {
                   t.printStackTrace()
                    progressDialogEvaluate.dismiss()
                    Toast.makeText(
                        applicationContext,
                        t.message,
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
