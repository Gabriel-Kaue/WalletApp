package com.example.walletapp

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletapp.data.TransactionDAO
import com.example.walletapp.model.TipoTransaction
import com.example.walletapp.model.Transaction

class Extrato : AppCompatActivity() {

    private lateinit var dao: TransactionDAO
    private lateinit var adapter: TransactionAdapter
    private var listaCompleta: List<Transaction> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extrato)

        dao = TransactionDAO(this)
        adapter = TransactionAdapter(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTransacoes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val radioGroupFiltro = findViewById<RadioGroup>(R.id.radioGroupFiltro)
        radioGroupFiltro.setOnCheckedChangeListener { group, checkedId ->
            filtrarLista()
        }

        val btnVoltar = findViewById<Button>(R.id.buttonVoltar)
        btnVoltar.setOnClickListener {
            voltar()
        }
    }

    override fun onResume() {
        super.onResume()
        carregarDados()
    }

    private fun voltar() {
        finish()
    }

    private fun carregarDados() {
        val textViewSaldo = findViewById<TextView>(R.id.tvSaldo)
        val saldo = dao.getSaldo()
        textViewSaldo.text = "R$ %.2f".format(saldo)

        listaCompleta = dao.getAllTransactions()
        filtrarLista()
    }

    private fun filtrarLista() {
        val radioGroupFiltro = findViewById<RadioGroup>(R.id.radioGroupFiltro)
        val filtroSelecionadoId = radioGroupFiltro.checkedRadioButtonId

        val listaFiltrada = when (filtroSelecionadoId) {
            R.id.rbCreditos -> {
                listaCompleta.filter { it.tipo == TipoTransaction.CREDITO }
            }
            R.id.rbDebitos -> {
                listaCompleta.filter { it.tipo == TipoTransaction.DEBITO }
            }
            else -> { // R.id.rbTodas
                listaCompleta
            }
        }
        adapter.updateList(listaFiltrada)
    }

}