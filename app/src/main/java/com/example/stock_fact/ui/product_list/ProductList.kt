package com.example.stock_fact.ui.product_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stock_fact.R
import com.example.stock_fact.domain.CommanRequest
import com.example.stock_fact.domain.ProductCommand
import com.example.stock_fact.domain.ProductResponse
import com.example.stock_fact.ui.product_list.items.ProductItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_list_fragment.*


class ProductList : Fragment() {

    companion object {
        var viewModelCurrent: ProductListViewModel? = null
        fun newInstance() = ProductList()
    }

    var builder: AlertDialog.Builder? = null
    private lateinit var viewModel: ProductListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        viewModelCurrent = viewModel
        builder = AlertDialog.Builder(requireContext())
        viewModel.getProducts("landry", "1234", requireContext())
        initRecyclerview(viewModel.productList.value)
        observeViewModel()
        setOnClickListener()
    }

    private fun initRecyclerview(list: List<ProductResponse?>?) {
        product_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            product_list.adapter = GroupAdapter<ViewHolder>().apply {
                try {
                    list!!.forEach {
                        Log.e("product", it.toString())
                        add(Section(ProductItem(it!!, lifecycleOwner = viewLifecycleOwner)))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }
        }
    }

    private fun observeViewModel() {
        viewModel.productList.observe(viewLifecycleOwner, Observer {
            initRecyclerview(it)
        })
        viewModel.productsOrdered.observe(viewLifecycleOwner, Observer {
            it.forEach {
                Log.e("new order", it.toString())
            }
        })
    }

    private fun setOnClickListener() {
        commander.setOnClickListener {
            Log.e("buutpn", "clicked")
            var totalPrice = 0.0
            var listTosend = mutableListOf<ProductCommand>()
            viewModel.productsOrdered.value!!.forEach {
                if (it.quantityOrdered!!.compareTo(0) > 0) {
                    listTosend.add(it)
                    totalPrice = totalPrice + (it.product!!.price * it.quantityOrdered!!)
                }

            }

            var command = CommanRequest(
                commandtype = "FACTURE",
                customerPhone = 237698481557,
                productCommand = listTosend,
                status = "CONFIRM",
                totalPrice = totalPrice.toFloat()
            )

            //Uncomment the below code to Set the message and title from the strings.xml file
            //Uncomment the below code to Set the message and title from the strings.xml file
            builder!!.setMessage("TOTAL Price " + totalPrice).setTitle(R.string.confirm_command)

            //Setting message manually and performing action on button click
            //Setting message manually and performing action on button click
            builder!!.setMessage("Do you want to confirm this order?\nTOTAL PRICE: " + totalPrice)
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { dialog, id ->
                    viewModel.performRegisterCommand(
                        "landry",
                        "1234",
                        requireContext(),
                        command,
                        requireView()
                    )
                }
                .setNegativeButton(
                    "No"
                ) { dialog, id ->
                    //  Action for 'NO' Button
                    dialog.cancel()
                    Toast.makeText(
                        requireContext(),
                        "you choose no action for alertbox",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            //Creating dialog box
            //Creating dialog box
            val alert = builder!!.create()
            //Setting the title manually
            //Setting the title manually
            alert.setTitle(requireContext().getText(R.string.confirm_command))
            alert.show()
        }
    }

}
