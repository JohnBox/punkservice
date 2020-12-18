package com.omo.bankservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.omo.bankservice.controller.Controller
import com.omo.bankservice.service.BankService
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.UUID
import kotlin.Long.Companion
import kotlin.random.Random

@WebMvcTest(Controller::class)
class ControllerTest {
    @Autowired
    private lateinit var mockMVC: MockMvc

    @MockkBean(relaxed = true)
    private lateinit var bankService: BankService

    private val mapper = jacksonObjectMapper()

    @Test
    fun `should receive payment`() {
        val transactionId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val amount = Random.nextLong(Long.MAX_VALUE)
        val request = mapOf(
            "transactionId" to transactionId,
            "userId" to userId,
            "amount" to amount
        )

        mockMVC.post("/payment") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }

        verify {
            bankService.saveBalance(transactionId, userId, amount)
        }
    }

    @Test
    fun `should get balance`() {
        val userId = UUID.randomUUID().toString()

        mockMVC.get("/balance") {
            param("userId", userId)
        }.andExpect {
            status { isOk() }
        }

        verify {
            bankService.getBalance(userId)
        }
    }

    @Test
    fun `should return exception if the amount is negative`() {
        val transactionId = UUID.randomUUID().toString()
        val userId = UUID.randomUUID().toString()
        val amount = Random.nextLong(Companion.MIN_VALUE, 0)
        val request = mapOf(
            "transactionId" to transactionId,
            "userId" to userId,
            "amount" to amount
        )

        mockMVC.post("/payment") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(request)
        }.andExpect {
            status { isBadRequest() }
        }
    }
}
