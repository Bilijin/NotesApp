package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
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
import com.mobolajia.notesapp.viewmodel.NotesListViewModel
import kotlinx.coroutines.launch

class NotesListFragment : Fragment() {
    private lateinit var binding: FragmentNotesListBinding
    private val viewModel: NotesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(layoutInflater)
        setupViews()
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

        binding.newNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_noteEditFragment)
        }
    }
}