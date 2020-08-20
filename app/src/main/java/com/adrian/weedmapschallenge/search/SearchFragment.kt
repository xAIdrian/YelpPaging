package com.adrian.weedmapschallenge.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adrian.weedmapschallenge.R
import com.adrian.weedmapschallenge.dagger.viewmodel.ViewModelFactory
import com.adrian.weedmapschallenge.databinding.FragmentSearchBinding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.layout_search_header.view.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory<SearchFragmentViewModel>
    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var binding: FragmentSearchBinding

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
                binding.searchHeader.searchView.queryHint(requireActivity().getString(R.string.error_empty))
            }
        }
        binding.searchHeader.myLocationView.setOnClickListener {
            viewModel.updateLocation()
        }

        binding.searchHeader.searchView.queryHint = "eg. Food"
        binding.searchHeader.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.e("text submit", p0!!)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.e("text change", p0!!)
                return false
            }
        })

        viewModel.successfulLocationUpdateLiveData.observe(requireActivity(), Observer {
            binding.searchHeader.myLocationView.location_text.text = requireActivity().getString(R.string.got_it)
            binding.searchHeader.myLocationView.location_text.setTextColor(requireActivity().getColor(android.R.color.holo_green_light))
        })
        viewModel.businessesLiveData.observe(requireActivity(), Observer {
            // TODO: 8/19/20 update list view
        })
        viewModel.errorToastLiveData.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    private fun resetSearchView() {
        binding.searchHeader.searchView.setQuery("", false);
        binding.searchHeader.searchView.clearFocus();
    }
}

private operator fun CharSequence?.invoke(string: String) = string as CharSequence
