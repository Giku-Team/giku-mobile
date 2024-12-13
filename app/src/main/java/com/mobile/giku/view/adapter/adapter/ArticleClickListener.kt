package com.mobile.giku.view.adapter.adapter

import com.mobile.giku.model.remote.articles.Article

interface ArticleClickListener {
    fun onArticleClick(article: Article)
}