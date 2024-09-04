package com.example.mindtekassesment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarouselAdapter(
    private var items: List<ListItem>,
    private val onPageChanged: (Int) -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.imageView) // Use holder.imageView
        holder.textViewTitle.text = item.title // Use holder.textViewTitle
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ListItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    class CarouselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView) // Binding ImageView
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle) // Binding TextView
    }
}
