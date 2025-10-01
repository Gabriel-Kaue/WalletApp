package com.example.walletapp.model

data class Transaction (
    val id: Long = 0,
    val tipo: TipoTransaction,
    val valor: Double,
    val descricao: String
)

