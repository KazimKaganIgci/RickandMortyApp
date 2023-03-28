package com.kazim.rickandmorty.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Color.MAGENTA
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kazim.rickandmorty.R
import com.kazim.rickandmorty.data.Result
import com.kazim.rickandmorty.databinding.ButtonRowBinding

class ButtonAdapter(var context:Context):RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {
    private var data : ((Result) -> Unit)? =null
    private var selectedPosition = -1

    private var oldItems = ArrayList<Result>()



    class ViewHolder(val binding: ButtonRowBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ButtonRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result =oldItems[position]
        holder.binding.button.text =result.name
        var p =position





        holder.binding.button.setOnClickListener {
            data?.invoke(result)
            selectedPosition = p
            notifyDataSetChanged()
        }


                if (selectedPosition == position) {
                    holder.binding.button.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_700))
                } else {
                    holder.binding.button.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
                }








    }

    override fun getItemCount() =oldItems.size

    fun setOnActionEditListener(callback:(Result)->Unit){
        this.data =callback

    }
    fun setData(newList: List<Result>){
        oldItems.addAll(newList)
        notifyDataSetChanged()
    }




}
