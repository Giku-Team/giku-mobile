package com.mobile.giku.view.adapter.child

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobile.giku.databinding.ItemChildProfileBinding
import com.mobile.giku.model.remote.child.ChildData

class ChildProfileAdapter(
    private val childClickListener: ChildClickListener
) : ListAdapter<ChildData, ChildProfileAdapter.ChildProfileViewHolder>(ChildDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildProfileViewHolder {
        val binding = ItemChildProfileBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChildProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildProfileViewHolder, position: Int) {
        val childData = getItem(position)
        holder.bind(childData, childClickListener)
    }

    class ChildProfileViewHolder(
        private val binding: ItemChildProfileBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(childData: ChildData, childClickListener: ChildClickListener) {
            binding.apply {
                tvChildName.text = childData.name
                tvChildName.text = childData.dateOfBirth
                ivChildProfile.load(childData.photoURL) {
                    crossfade(true)
                }

                root.setOnClickListener {
                    childClickListener.onChildClick(childData)
                }
            }
        }
    }
}
