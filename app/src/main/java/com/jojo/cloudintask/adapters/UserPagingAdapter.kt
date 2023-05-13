package com.jojo.cloudintask.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.jojo.cloudintask.R
import com.jojo.cloudintask.models.Result
import com.jojo.cloudintask.ui.DetailsActivity

class UserPagingAdapter(private val context: Context) : PagingDataAdapter<Result, UserPagingAdapter.QuoteViewHolder>(
    COMPARATOR
) {
//     private val application : Application = mainActivity.application
    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById (R.id.name)
        val email: TextView = itemView.findViewById(R.id.email)
        val phone: TextView = itemView.findViewById(R.id.phone_number)
        val img: ImageView = itemView.findViewById(R.id.imageView)
        val cardDetails: CardView = itemView.findViewById (R.id.card_details)

}

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.name.text = item.name.title.trim()+" "+item.name.first.trim()+" "+item.name.last.trim()
            holder.email.text = item.email
            holder.phone.text = item.phone
            Glide.with(context).load(item.picture.large).into(holder.img)

            holder.cardDetails.setOnClickListener {
                val gson = Gson()
                val jsonString = gson.toJson(item)
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("myModel", jsonString)
                context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_quote_layout, parent, false)
        return QuoteViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}


















