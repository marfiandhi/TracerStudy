package id.divascion.tracerstudy.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.divascion.tracerstudy.R
import kotlinx.android.synthetic.main.item_quiz_menu.view.*

class QuizMenuAdapter(
    private val context: Context,
    private val title: Array<String>,
    private val list: ArrayList<String>,
    private val listener: (Int, String, String) -> Unit
) : RecyclerView.Adapter<QuizMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_quiz_menu,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return title.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(title[position], position, list[position], context, listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("DefaultLocale")
        fun bindItem(
            item: String,
            position: Int,
            list: String,
            context: Context,
            listener: (Int, String, String) -> Unit
        ) {
            if (list.equals("done", true)) {
                itemView.item_image_exclamation.setColorFilter(
                    ContextCompat.getColor(
                        context,
                        R.color.colorBlueSpecial
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            itemView.item_quiz_menu_text.text = item

            itemView.setOnClickListener {
                listener(position, item, list.toLowerCase())
            }
        }
    }

}