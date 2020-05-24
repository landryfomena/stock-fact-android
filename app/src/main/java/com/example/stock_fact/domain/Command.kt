package com.example.stock_fact.domain

import java.util.*

data class Command(
    var commandId: Long?,
    var dateCreation: Date?,
    var totalPrice: Float?,
    var customerPhone: Long?,
    var status: String?,
    var commandtype: String?,
    var productCommand: List<ProductCommand>?
) {
}