package com.example.dropchallenge.ui.list.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import com.bumptech.glide.Glide
import com.example.dropchallenge.R

import kotlinx.android.synthetic.main.beer_list_item.view.*

class BeerListRecyclerViewAdapter(
    values: List<BeerListItemEntity>
) : RecyclerView.Adapter<BeerListRecyclerViewAdapter.ViewHolder>() {
    private val internalList:ArrayList<BeerListItemEntity> = ArrayList(values)
    private var callback: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.beer_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = internalList[position]
        holder.idView.text = item.name
        holder.abvView.text = item.abv.toString()
        Glide.with(holder.imageView).load(item.imageUrl).into(holder.imageView)
        holder.beerTypeView.text = item.beerType.toString()
        holder.parentView.setOnClickListener {
            callback?.onClick(item)
        }
    }

    override fun getItemCount(): Int = internalList.size

    fun setItems(values: List<BeerListItemEntity>){
        internalList.clear()
        internalList.addAll(values)
        notifyDataSetChanged()
    }

    fun setOnClickListner(callback: OnClickListener) {
        this.callback = callback
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parentView = view
        val idView: TextView = view.beerName
        val abvView: TextView = view.abv
        val imageView:AppCompatImageView = view.beerImage
        val beerTypeView:TextView = view.beerType
    }

    interface OnClickListener{
        fun onClick(item:BeerListItemEntity)
    }
}