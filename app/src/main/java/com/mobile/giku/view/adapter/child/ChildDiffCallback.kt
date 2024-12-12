package com.mobile.giku.view.adapter.child

import androidx.recyclerview.widget.DiffUtil
import com.mobile.giku.model.remote.child.ChildData

class ChildDiffCallback: DiffUtil.ItemCallback<ChildData>() {
    override fun areItemsTheSame(oldItem: ChildData, newItem: ChildData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChildData, newItem: ChildData): Boolean {
        return oldItem == newItem
    }
}