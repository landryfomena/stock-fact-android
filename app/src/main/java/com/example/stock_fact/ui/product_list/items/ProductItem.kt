package com.example.stock_fact.ui.product_list.items

import com.example.stock_fact.R
import com.example.stock_fact.domain.ProductResponse
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.addproduct_fragment.view.*
import kotlinx.android.synthetic.main.product_item.view.*

class ProductItem(var product:ProductResponse):Item() {
    var quantityOrdered=0
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.product_name.setText(product.title)
        viewHolder.itemView.product_price.setText(product.price.toString())
        viewHolder.itemView.product_quantity.setText(product.stock!!.quantity.toString())
        viewHolder.itemView.ordered.setText(quantityOrdered)
        viewHolder.itemView.product_add_qte.setOnClickListener {
            quantityOrdered+=1
            viewHolder.itemView.ordered.setText(quantityOrdered)
        }
        viewHolder.itemView.product_reduise_qte.setOnClickListener {
            quantityOrdered-=1
            viewHolder.itemView.ordered.setText(quantityOrdered)
        }
    }

    override fun getLayout()= R.layout.product_item
}