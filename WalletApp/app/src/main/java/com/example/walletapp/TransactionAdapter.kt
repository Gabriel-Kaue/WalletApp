package com.example.walletapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.walletapp.model.TipoTransaction
import com.example.walletapp.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter (private val context: Context) : RecyclerView.Adapter<TransactionAdapter.TransacaoViewHolder>() {
    private var transacoes: List<Transaction> = listOf()

    inner class TransacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTipo: ImageView = itemView.findViewById(R.id.imageViewTipoTransacao)
        val tvDescricao: TextView = itemView.findViewById(R.id.textViewDescricao)
        val tvValor: TextView = itemView.findViewById(R.id.textViewValor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacaoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transacao, parent, false)
        return TransacaoViewHolder(view)
    }

    override fun getItemCount(): Int = transacoes.size

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        val transacao = transacoes[position]

        holder.tvDescricao.text = transacao.descricao

        if (transacao.tipo == TipoTransaction.CREDITO) {
            holder.tvValor.text = "+ R$ %.2f".format(transacao.valor)
            holder.tvValor.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
            holder.ivTipo.setImageResource(R.drawable.ic_credit)
        } else {
            holder.tvValor.text = "- R$ %.2f".format(transacao.valor)
            holder.tvValor.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            holder.ivTipo.setImageResource(R.drawable.ic_debit)
        }
    }

    fun updateList(novaLista: List<Transaction>) {
        this.transacoes = novaLista
        notifyDataSetChanged()
    }
}