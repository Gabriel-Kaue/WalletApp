package com.example.walletapp

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.walletapp.model.Transaction

class TransactionAdapter (private val context: Context) : RecyclerView.Adapter<TransactionAdapter.TransacaoViewHolder>() {

    private var transacoes: List<Transaction> = listOf()

    // ViewHolder "segura" as views de cada item
    inner class TransacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTipo: ImageView = itemView.findViewById(R.id.ivTipoTransacao)
        val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
        val tvData: TextView = itemView.findViewById(R.id.tvData)
        val tvValor: TextView = itemView.findViewById(R.id.tvValor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacaoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transacao, parent, false)
        return TransacaoViewHolder(view)
    }

    override fun getItemCount(): Int = transacoes.size

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        val transacao = transacoes[position]

        holder.tvDescricao.text = transacao.descricao

        // Formata a data
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.tvData.text = dateFormat.format(Date(transacao.timestamp))

        // Configura valor e ícone com base no tipo
        if (transacao.tipo == TipoTransacao.CREDITO) {
            holder.tvValor.text = "+ R$ %.2f".format(transacao.valor)
            holder.tvValor.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
            holder.ivTipo.setImageResource(R.drawable.ic_credit)
        } else {
            holder.tvValor.text = "- R$ %.2f".format(transacao.valor)
            holder.tvValor.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            holder.ivTipo.setImageResource(R.drawable.ic_debit)
        }
    }

    // Função para o adapter receber e atualizar a lista de transações
    fun updateList(novaLista: List<Transacao>) {
        this.transacoes = novaLista
        notifyDataSetChanged() // Notifica o RecyclerView que os dados mudaram
    }

}