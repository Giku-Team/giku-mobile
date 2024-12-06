package com.mobile.giku.view.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentForgotPasswordBinding
import com.mobile.giku.viewmodel.auth.ForgotPasswordViewModel
import com.mobile.giku.viewmodel.auth.SharedAuthViewModel
import com.mobile.giku.viewmodel.state.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ForgotPasswordViewModel by viewModel()
    private val sharedAuthViewModel: SharedAuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (email.isValidEmail()) {
                sharedAuthViewModel.setEmail(email)
                viewModel.forgotPassword(email)
            } else {
                Snackbar.make(view, "Invalid email address", Snackbar.LENGTH_SHORT).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.forgotPasswordState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is UIState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_verificationCodeFragment)
                }
                is UIState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(requireView(), state.message, Snackbar.LENGTH_LONG).show()
                }

                UIState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnContinue.isEnabled = true
                }
            }
        }
    }

    private fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
