package com.mobile.giku.view.adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobile.giku.databinding.ItemArticleBinding
import com.mobile.giku.model.remote.articles.Article

class ArticleAdapter(
    private val articleClickListener: ArticleClickListener
): ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, articleClickListener: ArticleClickListener) {
            binding.apply {
                ivArticle.load(article.photoUrl) {
                    crossfade(true)
                }
                tvArticleTitle.text = article.title
                tvAuthor.text = article.author
                root.setOnClickListener {
                    articleClickListener.onArticleClick(article)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleAdapter.ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article, articleClickListener)
    }
}