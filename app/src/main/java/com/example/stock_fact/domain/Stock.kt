package com.example.stock_fact.domain

import java.util.*

data class Stock(
    var stockId: Long?,
    var quantity: Int?
) {
    constructor() : this(0, 0)
}