package id.divascion.tracerstudy.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.divascion.tracerstudy.R
import kotlinx.android.synthetic.main.item_quiz_menu.view.*

class QuizMenuAdapter (private val context: Context, private val list: Array<String>, private val listener: (Int, String)-> Unit)
    : RecyclerView.Adapter<QuizMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_quiz_menu, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], position, listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindItem(item: String, position: Int, listener: (Int, String) -> Unit){
            itemView.item_quiz_menu_text.text = item

            itemView.setOnClickListener{
                listener(position, item)
            }
        }
    }

}