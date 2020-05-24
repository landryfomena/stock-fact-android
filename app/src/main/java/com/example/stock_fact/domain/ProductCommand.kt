package com.example.stock_fact.domain

data class ProductCommand(
    var quantityOrdered:Int?,
    var product:ProductResponse?
) {

}