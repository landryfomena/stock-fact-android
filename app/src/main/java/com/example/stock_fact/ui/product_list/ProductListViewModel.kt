package com.example.stock_fact.ui.product_list

import android.app.ProgressDialog
import android.content.Context
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.stock_fact.R
import com.example.stock_fact.domain.*
import com.example.stock_fact.repository.ApiUtils
import com.example.stock_fact.repository.SprintPayServicesRemote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var productList = MutableLiveData<List<ProductResponse?>>()
    var productsOrdered=MutableLiveData<MutableList<ProductCommand>>().apply {
        value= mutableListOf<ProductCommand>()
    }
    private var mSPServicesRemote: SprintPayServicesRemote? = null

    fun getProducts(edLogin: String, edEmail: String, context: Context) {
        mSPServicesRemote = ApiUtils.getSprintPayService();
        val progressDialogEvaluate = ProgressDialog(context)
        progressDialogEvaluate.setMessage(context.resources.getString(R.string.login))
        progressDialogEvaluate.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialogEvaluate.setCancelable(false)
        progressDialogEvaluate.setCanceledOnTouchOutside(false)
        //progressDialogEvaluate.setProgressDrawable(resources.getDrawable(R.drawable.circular_progress_view))
        progressDialogEvaluate.show()
        val base = edLogin + ":" + edEmail
        val authHeader: String = "Basic " + Base64.encodeToString(
            base.toByteArray(),
            Base64.NO_WRAP
        )
        val compte: Call<List<ProductResponse?>?>? = mSPServicesRemote!!.listRepos(authHeader)
        try {
            compte!!.enqueue(object : Callback<List<ProductResponse?>?> {
                override fun onFailure(call: Call<List<ProductResponse?>?>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<List<ProductResponse?>?>?,
                    response: Response<List<ProductResponse?>?>?
                ) {
                    progressDialogEvaluate.dismiss()
                    if (response!!.isSuccessful) {
                        progressDialogEvaluate.dismiss()
                        productList.value = response.body()
                    } else {
                        Toast.makeText(
                            context,
                            "ERROr " + response.errorBody().string(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

            })
        } catch (e: Exception) {
            progressDialogEvaluate.dismiss()
            progressDialogEvaluate.dismiss()
            e.printStackTrace()
        }
    }

    fun performRegisterCommand(edLogin: String, edEmail: String,context: Context,command: CommanRequest,view:View) {
        val progressDialogEvaluate = ProgressDialog(context)
        progressDialogEvaluate.setMessage(context.getString(R.string.login))
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
        val compte: Call<Command?>? = mSPServicesRemote!!.createCommand(authHeader,command)
        try {
            compte!!.enqueue(object : Callback<Command?> {
                override  fun onResponse(
                    call: Call<Command?>?,
                    response: Response<Command?>
                ) {
                    if (response.isSuccessful()) {
                        val productCreated: Command = response.body()!!
                        Log.e("user", productCreated.toString())
                        progressDialogEvaluate.dismiss()
                        Navigation.findNavController(view).navigateUp()
                    } else {
                        progressDialogEvaluate.dismiss()
                        Toast.makeText(
                            context,
                            response.errorBody().string(),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("echec:",  response.message())
                    }
                }

                override fun onFailure(call: Call<Command?>?, t: Throwable) {
                    Log.e("echec:", t.message)
                    progressDialogEvaluate.dismiss()
                    Toast.makeText(
                        context,
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
