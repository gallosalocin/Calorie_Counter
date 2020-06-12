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

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
            val food = differ.currentList[position]
        holder.itemView.apply {
            food_name.text = food.name
            food_gram.text = food.weight.toString()
            food_cal.text = food.calorie.toString()
            food_fat.text = food.fat.toString()
            food_carb.text = food.carb.toString()
            food_prot.text = food.prot.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Food>) {
        differ.submitList(list)
    }

    private var onItemClickListener: ((Food) -> Unit)? = null

    fun setOnItemClickListener(listener: (Food) -> Unit) {
        onItemClickListener = listener
    }
}