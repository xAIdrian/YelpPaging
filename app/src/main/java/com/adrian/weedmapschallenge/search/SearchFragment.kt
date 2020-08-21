package com.adrian.weedmapschallenge.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.adrian.weedmapschallenge.R
import com.adrian.weedmapschallenge.dagger.viewmodel.ViewModelFactory
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.databinding.FragmentSearchBinding
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_search_header.view.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject lateinit var factory: ViewModelFactory<SearchFragmentViewModel>
    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var binding: FragmentSearchBinding

    private var searchAdapter: SearchAdapter = SearchAdapter(COMPARATOR_DIFF)
    private var disposable = CompositeDisposable()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(SearchFragmentViewModel::class.java)

        binding.searchHeader.cancel.setOnClickListener {
            resetSearchView()
        }

        disposable.add(viewModel.searchYelp("coffee").subscribe { pagingData ->
            searchAdapter.submitData(lifecycle, pagingData)
        })

        binding.searchHeader.search.setOnClickListener {
            val query = binding.searchHeader.searchView.query
            if (query.isNotEmpty()) {
                viewModel.searchYelp(query).subscribe { pagingData ->
                    searchAdapter.submitData(lifecycle, pagingData)
                }
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
                adapter = searchAdapter
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
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
//            searchAdapter.setBusinesses(it)
            val gr = it
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

    // TODO: 8/20/20
    private fun showEmptyList(show: Boolean) {
//        if (show) {
//            binding.emptyList.visibility = View.VISIBLE
//            binding.list.visibility = View.GONE
//        } else {
//            binding.emptyList.visibility = View.GONE
//            binding.list.visibility = View.VISIBLE
//        }
    }

    companion object {
        private val COMPARATOR_DIFF = object : DiffUtil.ItemCallback<Business>() {
            override fun areItemsTheSame(oldItem: Business, newItem: Business): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Business, newItem: Business): Boolean {
                return oldItem == newItem
            }
        }
    }
}

