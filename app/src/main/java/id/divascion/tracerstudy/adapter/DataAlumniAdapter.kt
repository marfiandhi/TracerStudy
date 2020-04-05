package id.divascion.tracerstudy.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.divascion.tracerstudy.R
import id.divascion.tracerstudy.data.model.AlumniList
import kotlinx.android.synthetic.main.item_data_alumni.view.*

class DataAlumniAdapter(
    private val context: Context,
    private val uid: String,
    private val list: List<AlumniList>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<DataAlumniAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_data_alumni,
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position], uid, context, listener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("DefaultLocale", "SetTextI18n")
        fun bindItem(
            list: AlumniList,
            uid: String,
            context: Context,
            listener: (String) -> Unit
        ) {
            @Suppress("DEPRECATION")
            if (list.uid == uid) {
                itemView.item_alumni_image_view.setCardBackgroundColor(context.resources.getColor(R.color.colorMaroon))
                itemView.item_alumni_name.setTextColor(context.resources.getColor(R.color.colorLightGreen))
                itemView.item_alumni_major.setTextColor(context.resources.getColor(R.color.colorLightGreen))
                itemView.item_alumni_entrance.setTextColor(context.resources.getColor(R.color.colorLightGreen))
            } else {
                itemView.item_alumni_image_view.setCardBackgroundColor(context.resources.getColor(R.color.colorWhiteTransparent))
                itemView.item_alumni_name.setTextColor(context.resources.getColor(R.color.colorWhite))
                itemView.item_alumni_major.setTextColor(context.resources.getColor(R.color.colorWhite))
                itemView.item_alumni_entrance.setTextColor(context.resources.getColor(R.color.colorWhite))
            }
            itemView.item_alumni_name.text = list.name
            itemView.item_alumni_major.text = list.major
            itemView.item_alumni_entrance.text = "${list.entrance} - ${list.graduate}"
            val image = if (list.gender.equals("perempuan", true)) {
                R.drawable.women
            } else {
                R.drawable.men
            }
            Picasso.get().load(image)
                .into(itemView.item_alumni_pic)

            itemView.setOnClickListener {
                listener(list.uid)
            }
        }
    }

}