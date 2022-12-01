package com.example.tappze.util


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tappze.databinding.GridItemBinding

class RVAdapter(private val list: Array<Pair<String, String>>?, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RVAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = GridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list?.get(position)
        if (item != null) {
            holder.bind(item, itemClickListener)
        }
    }

    override fun getItemCount() = list?.size!!

    inner class MyViewHolder(private val binding: GridItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Pair<String, String>, clickListener: OnItemClickListener){
            binding.textView.text = item.first
            binding.imageView.setImageResource(selectImage(item.first))

            binding.root.setOnClickListener{
                clickListener.onItemClicked(item)
            }
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(link: Pair<String, String>)
}