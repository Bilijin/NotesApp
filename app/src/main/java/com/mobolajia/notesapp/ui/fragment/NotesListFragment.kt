package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.adapter.NotesListAdapter
import com.mobolajia.notesapp.databinding.FragmentNotesListBinding
import com.mobolajia.notesapp.utils.SharedPrefHelper
import com.mobolajia.notesapp.viewmodel.NotesListViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesListFragment : Fragment() {
    private lateinit var binding: FragmentNotesListBinding
    private val viewModel: NotesListViewModel by viewModels()
    private lateinit var sharedPrefs : SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(layoutInflater)
        sharedPrefs = SharedPrefHelper(this.requireActivity())
        setupViews()
        setupClickListeners()
        return binding.root
    }

    private fun setupViews() {
        val adapter = NotesListAdapter(viewModel, this)
        binding.notesRecycler.adapter = adapter

        lifecycleScope.launch {
            viewModel.listOfNotes.collect {
                adapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModel.name.collect {
                binding.helloUser.text = getString(R.string.hello_user, it)
            }
        }

        lifecycleScope.launch {
            viewModel.noteCount.collect{
                sharedPrefs.saveInt("note_count", it)
            }
        }
    }

    private fun setupClickListeners() {
        binding.newNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_noteEditFragment)
        }

        binding.openProfile.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_profileFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }
}