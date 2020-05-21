package com.example.stock_fact.domain

data class User(var  userId:Int,
var   name:String,
var  subName:String,
 var userName: String ?,
  var passWord:String,
 var mail:String ,
var   phoneNumber:String,
                var UserType :String,
 var userStatus:String,
                 var validate:Boolean,
var   validateMessage:Integer
) {
}