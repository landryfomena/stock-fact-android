package com.example.stock_fact.domain


import java.util.*

data class ProductResponse(
    var productId: Long?,
    var title: String?,
    var price: Float?,
    var dateCreation: Date?,
    var status: String?,
    var owner:String?,
    var stock:Stock?

) {
    constructor():this(0,"",0.toFloat(),Date(),"","",Stock())
}