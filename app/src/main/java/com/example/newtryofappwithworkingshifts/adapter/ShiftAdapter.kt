package com.example.newtryofappwithworkingshifts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newtryofappwithworkingshifts.R
import com.example.newtryofappwithworkingshifts.data.WorkShift
import com.example.newtryofappwithworkingshifts.databinding.ListOfShiftsItemBinding


class ShiftAdapter : ListAdapter<WorkShift, ShiftAdapter.Holder>(Comparator()) {

    private var onClickListener: OnClickListener? = null

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ListOfShiftsItemBinding.bind(view)

        fun bind(shift: WorkShift)= with(binding){
            shiftNameListView.text = shift.name
            cardView.setCardBackgroundColor(shift.color)
        }
    }

    class Comparator : DiffUtil.ItemCallback<WorkShift>(){
        override fun areItemsTheSame(oldItem: WorkShift, newItem: WorkShift): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(oldItem: WorkShift, newItem: WorkShift): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_of_shifts_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, getItem(position) )
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: WorkShift)
    }
}