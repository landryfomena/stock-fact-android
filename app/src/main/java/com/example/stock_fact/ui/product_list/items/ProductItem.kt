package com.example.stock_fact.ui.product_list.items

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.stock_fact.R
import com.example.stock_fact.domain.ProductCommand
import com.example.stock_fact.domain.ProductResponse
import com.example.stock_fact.ui.product_list.ProductList
import com.example.stock_fact.ui.product_list.ProductListViewModel
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.product_item.view.*
import java.lang.Exception

class ProductItem(
    val product: ProductResponse,
    val lifecycleOwner: LifecycleOwner
) : Item() {
    var quantity = MutableLiveData<Int>().apply {
        value = 0
    }
    var command = ProductCommand(quantityOrdered = 0, product = product)
    override fun bind(viewHolder: ViewHolder, position: Int) {
        ProductList.viewModelCurrent!!.productsOrdered.value!!.add(command)
        val orderedQuantity = viewHolder.itemView.ordered
        orderedQuantity.setText("0")
        if (product.title != null) {
            viewHolder.itemView.item_product_name.setText(product.title)
        }
        if (product.price != null) {
            viewHolder.itemView.item_product_price.setText(product.price.toString())
        }
        if (product.stock != null) {
            viewHolder.itemView.item_product_quantity.setText(product.stock!!.quantity.toString())
        }
        viewHolder.itemView.product_add_qte.setOnClickListener {
            try {
                if (quantity.value!!.compareTo(product.stock.quantity!!) < 0) {
                    quantity.value = quantity.value!!.plus(1)
                    command.quantityOrdered = quantity.value
                    var i = 0
                    var ii=0
                    ProductList.viewModelCurrent!!.productsOrdered!!.value!!.forEach {
                        if (it.product!!.equals(command.product)) {
                            ii=i
                        }
                        i++
                    }
                    ProductList.viewModelCurrent!!.productsOrdered.value!!.remove(ProductList.viewModelCurrent!!.productsOrdered.value!!.get(ii))
                    ProductList.viewModelCurrent!!.productsOrdered.value!!.add(command)
                    ProductList.viewModelCurrent!!.productsOrdered.value!!.forEach {
                        Log.e("order",it.toString())
                    }

                } else {
                    Toast.makeText(
                        viewHolder.itemView.context,
                        viewHolder.itemView.context.getText(R.string.insufiscient),
                        Toast.LENGTH_LONG
                    ).show()
                    command.quantityOrdered = quantity.value
                }
            }catch (e:Exception){
                e.printStackTrace()
            }


        }
        viewHolder.itemView.product_reduise_qte.setOnClickListener {
            var i = 0
            var ii=0
            if (quantity.value!!.compareTo(0) > 0) {
                quantity.value = quantity.value!!.minus(1)
                command.quantityOrdered = quantity.value
                ProductList.viewModelCurrent!!.productsOrdered!!.value!!.forEach {
                    if (it.product!!.equals(command.product)) {
                        ii=i
                    }
                    i++
                }
                ProductList.viewModelCurrent!!.productsOrdered.value!!.remove(
                    ProductList.viewModelCurrent!!.productsOrdered.value!!.get(
                        ii
                    )
                )
                ProductList.viewModelCurrent!!.productsOrdered.value!!.add(command)
                ProductList.viewModelCurrent!!.productsOrdered.value!!.forEach {
                    Log.e("order",it.toString())
                }
            } else {
                Toast.makeText(
                    viewHolder.itemView.context,
                    viewHolder.itemView.context.getText(R.string.positive),
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        quantity.observe(lifecycleOwner, Observer {
            orderedQuantity.setText("" + it)
        })
    }

    override fun getLayout() = R.layout.product_item

}