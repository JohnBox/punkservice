package com.omo.bankservice.service

import org.springframework.stereotype.Service

@Service
class BankServiceImpl(private val userBalance: MutableMap<String, Long>) : BankService {

    override fun saveBalance(transactionId: String, userId: String, amount: Long) {
        userBalance[userId] = userBalance.getOrDefault(userId, 0L) + amount
    }

    override fun getBalance(userId: String) = userBalance[userId] ?: 0L
}