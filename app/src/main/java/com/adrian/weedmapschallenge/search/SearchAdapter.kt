package com.adrian.weedmapschallenge.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.databinding.FragmentListItemSearchBinding
import com.bumptech.glide.Glide

class SearchAdapter(
    diffCallback: DiffUtil.ItemCallback<Business>
) : PagingDataAdapter<Business, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentListItemSearchBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as ViewHolder).bind(it)
        }
    }

    inner class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val localBinding = binding as FragmentListItemSearchBinding

        fun bind(business: Business) {
            Glide.with(localBinding.root.context)
                .load(business.imageUrl)
                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
                .into(localBinding.businessImage)
            localBinding.businessTitle.text = business.name
            localBinding.businessReview.text = business.bestReview?.text ?: "No Reviews"
        }
    }
}