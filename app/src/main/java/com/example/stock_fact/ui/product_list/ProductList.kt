package com.example.stock_fact.ui.product_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.stock_fact.R
import com.example.stock_fact.ui.product_list.items.ProductItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.product_list_fragment.*

class ProductList : Fragment() {

    companion object {
        fun newInstance() = ProductList()
    }

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
        viewModel.getProducts("landry","1234",requireContext())
       initRecyclerview()
    }
    private fun initRecyclerview(){
        product_list.apply {
            layoutManager=LinearLayoutManager(requireContext())

        }
    }
    private fun observeViewModel(){
        viewModel.productList.observe(viewLifecycleOwner, Observer {
            it.forEach {product->
                product_list.adapter=GroupAdapter<ViewHolder>().apply {
                        add(Section(ProductItem(product!!)))
                }
            }
        })
    }

}
