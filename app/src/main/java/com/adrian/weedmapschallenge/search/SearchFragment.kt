package com.adrian.weedmapschallenge.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.adrian.weedmapschallenge.R
import com.adrian.weedmapschallenge.dagger.viewmodel.ViewModelFactory
import com.adrian.weedmapschallenge.databinding.FragmentSearchBinding
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_search_header.view.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelFactory<SearchFragmentViewModel>
    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var binding: FragmentSearchBinding

    private var searchAdapter: SearchAdapter = SearchAdapter()
    private var disposable = CompositeDisposable()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(SearchFragmentViewModel::class.java)

        binding.searchHeader.clear.setOnClickListener {
            resetSearchView()
            searchAdapter.submitData(lifecycle, PagingData.empty())
        }

        binding.searchHeader.search.setOnClickListener { submitQuery() }
        binding.searchHeader.myLocationView.setOnClickListener {
            viewModel.updateLocation()
        }

        binding.searchHeader.searchView.queryHint = requireActivity().getString(R.string.eg_food)

        binding.retryButton.setOnClickListener { searchAdapter.retry() }

        setObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        initAdapterLoadingStates()
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
                binding.searchHeader.myLocationView.apply {
                    location_text.text = requireActivity().getString(R.string.got_it)
                    location_text.setTextColor(requireActivity().getColor(android.R.color.holo_green_light))
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    requireActivity().getString(R.string.error_location),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        viewModel.errorToastLiveData.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.emptyResultsLiveData.observe(requireActivity(), Observer {
            binding.edgeCaseText.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
    }

    private fun submitQuery() {
        val query = binding.searchHeader.searchView.query
        if (query?.isNotEmpty() == true) {
            disposable.add(viewModel.searchYelp(query).subscribe { pagingData ->
                searchAdapter.submitData(lifecycle, pagingData)
            })
        } else {
            Toast.makeText(
                requireContext(),
                requireActivity().getString(R.string.error_empty),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun resetSearchView() {
        binding.searchHeader.searchView.apply {
            setQuery("", false)
            clearFocus()
        }
    }

    private fun initAdapterLoadingStates() {
        searchAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.searchRecyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    requireActivity(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


