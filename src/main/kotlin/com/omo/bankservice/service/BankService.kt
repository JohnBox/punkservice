package com.omo.bankservice.service

interface BankService {

    fun saveBalance(transactionId: String, userId: String, amount: Long)
    fun getBalance(userId: String): Long

}