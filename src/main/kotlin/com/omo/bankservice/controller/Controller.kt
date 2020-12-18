package com.omo.bankservice.controller

import com.omo.bankservice.service.BankService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.PositiveOrZero

@RestController
class Controller(private val bankService: BankService) {

    @PostMapping("/payment")
    fun savePayment(@RequestBody @Valid paymentReceived: PaymentReceivedDto) {
        bankService.saveBalance(paymentReceived.transactionId, paymentReceived.userId, paymentReceived.amount)
    }

    data class PaymentReceivedDto(
        @get:NotEmpty(message = "transactionId should not be empty! ")
        val transactionId: String,
        @get:NotEmpty(message = "userId should not be empty!")
        val userId: String,
        @get:PositiveOrZero(message = "Amount should be positive")
        val amount: Long,
    )

    @GetMapping("/balance")
    fun getBalance(@RequestParam userId: String): ResponseEntity<Map<String, Long>> {
        val balance = bankService.getBalance(userId)
        return ResponseEntity.ok(mapOf("balance" to balance))
    }
}