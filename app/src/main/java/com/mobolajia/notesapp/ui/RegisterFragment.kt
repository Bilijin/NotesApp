package com.mobolajia.notesapp.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
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
            if (areFieldsValid()) register(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
            else disableFields(false)
        }
    }

    private fun areFieldsValid(): Boolean {

        if (binding.firstName.text.isNullOrBlank()) {
            binding.firstName.error = "First name cannot be empty"
            return false
        } else if (binding.firstName.text.toString().length < 3) {
            binding.firstName.error = "First name must have more than 3 characters"
            return false
        }

        if (binding.lastName.text.isNullOrBlank()) {
            binding.lastName.error = "Last name cannot be empty"
            return false
        } else if (binding.lastName.text.toString().length < 3) {
            binding.lastName.error = "Last name must have more than 3 characters"
            return false
        }

        if (binding.email.text.isNullOrBlank()) {
            binding.email.error = "Email cannot be empty"
            return false
        } else if (binding.email.length() < 7) {
            binding.email.error = "Email is too short"
            return false
        } else if (!isValidEmail(binding.email.text.toString())) {
            binding.email.error = "Please enter a valid email"
            return false
        }

        if (binding.password.text.isNullOrBlank()) {
            binding.passwordLyt.error = "Password cannot be blank"
            return false
        } else if (binding.password.text.toString().length < 6) {
            binding.passwordLyt.error = "Password must have at least 6 characters"
            return false
        }

        if (binding.confirmPassword.text.isNullOrBlank()) {
            binding.confirmPasswordLyt.error = "Confirm password cannot be blank"
            return false
        } else if (binding.confirmPassword.text != binding.password.text) {
            binding.confirmPasswordLyt.error = "Confirm password must match password"
            return false
        }

        return true
    }

    private fun disableFields(disabled: Boolean) {
        binding.email.isEnabled = !disabled
        binding.firstName.isEnabled = !disabled
        binding.lastName.isEnabled = !disabled
        binding.email.isEnabled = !disabled
        binding.password.isEnabled = !disabled
        binding.confirmPassword.isEnabled = !disabled
        binding.registerBtn.isEnabled = !disabled
        if (disabled) binding.progressIndicator.show()
        else binding.progressIndicator.hide()
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                //do registration successful
            } else {
                disableFields(false)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}