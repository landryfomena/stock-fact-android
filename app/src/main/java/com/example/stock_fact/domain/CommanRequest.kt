package com.example.stock_fact.domain

data class CommanRequest( var totalPrice: Float?,
                          var customerPhone: Long?,
                          var status: String?,
                          var commandtype: String?,
                          var productCommand: List<ProductCommand>?) {
}