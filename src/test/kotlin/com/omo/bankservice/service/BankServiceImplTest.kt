package com.omo.bankservice.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import java.util.UUID
import kotlin.random.Random

internal class BankServiceImplTest {

    private lateinit var bankService: BankService
    private lateinit var userBalance: MutableMap<String, Long>

    @BeforeEach
    fun init() {
        userBalance = mutableMapOf()
        bankService = BankServiceImpl(userBalance)
    }

    @Test
    fun `should create user and save balance`() {
        val transactionId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val amount = Random.nextLong(0, Long.MAX_VALUE)

        bankService.saveBalance(transactionId, userId, amount)

        assertEquals(userBalance[userId], amount)
    }

    @Test
    fun `should update balance for existed user`() {
        val transactionId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val amount = Random.nextLong(0, Long.MAX_VALUE / 2)
        val startBalance = Random.nextLong(0, Long.MAX_VALUE / 2)
        userBalance[userId] = startBalance

        bankService.saveBalance(transactionId, userId, amount)

        assertEquals(userBalance[userId],amount + startBalance)
    }

    @Test
    fun `should return balance for existed user`() {
        val userId = UUID.randomUUID().toString()
        val expectedAmount = Random.nextLong(0, Long.MAX_VALUE)
        userBalance[userId] = expectedAmount

        val actualAmount = bankService.getBalance(userId)

        assertEquals(expectedAmount, actualAmount)
    }

    @Test
    fun `should return zero balance for not existed user`(){
        val userId = UUID.randomUUID().toString()

        val actualAmount = bankService.getBalance(userId)

        assertEquals(0, actualAmount)
    }
}
