package com.mamafua.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamafua.app.databinding.ServiceLoadStateFooterBinding

class ServiceLoadStateAdapter(private  val retry:()->Unit) : LoadStateAdapter<ServiceLoadStateAdapter.LoadStateViewholder>() {

    override fun onBindViewHolder(holder: LoadStateViewholder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewholder {
        val binding=ServiceLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewholder(binding)
    }

    inner class LoadStateViewholder(private val binding: ServiceLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonretry.setOnClickListener{
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            binding.apply {
                pgbar.isVisible= loadState is LoadState.Loading
                buttonretry.isVisible= loadState !is LoadState.Loading
                textViewError.isVisible= loadState !is LoadState.Loading

            }

        }

    }
}