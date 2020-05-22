package com.example.stock_fact.domain

import java.util.*

data class Stock(
    var stockId: Long?,
    var dateCreation: Date?,
  var   quantity : Int?) {
    constructor():this(0,Date(),0)
}