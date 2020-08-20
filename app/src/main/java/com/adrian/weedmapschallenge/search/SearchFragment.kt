package com.adrian.weedmapschallenge.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.adrian.weedmapschallenge.R
import com.adrian.weedmapschallenge.dagger.viewmodel.ViewModelFactory
import com.adrian.weedmapschallenge.databinding.FragmentSearchBinding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.layout_search_header.view.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject lateinit var factory: ViewModelFactory<SearchFragmentViewModel>
    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var binding: FragmentSearchBinding
    private var adapter: SearchAdapter ?= null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(SearchFragmentViewModel::class.java)

        binding.searchHeader.cancel.setOnClickListener {
            resetSearchView()
        }
        binding.searchHeader.search.setOnClickListener {
            val query = binding.searchHeader.searchView.query
            if (query.isNotEmpty()) {
                viewModel.getSearchResults(query)
                resetSearchView()
            } else {
                Toast.makeText(requireContext(), requireActivity().getString(R.string.error_empty), Toast.LENGTH_SHORT).show()
            }
        }
        binding.searchHeader.myLocationView.setOnClickListener {
            viewModel.updateLocation()
        }

        binding.searchHeader.searchView.queryHint = requireActivity().getString(R.string.eg_food)
        setObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.searchRecyclerview.apply {
            with(view) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = SearchAdapter()
            }
        }
        return binding.root
    }

    private fun setObservers() {
        viewModel.successfulLocationUpdateLiveData.observe(requireActivity(), Observer {
            if (it) {
                binding.searchHeader.myLocationView.location_text.text = requireActivity().getString(R.string.got_it)
                binding.searchHeader.myLocationView.location_text.setTextColor(requireActivity().getColor(android.R.color.holo_green_light))
            } else {
                Toast.makeText(requireContext(), requireActivity().getString(R.string.error_location), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.businessesLiveData.observe(requireActivity(), Observer {
            adapter?.setBusinesses(it)
        })
        viewModel.errorToastLiveData.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.emptyResultsLiveData.observe(requireActivity(), Observer {
            binding.edgeCaseText.visibility = if (it) View.VISIBLE else  View.INVISIBLE
        })
    }

    private fun resetSearchView() {
        binding.searchHeader.searchView.setQuery("", false);
        binding.searchHeader.searchView.clearFocus();
    }
}

