package com.mobolajia.notesapp.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.FragmentProfileBinding
import com.mobolajia.notesapp.utils.showMessageDialog
import com.mobolajia.notesapp.utils.validatePassword
import com.mobolajia.notesapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val vm: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        setClickListeners()
        setUpViews()

        return binding.root
    }

    private fun setClickListeners() {
        binding.deleteAccount.setOnClickListener {

        }

        binding.changePassword.setOnClickListener {
            binding.changePasswordLyt.visibility = View.VISIBLE
            binding.changePassword.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_lock_24,
                0, 0, 0)
        }

        binding.saveChangePasswordBtn.setOnClickListener {
            val pCheck = binding.password.text.toString().validatePassword()
            val newPCheck = binding.newPassword.text.toString().validatePassword()
            if (pCheck != "true") binding.passwordLyt.error = pCheck
            if (newPCheck != "true") binding.newPasswordLyt.error = newPCheck
            if (pCheck == "true" && newPCheck == "true") {
                binding.passwordLyt.isEnabled = false
                binding.newPasswordLyt.isEnabled = false
                changePassword()
            }
        }
    }

    private fun changePassword() {
        vm.checkPasswordAndChange(
            binding.password.text.toString(),
            binding.newPassword.text.toString()
        )
        lifecycleScope.launch {
            vm.updatePassword.collect {
                if (it == "success") {
                    Toast.makeText(
                        requireContext(),
                        "Password changed successfully. Please login again",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                } else if (it.startsWith("failed")) {
                    showMessageDialog(it.substringAfter("failed"))
                    binding.passwordLyt.isEnabled = true
                    binding.newPasswordLyt.isEnabled = true
                }
            }
        }
    }

    private fun setUpViews() {
        lifecycleScope.launch {
            vm.name.collect {
                binding.name.text = getString(R.string.name_template, it)
            }
        }

        lifecycleScope.launch {
            vm.email.collect {
                binding.email.text = getString(R.string.email_template, it)
            }
        }
    }
}