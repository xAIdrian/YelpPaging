package com.adrian.weedmapschallenge.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.adrian.weedmapschallenge.data.Business
import com.adrian.weedmapschallenge.databinding.FragmentListItemSearchBinding
import com.bumptech.glide.Glide

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var businesses: List<Business> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = FragmentListItemSearchBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount() = businesses.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(businesses[position])
    }

    fun setBusinesses(businessList: List<Business>) {
        businesses = businessList
        notifyDataSetChanged()
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