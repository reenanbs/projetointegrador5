package android.biker.com.myapplication

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.itemlist_historicos.view.*

/**
 * Created by VINTTAGE on 26/03/2018.
 */
class MyAdapter constructor(val context: Context, private val historicos: ArrayList<Historic>,
                            val clickListener:(Historic)->Unit) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.itemlist_historicos, parent, false)
        val vh = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        historicos.let{
            var historico = historicos[position]
            holder.itemView.tvDataAluguel.text = historico.dataAluguel
            holder.itemView.tvPercurso.text = historico.percurso
            holder.itemView.tvHorarioInicio.text = historico.horarioInicio
            holder.itemView.tvHorarioEntrega.text = historico.horarioEntrega
            holder.itemView.setOnClickListener { clickListener(historicos[position]) }


        }
    }

    override fun getItemCount(): Int {

        return historicos.size
    }

}