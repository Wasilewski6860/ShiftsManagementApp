package com.example.newtryofappwithworkingshifts.presentation.change_shedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newtryofappwithworkingshifts.databinding.ListOfShiftsItemBinding
import com.example.newtryofappwithworkingshifts.domain.model.Day

class ShiftAdapter(
    private val context: Context,
    private val actionListener: ShiftActionListener
) : ListAdapter<Day, ShiftAdapter.DayViewHolder>(DiffCallBack), View.OnClickListener {

    class DayViewHolder(val binding: ListOfShiftsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListOfShiftsItemBinding.inflate(inflater, parent, false)

//        binding.root.setOnClickListener(this)
//        binding.favouriteBtnItem.setOnClickListener(this)
        binding.cardView.setOnClickListener(this)
//        binding.itemLayout.setOnClickListener(this)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            actionListener.onClickItem(item)
        }
        with(holder.binding) {
            nameDayItem.text = item.name
            cardView.setCardBackgroundColor(item.color)
        }
    }

    override fun onClick(v: View?) {
        var tag = v?.tag
//        var cat = v?.tag as ProductWithProductOnWarehouse
//        when (v.id) {
//            R.id.product_card ->{
//                actionListener.onClickItem(cat)
//            }
//            else -> actionListener.onClickItem(cat)
//        }
    }

    interface ShiftActionListener {
        fun onClickItem(day : Day)
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Day>() {

            override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem == newItem
            }
        }
    }

}