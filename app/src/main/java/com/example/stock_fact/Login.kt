package com.example.stock_fact

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.stock_fact.domain.User
import com.example.stock_fact.repository.ApiUtils
import com.example.stock_fact.repository.SprintPayServicesRemote
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class Login : AppCompatActivity() {
    var btnSeConnecter: Button? = null
    var tvCreerCompte: TextView? = null
    var edLogin: EditText? = null
    var edEmail:EditText? = null
    private var mSPServicesRemote: SprintPayServicesRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSPServicesRemote= ApiUtils.getSprintPayService();
        setContentView(R.layout.activity_login)
        edLogin=findViewById(R.id.login)
        edEmail=findViewById(R.id.password)
        setOnclickistener()
    }
    private fun setOnclickistener(){
        singnin_btn.setOnClickListener {

            if(validInputs()){
                login(edLogin!!.getText().toString(),edEmail!!.getText().toString());
            }

        }
    }
    private fun login(edLogin: String, edEmail: String) {
        val progressDialogEvaluate = ProgressDialog(this@Login)
        progressDialogEvaluate.setMessage(resources.getString(R.string.login))
        progressDialogEvaluate.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialogEvaluate.setCancelable(false)
        progressDialogEvaluate.setCanceledOnTouchOutside(false)
        //progressDialogEvaluate.setProgressDrawable(resources.getDrawable(R.drawable.circular_progress_view))
        progressDialogEvaluate.show()
        val base = edLogin+":"+edEmail
        val authHeader: String = "Basic " + Base64.encodeToString(
            base.toByteArray(),
            Base64.NO_WRAP
        )
        val compte: Call<User?>? = mSPServicesRemote!!.login(authHeader)
        try {
            compte!!.enqueue(object : Callback<User?> {
                override  fun onResponse(
                    call: Call<User?>?,
                    response: Response<User?>
                ) {
                    if (response.isSuccessful()) {
                        val userconnected: User = response.body()!!
                        Log.e("user", userconnected.toString())
                        progressDialogEvaluate.dismiss()
                        var intent=Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        progressDialogEvaluate.dismiss()
                        Toast.makeText(
                            applicationContext,
                            response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("echec:",  response.message())
                    }
                }

               override fun onFailure(call: Call<User?>?, t: Throwable) {
                    Log.e("echec:", t.message)
                    progressDialogEvaluate.dismiss()
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

    private fun validInputs(): Boolean {
        var valid = true
        if (edLogin!!.getText().toString().trim().equals("",true)) {
            edLogin!!.setError("require!")
            valid = false
        }
        if (edEmail!!.getText().toString().trim().equals("",true)) {
            edEmail!!.setError("require!")
            valid = false
        }
        return valid
    }

}
