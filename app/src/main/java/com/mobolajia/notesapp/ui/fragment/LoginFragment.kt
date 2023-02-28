package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.FragmentLoginBinding
import com.mobolajia.notesapp.utils.isValidEmail
import com.mobolajia.notesapp.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.progressIndicator.hide()
        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.registerTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.loginBtn.setOnClickListener {
            disableFields(true)
            if (areFieldsValid(
                    binding.email.text.toString().trim().lowercase(),
                    binding.password.text.toString().trim()
                )
            ) {
                viewModel.loginUser(
                    binding.email.text.toString().trim().lowercase(),
                    binding.password.text.toString().trim()
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.loginStatus.collect {
                        when (it) {
                            "success" -> {
                                showToastAndEnableFields(getString(R.string.login_success))
                                findNavController().navigate(R.id.action_loginFragment_to_notesListFragment)
                            }
                            "failed" -> {
                                showToastAndEnableFields(getString(R.string.login_failed))
                            }
                            "n/a" -> {}
                            else -> {
                                showToastAndEnableFields(it)
                            }
                        }
                    }
                }
            } else disableFields(false)
        }
    }

    private fun areFieldsValid(email: String, password: String): Boolean {
        if (email.isBlank()) {
            binding.email.error = getString(R.string.email_blank)
            return false
        } else if (email.length < 7) {
            binding.email.error = getString(R.string.email_short)
            return false
        } else if (!email.isValidEmail()) {
            binding.email.error = getString(R.string.enter_a_valid_email)
            return false
        }

        if (password.isBlank()) {
            binding.passwordLyt.error = getString(R.string.password_blank)
            return false
        } else if (password.length < 6) {
            binding.passwordLyt.error = getString(R.string.password_invalid)
            return false
        }

        return true
    }

    private fun disableFields(disabled: Boolean) {
        binding.email.isEnabled = !disabled
        binding.email.isEnabled = !disabled
        binding.password.isEnabled = !disabled
        binding.loginBtn.isEnabled = !disabled
        if (disabled) binding.progressIndicator.show()
        else binding.progressIndicator.hide()
    }

    private fun showToastAndEnableFields(text : String){
        disableFields(false)
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }
}