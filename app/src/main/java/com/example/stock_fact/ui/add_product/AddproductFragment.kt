package com.example.stock_fact.ui.add_product

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.stock_fact.R
import com.example.stock_fact.domain.Product
import com.example.stock_fact.domain.ProductResponse
import com.example.stock_fact.repository.ApiUtils
import com.example.stock_fact.repository.SprintPayServicesRemote
import kotlinx.android.synthetic.main.addproduct_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddproductFragment : Fragment() {

    companion object {
        fun newInstance() = AddproductFragment()
    }
    private var mSPServicesRemote: SprintPayServicesRemote? = null
    private lateinit var viewModel: AddproductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.addproduct_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSPServicesRemote= ApiUtils.getSprintPayService();
        viewModel = ViewModelProviders.of(this).get(AddproductViewModel::class.java)
        onClickListener()
    }
private fun onClickListener(){
    register.setOnClickListener {
        if (product_name.text.isNullOrBlank()){
            product_name.error=resources.getString(R.string.fill_product_name)
        }else{
            if (product_price.text.isNullOrEmpty()){
                product_price.error=resources.getString(R.string.fill_product_price)
            }else{
                var product=Product(product_name.text.toString(),product_price.text.toString().toDouble())
                performRegisterProdut("landry","1234",product)
            }
        }
    }
}


    private fun performRegisterProdut(edLogin: String, edEmail: String,product: Product) {
        val progressDialogEvaluate = ProgressDialog(requireContext())
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
        val compte: Call<ProductResponse?>? = mSPServicesRemote!!.createProduct(authHeader,product = product)
        try {
            compte!!.enqueue(object : Callback<ProductResponse?> {
                override  fun onResponse(
                    call: Call<ProductResponse?>?,
                    response: Response<ProductResponse?>
                ) {
                    if (response.isSuccessful()) {
                        val productCreated: ProductResponse = response.body()!!
                        Log.e("user", productCreated.toString())
                        progressDialogEvaluate.dismiss()
                        Navigation.findNavController(requireView()).navigateUp()
                    } else {
                        progressDialogEvaluate.dismiss()
                        Toast.makeText(
                            requireContext(),
                            response.message(),
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e("echec:",  response.message())
                    }
                }

                override fun onFailure(call: Call<ProductResponse?>?, t: Throwable) {
                    Log.e("echec:", t.message)
                    progressDialogEvaluate.dismiss()
                    Toast.makeText(
                        requireContext(),
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
