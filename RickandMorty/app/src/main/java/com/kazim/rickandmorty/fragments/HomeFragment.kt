package com.kazim.rickandmorty.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kazim.rickandmorty.adapter.ButtonAdapter
import com.kazim.rickandmorty.adapter.CharacterAdapter
import com.kazim.rickandmorty.data.Result
import com.kazim.rickandmorty.data.SingleCharacter
import com.kazim.rickandmorty.databinding.FragmentHomeBinding
import com.kazim.rickandmorty.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var buttonAdapter: ButtonAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var characterAdapter:CharacterAdapter ?=null
    private lateinit var binding:FragmentHomeBinding
    private var a =2
    var pageNumber =1
    var isLoading=false
    var limit=8
    var singleList=ArrayList<SingleCharacter>(emptyList())




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAdapter =ButtonAdapter(requireContext())
        characterAdapter= CharacterAdapter()
        setupRecyclerview()
        setupRecyclerviewC()
        getSeries()
        getAdapterData()
        refreshData()



        viewModel.getCharacterLiveData.observe(viewLifecycleOwner, Observer {
            if (!singleList.contains(it)){
                singleList.add(it)
            }
            if (singleList.isEmpty()){
                characterAdapter?.setData(emptyList())
            }

        })

            }

    private fun refreshData() {

        lifecycleScope.launch {
            viewModel.getLocationLiveData.observe(viewLifecycleOwner, Observer {
                //buttonAdapter.differ.submitList(emptyList())
                buttonAdapter.setData(it)
                buttonAdapter.notifyDataSetChanged()
            })

            if (a<8){
                a++
            }

        }
    }


    private fun firstlyCallList(){
        lifecycleScope.launch {
            delay(300)
            characterAdapter?.clearData()
            characterAdapter?.setData(singleList)

        }
    }

    private fun callList(){
        lifecycleScope.launch {
            delay(1500)
           //characterAdapter?.clearData()
            if (singleList.isEmpty()){
            }else{
                characterAdapter?.setData(singleList)

            }

        }
    }

    private fun getSeries() {
        viewModel.getSeriesLiveData.observe(viewLifecycleOwner, Observer {
            buttonAdapter.setData(it)

            var list =it[0].residents
            if (list != null) {
                singleList.clear()
                var a =0
                lifecycleScope.launch {
                    while(a <list.size){
                        delay(40)
                        var idList = list[a]?.split("/")
                        var id =idList?.lastIndex?.let { lastindex -> idList?.get(lastindex)}
                        viewModel.getCharacterData(id.toString())
                        a++

                    }
                    firstlyCallList()

                }

            }

        }) }





    private fun setupRecyclerview() {
        binding.overRecyclerView.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
            adapter =buttonAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dx >0){
                        val visibleItemCount= (layoutManager as LinearLayoutManager).childCount
                        val pastVisibleItem= (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        val total =buttonAdapter.itemCount
                        if (!isLoading){
                            if (visibleItemCount+pastVisibleItem>=total){
                                pageNumber++
                                viewModel.getLocationData(pageNumber)
                                getLoad()
                            }
                        }

                    }else{

                    }
                    super.onScrolled(recyclerView, dx, dy)





                }
            })



        }
    }

    private fun getLoad() {
        lifecycleScope.launch {
        isLoading=true
        binding.progressDialog.visibility=View.VISIBLE

            delay(1500)
            binding.progressDialog.visibility=View.GONE

            isLoading=false
        }
    }

    private fun setupRecyclerviewC() {
        binding.characterRecyclerView.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter =characterAdapter


        }
    }


    private fun getAdapterData() {
            buttonAdapter.setOnActionEditListener{
                lifecycleScope.launch {
                    buttonInfo(it)



                }
            }
    }

    private fun buttonInfo(result:Result){
        lifecycleScope.launch {

            var list: List<String> = result.residents as List<String>

            singleList.clear()
            var a =0
            characterAdapter?.setData(emptyList())
            while(a <list.size){
                delay(50)
                var idList = list[a]?.split("/")
                var id =idList?.lastIndex?.let { lastindex -> idList?.get(lastindex)}
                viewModel.getCharacterData(id.toString())
                a++


            }
            callList()

        }


    }



}
