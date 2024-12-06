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
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.mobile.giku.databinding.FragmentVerificationCodeBinding
import com.mobile.giku.viewmodel.auth.SharedAuthViewModel
import com.mobile.giku.viewmodel.auth.VerificationCodeViewModel
import com.mobile.giku.viewmodel.state.UIState

class VerificationCodeFragment : Fragment() {

    private var _binding: FragmentVerificationCodeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VerificationCodeViewModel by viewModel()
    private val sharedAuthViewModel: SharedAuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerificationCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            val verificationCode = binding.etPin.text.toString()
            val email = sharedAuthViewModel.email.value

            if (verificationCode.isNotBlank() && !email.isNullOrBlank()) {
                viewModel.verificationCode(email, verificationCode)
            } else {
                Snackbar.make(view, "Invalid input", Snackbar.LENGTH_SHORT).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.verificationCodeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                }
                is UIState.Loading -> binding.progressBar.visibility = View.VISIBLE
                is UIState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(requireView(), "Verification successful", Snackbar.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_verificationCodeFragment_to_setNewPasswordFragment)
                }
                is UIState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(requireView(), state.message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
