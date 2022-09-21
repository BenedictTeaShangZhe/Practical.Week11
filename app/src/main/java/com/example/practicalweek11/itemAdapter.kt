package com.example.practicalweek11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class itemAdapter (private val itemList : List<item>,
                   private val listener: OnItemClickListener)

    : RecyclerView.Adapter<itemAdapter.MyViewHolder> () {

    inner class MyViewHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvPrice:TextView = view.findViewById(R.id.tvPrice)
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.itemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun itemClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.tvDescription.text = currentItem.productDescription
        holder.tvPrice.text = currentItem.productPrice.toString()
        //holder.imgProduct.text = currentStudent.programme
        //holder.imgProduct.setImageResource(R.drawable.currentItem.uri)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}