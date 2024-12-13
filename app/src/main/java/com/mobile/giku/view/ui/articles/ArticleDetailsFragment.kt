package com.mobile.giku.view.ui.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.mobile.giku.databinding.FragmentArticleDetailsBinding

class ArticleDetailsFragment : Fragment() {

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleTitle = arguments?.getString("articleTitle")
        val articleAuthor = arguments?.getString("articleAuthor")
        val articleDescription = arguments?.getString("articleDescription")
        val articlePhotoUrl = arguments?.getString("articlePhotoUrl")

        binding.apply {
            ivArticle.load(articlePhotoUrl) {
                crossfade(true)
                error(com.mobile.giku.R.color.md_theme_primary)
            }

            tvArticleTitle.text = articleTitle ?: "Untitled Article"
            tvArticleDescription.text = articleDescription ?: "No description available"

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}