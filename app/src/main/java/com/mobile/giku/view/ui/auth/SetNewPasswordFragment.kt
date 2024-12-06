package com.mobile.giku.view.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentSetNewPasswordBinding
import com.mobile.giku.viewmodel.auth.SetNewPasswordViewModel
import com.mobile.giku.viewmodel.auth.SharedAuthViewModel
import com.mobile.giku.viewmodel.state.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SetNewPasswordFragment : Fragment() {

    private var _binding: FragmentSetNewPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SetNewPasswordViewModel by viewModel()
    private val sharedAuthViewModel: SharedAuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedAuthViewModel.email.observe(viewLifecycleOwner) { email ->
            binding.btnConfirm.setOnClickListener {
                val newPassword = binding.etPassword.text.toString()
                val confirmNewPassword = binding.etConfirmPassword.text.toString()

                if (validateInputs(newPassword, confirmNewPassword)) {
                    if (email != null) {
                        viewModel.setNewPassword(email, newPassword)
                    }
                }
            }
        }

        observeViewModel()
    }

    private fun validateInputs(newPassword: String, confirmNewPassword: String): Boolean {
        var isValid = true
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null

        when {
            newPassword.isEmpty() -> {
                binding.tilPassword.error = getString(R.string.password_is_required)
                isValid = false
            }
            newPassword.length < 8 -> {
                binding.tilPassword.error = getString(R.string.password_must_be_at_least_8_characters)
                isValid = false
            }
            !newPassword.matches(Regex(".*[A-Z].*")) -> {
                binding.tilPassword.error =
                    getString(R.string.password_must_contain_at_least_one_uppercase_letter)
                isValid = false
            }
            !newPassword.matches(Regex(".*[a-z].*")) -> {
                binding.tilPassword.error =
                    getString(R.string.password_must_contain_at_least_one_lowercase_letter)
                isValid = false
            }
            !newPassword.matches(Regex(".*\\d.*")) -> {
                binding.tilPassword.error = getString(R.string.password_must_contain_at_least_one_digit)
                isValid = false
            }
            !newPassword.matches(Regex(".*[@#\$%^&+=].*")) -> {
                binding.tilPassword.error =
                    getString(R.string.password_must_contain_at_least_one_special_character)
                isValid = false
            }
        }

        if (confirmNewPassword.isEmpty()) {
            binding.tilConfirmPassword.error = getString(R.string.confirm_your_password)
            isValid = false
        } else if (confirmNewPassword != newPassword) {
            binding.tilConfirmPassword.error = getString(R.string.passwords_do_not_match)
            isValid = false
        }

        return isValid
    }

    private fun observeViewModel() {
        viewModel.setNewPasswordState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                }
                is UIState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UIState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.password_reset_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_setNewPasswordFragment_to_loginFragment)
                }

                is UIState.Error -> {
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
