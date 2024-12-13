package com.mobile.giku.view.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentArticleBinding
import com.mobile.giku.model.remote.articles.Article
import com.mobile.giku.view.adapter.adapter.ArticleAdapter
import com.mobile.giku.view.adapter.adapter.ArticleClickListener
import com.mobile.giku.viewmodel.articles.ArticlesViewModel
import com.mobile.giku.viewmodel.state.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment(), ArticleClickListener {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticlesViewModel by viewModel()
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupToolbar()
        observeViewModel()

        viewModel.fetchArticles()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter(this)
        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ArticleFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.progressBar.visibility = View.GONE
                }
                is UIState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }

        viewModel.articles.observe(viewLifecycleOwner) { response ->
            response?.articles?.let { articles ->
                adapter.submitList(articles)
                if (articles.isEmpty()) {
                    Toast.makeText(requireContext(), "No articles available", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(requireContext(), "No articles available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onArticleClick(article: Article) {
        // Navigate to Article Details Fragment using Bundle
        val bundle = bundleOf(
            "articleId" to article.id,
            "articleTitle" to article.title,
            "articleAuthor" to article.author,
            "articleDescription" to article.description,
            "articlePhotoUrl" to article.photoUrl,
            "articleDate" to article.date
        )
        findNavController().navigate(R.id.action_articleFragment_to_articleDetailsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}