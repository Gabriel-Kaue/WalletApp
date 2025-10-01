package com.example.walletapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.walletapp.model.TipoTransaction
import com.example.walletapp.model.Transaction
import kotlin.apply

class TransactionDAO (private val context: Context) {

    private val dbHelper = DBHelper(context)

    fun addTransaction(transaction: Transaction){
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply{
            put("descricao", transaction.descricao)
            put("tipo", transaction.tipo.name)
            put("valor", transaction.valor)
        }
        db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTransactions(): List<Transaction> {
        val db = dbHelper.readableDatabase
        val transactions = mutableListOf<Transaction>()
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, "id DESC")

        while(cursor.moveToNext()){
            val id = cursor.getLong(cursor.getColumnIndexOrThrow("id"))
            val valor = cursor.getDouble(cursor.getColumnIndexOrThrow("valor"))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
            val tipoString = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))

            val tipo = TipoTransaction.valueOf(tipoString)

            transactions.add(Transaction(id, tipo, valor, descricao))
        }
        cursor.close()
        db.close()
        return transactions
    }

    fun getSaldo(): Double{
        val db = dbHelper.readableDatabase
        var saldo = 0.0

        val sql = "SELECT " +
                "SUM(CASE WHEN tipo = 'CREDITO' THEN valor ELSE 0 END) - " +
                "SUM(CASE WHEN tipo = 'DEBITO' THEN valor ELSE 0 END) " +
                "FROM " + DBHelper.TABLE_NAME

        val cursor: Cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()) {
            saldo = cursor.getDouble(0)
        }

        return saldo
    }

}