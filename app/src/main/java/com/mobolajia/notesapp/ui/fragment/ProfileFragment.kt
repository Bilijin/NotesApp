package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.FragmentProfileBinding
import com.mobolajia.notesapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val vm : ProfileViewModel by viewModels()

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