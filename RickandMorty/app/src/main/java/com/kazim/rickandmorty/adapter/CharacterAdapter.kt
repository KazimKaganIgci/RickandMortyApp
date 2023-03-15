package com.kazim.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kazim.rickandmorty.R
import com.kazim.rickandmorty.data.Result
import com.kazim.rickandmorty.data.SingleCharacter
import com.kazim.rickandmorty.databinding.CharacterRowBinding
import com.kazim.rickandmorty.fragments.HomeFragmentDirections

class CharacterAdapter:RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    private var list= mutableListOf<SingleCharacter>()




    class ViewHolder(val binding: CharacterRowBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CharacterRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =list[position]
        Glide.with(holder.itemView).load(data.image).into(holder.binding.imageView)
        holder.binding.nameText.text =data.name
        if (data.gender.toString() =="Female"){
            holder.binding.genderView.setImageResource(R.drawable.female);

        }
        else if (data.gender.toString() =="Male"){
            holder.binding.genderView.setImageResource(R.drawable.male);

        }
        else if(data.gender.toString() =="Genderless"){
            holder.binding.genderView.setImageResource(R.drawable.genderless);

        }
        else{
            holder.binding.genderView.setImageResource(R.drawable.unknown);

        }

        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(data))
        }

    }

    override fun getItemCount() =list.size

    fun setData(data:List<SingleCharacter>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
    fun clearData(){
        list.clear()
        notifyDataSetChanged()
    }
}