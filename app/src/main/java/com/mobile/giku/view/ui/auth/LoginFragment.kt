package com.mobile.giku.view.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mobile.giku.R
import com.mobile.giku.databinding.FragmentLoginBinding
import com.mobile.giku.view.ui.main.MainActivity
import com.mobile.giku.viewmodel.auth.LoginViewModel
import com.mobile.giku.viewmodel.state.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            handleUIState(state)
        }

        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun setupListeners() {
        binding.etEmail.doAfterTextChanged { binding.tilEmail.error = null }
        binding.etPassword.doAfterTextChanged { binding.tilPassword.error = null }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            if (validateInputs(email, password)) {
                viewModel.login(email, password)
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun handleUIState(state: UIState) {
        when (state) {
            is UIState.Idle -> {
                toggleLoading(false)
            }
            is UIState.Loading -> {
                toggleLoading(true)
            }
            is UIState.Success -> {
                toggleLoading(false)
                Snackbar.make(binding.root, getString(R.string.login_success), Snackbar.LENGTH_LONG).show()
            }
            is UIState.Error -> {
                toggleLoading(false)
                binding.root.showSnackbar(state.message)
            }
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
    }

    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = true

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
            binding.tilPassword.error =
                getString(R.string.password_must_contain_at_least_one_digit)
            isValid = false
        } else if (!password.matches(Regex(".*[@#\$%^&+=].*"))) {
            binding.tilPassword.error =
                getString(R.string.password_must_contain_at_least_one_special_character)
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
