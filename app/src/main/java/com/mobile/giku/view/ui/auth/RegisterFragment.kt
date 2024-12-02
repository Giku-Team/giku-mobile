package com.mobile.giku.view.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobile.giku.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.mobile.giku.databinding.FragmentRegisterBinding
import com.mobile.giku.viewmodel.auth.RegisterViewModel
import com.mobile.giku.viewmodel.state.UIState

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.registerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                }
                is UIState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRegister.isEnabled = false
                }
                is UIState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is UIState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    binding.root.showSnackbar(state.message)
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (validateInputs(name, email, password, confirmPassword)) {
                viewModel.register(name, email, password)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun validateInputs(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        binding.tilName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null

        if (name.isEmpty()) {
            binding.tilName.error = getString(R.string.name_is_required)
            isValid = false
        } else if (name.length < 3) {
            binding.tilName.error = getString(R.string.name_must_be_at_least_3_characters)
            isValid = false
        }

        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.email_is_required)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.enter_a_valid_email_address)
            isValid = false
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.password_is_required)
            isValid = false
        } else if (password.length < 8) {
            binding.tilPassword.error = getString(R.string.password_must_be_at_least_8_characters)
            isValid = false
        } else if (!password.matches(Regex(".*[A-Z].*"))) {
            binding.tilPassword.error =
                getString(R.string.password_must_contain_at_least_one_uppercase_letter)
            isValid = false
        } else if (!password.matches(Regex(".*[a-z].*"))) {
            binding.tilPassword.error =
                getString(R.string.password_must_contain_at_least_one_lowercase_letter)
            isValid = false
        } else if (!password.matches(Regex(".*\\d.*"))) {
            binding.tilPassword.error = getString(R.string.password_must_contain_at_least_one_digit)
            isValid = false
        } else if (!password.matches(Regex(".*[@#\$%^&+=].*"))) {
            binding.tilPassword.error =
                getString(R.string.password_must_contain_at_least_one_special_character)
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = getString(R.string.confirm_your_password)
            isValid = false
        } else if (confirmPassword != password) {
            binding.tilConfirmPassword.error = getString(R.string.passwords_do_not_match)
            isValid = false
        }

        return isValid
    }

    private fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}