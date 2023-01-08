package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.FragmentProfileBinding
import com.mobolajia.notesapp.viewmodel.ProfileViewModel

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

    }
}