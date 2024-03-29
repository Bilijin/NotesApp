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
import com.mobolajia.notesapp.databinding.FragmentRegisterBinding
import com.mobolajia.notesapp.utils.isValidEmail
import com.mobolajia.notesapp.utils.validatePassword
import com.mobolajia.notesapp.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)
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
        binding.loginTxt.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.registerBtn.setOnClickListener {
            disableFields(true)
            if (areFieldsValid(
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString(),
                    binding.email.text.toString().trim().lowercase(),
                    binding.password.text.toString(),
                    binding.confirmPassword.text.toString()
                )
            ) {
                viewModel.createUserAccount(
                    binding.email.text.toString().trim().lowercase(),
                    binding.password.text.toString()
                )

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.registrationStatus.collect {
                        when (it) {
                            "success" -> {
                                addUserToDb()
                            }
                            "failed" -> {
                                showToastAndEnableFields("Registration Failed")
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

    private fun areFieldsValid(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (firstName.isBlank()) {
            binding.firstName.error = "First name cannot be empty"
            return false
        } else if (firstName.length < 3) {
            binding.firstName.error = "First name must have more than 3 characters"
            return false
        }

        if (lastName.isBlank()) {
            binding.lastName.error = "Last name cannot be empty"
            return false
        } else if (lastName.length < 3) {
            binding.lastName.error = "Last name must have more than 3 characters"
            return false
        }

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

        if (password.validatePassword() != "true") {
            binding.passwordLyt.error = password.validatePassword()
            return false
        }

         if (confirmPassword != password) {
            binding.confirmPasswordLyt.error = getString(R.string.confirm_password_not_match)
            return false
        }

        return true
    }

    private fun addUserToDb() {
        viewModel.updateUserDetails(
            binding.firstName.text.toString(),
            binding.lastName.text.toString()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addUserDbStatus.collect { addDb ->
                when (addDb) {
                    "success" -> {
                        showToastAndEnableFields("Success")
                        findNavController().navigate(R.id.action_registerFragment_to_notesListFragment)
                    }
                    "n/a" -> {}
                    else -> {
                        showToastAndEnableFields(addDb)
                    }
                }
            }
        }
    }


    private fun disableFields(disabled: Boolean) {
        binding.firstName.isEnabled = !disabled
        binding.lastName.isEnabled = !disabled
        binding.email.isEnabled = !disabled
        binding.password.isEnabled = !disabled
        binding.confirmPassword.isEnabled = !disabled
        binding.registerBtn.isEnabled = !disabled
        if (disabled) binding.progressIndicator.show()
        else binding.progressIndicator.hide()
    }

    private fun showToastAndEnableFields(text: String) {
        disableFields(false)
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }
}