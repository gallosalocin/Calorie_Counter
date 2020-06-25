package com.gallosalocin.calorie_counter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gallosalocin.calorie_counter.R
import com.gallosalocin.calorie_counter.models.Food
import kotlinx.android.synthetic.main.item_food.view.*

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private lateinit var listener: OnItemClickListener


    inner class FoodViewHolder(itemView: View, var listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(view: View?) {
            listener.setOnClickListener(adapterPosition)
        }

        override fun onLongClick(view: View?): Boolean {
            listener.setOnLongClickListener(adapterPosition)
            return true
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false), listener)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = differ.currentList[position]
        holder.itemView.apply {
            cv_food.setBackgroundColor(food.color)
            food_name.text = food.name
            food_gram.text = food.weight.toString()
            food_cal.text = food.calorie.toString()
            food_fat.text = String.format("%.1f", food.fat)
            food_carb.text = String.format("%.1f",food.carb)
            food_prot.text = String.format("%.1f",food.prot)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Food>) {
        differ.submitList(list)
    }

    fun filterList(filteredNames: ArrayList<Food>) {
        this.differ.submitList(filteredNames)
    }

    interface OnItemClickListener{
        fun setOnClickListener(position: Int)
        fun setOnLongClickListener (position: Int)
    }

    fun setOnItemClickListener (listener: OnItemClickListener){
        this.listener = listener
    }

}