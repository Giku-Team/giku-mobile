package com.mobile.giku.view.adapter.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mobile.giku.model.remote.articles.Article

class ArticleDiffCallback: DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}