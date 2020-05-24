package com.example.stock_fact.repository

import com.example.stock_fact.repository.RetrofitClient.getClient


/**
 * Created by netserve on 13/10/2017.
 */
object ApiUtils {
    //public static final String SPRINTPAY_URL = "https://pcs.sprint-pay.com/paymentcoresystem/rest/";
//public static final String SPRINTPAY_URL = "https://test-api.s0rint-pay.com/paymentcoresystem/rest/";
// public static final String SPRINTPAY_URL = "http://172.16.1.43:0086/paymentcoresystem/rest/";
//public static final String SPRINTPAY_URL = "http://192.168.1.124:8080/";
//    public static final String SPRINTPAY_URL = "http://192.168.137.1:8080/";
    const val SPRINTPAY_URL = "http://192.168.43.23:8080/"

     fun getSprintPayService(): SprintPayServicesRemote {
        return getClient(SPRINTPAY_URL)!!.create(
            SprintPayServicesRemote::class.java
        )
    }
}
