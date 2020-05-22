package com.example.stock_fact.domain

import java.util.*

data class Product(
    var title: String?,
    var price: Double?
) {
    constructor():this("",0.0)
}