package com.example.mindtekassesment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListAdapter(
    private var items: List<ListItem>
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.imageView)
        holder.textViewTitle.text = item.title
        holder.textViewType.text = item.type
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ListItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val textViewType: TextView = view.findViewById(R.id.textViewType)
    }
}
