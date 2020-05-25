package com.example.stock_fact.domain

data class UserRequest(
    var name: String?,
    var subName: String?,
    var userName: String?,
    var passWord: String?,
    var mail: String?,
    var phoneNumber: String?,
    var UserType: String?
) {
}