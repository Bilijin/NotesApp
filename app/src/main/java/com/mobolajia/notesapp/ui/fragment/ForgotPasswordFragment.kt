package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.SyncStateContract
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
import com.mobolajia.notesapp.databinding.FragmentForgotPasswordBinding
import com.mobolajia.notesapp.utils.isInternetConnected
import com.mobolajia.notesapp.utils.isValidEmail
import com.mobolajia.notesapp.viewmodel.ForgotPasswordViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private val vm: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    private fun checkEmail(): Boolean {
        if (binding.email.text.isNullOrBlank()) {
            binding.email.error = getString(R.string.email_blank)
            return false
        }

        if (!binding.email.text.toString().trim().isValidEmail()) {
            binding.email.error = getString(R.string.enter_a_valid_email)
            return false
        }

        return true
    }

    private fun setUpViews() {
        binding.progressIndicator.hide()
        binding.resetPasswordBtn.setOnClickListener {
            if (checkEmail()) {
                disableFields(true)
                Executors.newSingleThreadExecutor().execute {
                    val isThereInternet = isInternetConnected()
                    Handler(Looper.getMainLooper()).post {
                        if (isThereInternet) {
                            vm.resetPassword(binding.email.text.toString())
                            monitorEmailStatus()
                        } else {
                            disableFields(false)
                            Toast.makeText(this.context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun monitorEmailStatus() {
        lifecycleScope.launch {
            vm.resendEmailStatus.collect {
                when (it.trim()) {
                    "success" -> {
                        Toast.makeText(
                            this@ForgotPasswordFragment.context,
                            getString(R.string.password_reset_email_sent),
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                    else -> {
                        Toast.makeText(
                            this@ForgotPasswordFragment.context,
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                        disableFields(false)
                    }
                }
            }
        }
    }

    private fun disableFields(disable: Boolean) {
        binding.email.isEnabled = !disable
        binding.resetPasswordBtn.isEnabled = !disable
        if (disable) binding.progressIndicator.show()
        else binding.progressIndicator.hide()
    }
}