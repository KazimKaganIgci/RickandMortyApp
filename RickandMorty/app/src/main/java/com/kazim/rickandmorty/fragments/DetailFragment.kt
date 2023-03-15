package com.kazim.rickandmorty.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.kazim.rickandmorty.R
import com.kazim.rickandmorty.data.SingleCharacter
import com.kazim.rickandmorty.databinding.FragmentDetailBinding
import com.kazim.rickandmorty.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private lateinit var singleCharacter: SingleCharacter
    var arrayList=ArrayList<String>(emptyList())
    private lateinit var mView:View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            singleCharacter =DetailFragmentArgs.fromBundle(it).data
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsData()
        binding.fabDone.setOnClickListener {
            view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }


    }


    private fun detailsData() {
        Glide.with(this).load(singleCharacter.image).into(binding.imageView)


            var eposides =singleCharacter.episode
        if (eposides != null) {
            for (i in eposides){
                var idList = i.toString()?.split("/")
                var id =idList?.lastIndex?.let { lastindex -> idList?.get(lastindex)}
                arrayList.add(id.toString())
            }
        }

        binding.name.text=singleCharacter.name
        binding.statusText.text=singleCharacter.status
        binding.specyText.text= singleCharacter.species
        binding.genderText.text=singleCharacter.gender
        binding.originText.text=singleCharacter.origin?.name.toString()
        binding.locationText.text=singleCharacter.location?.name.toString()
        binding.episodesText.text=arrayList.toString()
        binding.createdText.text=singleCharacter.created
    }






}