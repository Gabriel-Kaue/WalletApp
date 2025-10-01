package com.example.walletapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.walletapp.data.TransactionDAO
import com.example.walletapp.model.TipoTransaction
import com.example.walletapp.model.Transaction

class CadrastraOperacoes : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var editTextDescricao: EditText
    private lateinit var editTextValor: EditText
    private lateinit var buttonSalvar: Button
    private lateinit var dao: TransactionDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadrastra_operacoes)

        radioGroup = findViewById<RadioGroup>(R.id.radioGroupTipo)
        editTextDescricao = findViewById<EditText>(R.id.editTextDescricao)
        editTextValor = findViewById<EditText>(R.id.editTextValor)
        buttonSalvar = findViewById<Button>(R.id.buttonSalvar)
        buttonSalvar.setOnClickListener {
            salvarTransacao()
        }

        dao = TransactionDAO(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun salvarTransacao(){
        val descricao = editTextDescricao.text.toString()
        val valorString = editTextValor.text.toString()

        if (descricao.isEmpty() || valorString.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val tipoSelecionado = radioGroup.checkedRadioButtonId
        var tipo: TipoTransaction

        if (tipoSelecionado == R.id.radioButtonCredito) {
            tipo = TipoTransaction.CREDITO
        } else {
            tipo = TipoTransaction.DEBITO
        }

        val valor = valorString.toDouble()

        val novaTransaction = Transaction(
            tipo = tipo,
            valor = valor,
            descricao = descricao
        )

        dao.addTransaction(novaTransaction)

        Toast.makeText(this, "Transação salva com sucesso", Toast.LENGTH_SHORT).show()
        finish()

    }

}