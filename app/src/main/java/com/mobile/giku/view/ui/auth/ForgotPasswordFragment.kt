package com.mobile.giku.view.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentForgotPasswordBinding
import com.mobile.giku.viewmodel.auth.ForgotPasswordViewModel
import com.mobile.giku.viewmodel.state.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            if (email.isEmpty()) {
                binding.root.showSnackbar(getString(R.string.error_empty_email))
            } else {
                viewModel.forgotPassword(email)
            }
        }

        viewModel.forgotPasswordState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Idle -> binding.progressBar.visibility = View.GONE
                is UIState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is UIState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.root.showSnackbar(getString(R.string.success_forgot_password))
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_verificationCodeFragment)
                }
                is UIState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    handleErrorSnackbar(state.message)
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun handleErrorSnackbar(message: String) {
        val userFriendlyMessage = when {
            message.contains("404") -> getString(R.string.error_user_not_found)
            message.contains("408") -> getString(R.string.error_request_timeout)
            message.contains("500") -> getString(R.string.error_server)
            else -> getString(R.string.error_unknown)
        }
        binding.root.showSnackbar(userFriendlyMessage)
    }

    private fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}