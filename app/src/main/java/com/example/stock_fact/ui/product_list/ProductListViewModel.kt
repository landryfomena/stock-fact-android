package com.example.stock_fact.ui.product_list

import android.app.ProgressDialog
import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stock_fact.R
import com.example.stock_fact.domain.ProductResponse
import com.example.stock_fact.repository.ApiUtils
import com.example.stock_fact.repository.SprintPayServicesRemote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var productList = MutableLiveData<List<ProductResponse?>>()
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
        val compte: Call<List<ProductResponse>?> = mSPServicesRemote!!.getProducts(authHeader)
        try {
          compte.enqueue(object :Callback<List<ProductResponse>?>{
              override fun onFailure(call: Call<List<ProductResponse>?>?, t: Throwable?) {

              }

              override fun onResponse(
                  call: Call<List<ProductResponse>?>?,
                  response: Response<List<ProductResponse>?>?
              ) {

              }

          })
        } catch (e: Exception) {
            progressDialogEvaluate.dismiss()
            e.printStackTrace()
        }
    }
}
