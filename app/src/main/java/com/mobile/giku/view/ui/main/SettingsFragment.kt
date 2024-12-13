package com.mobile.giku.view.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mobile.giku.databinding.FragmentSettingsBinding
import com.mobile.giku.view.ui.auth.AuthActivity
import com.mobile.giku.viewmodel.auth.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyLanguagePreference()

        binding.cardLogout.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java)
            loginViewModel.logout()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.cardLanguages.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Indonesia", "Inggris", "Jawa", "Sunda")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pilih Bahasa")
        builder.setItems(languages) { _, which ->
            val selectedLanguage = when (which) {
                0 -> "id"
                1 -> "en"
                2 -> "jv"
                3 -> "su"
                else -> "en"
            }
            setLocale(selectedLanguage)
        }
        builder.show()
    }

    private fun applyLanguagePreference() {
        val sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "en") ?: "en"
        updateAppLocale(language)
    }

    private fun updateAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        requireContext().createConfigurationContext(config)
    }

    private fun setLocale(language: String) {
        val sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("My_Lang", language).apply()
        updateAppLocale(language)
        recreate()
    }

    private fun recreate() {
        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}