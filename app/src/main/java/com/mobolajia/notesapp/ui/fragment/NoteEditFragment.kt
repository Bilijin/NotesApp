package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.FragmentNoteEditBinding
import com.mobolajia.notesapp.model.Note
import com.mobolajia.notesapp.viewmodel.NoteEditViewModel
import kotlinx.coroutines.launch

class NoteEditFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditBinding
    private val vm : NoteEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteEditBinding.inflate(layoutInflater)

        binding.saveBtn.setOnClickListener {
            val note = Note(
                binding.noteTitle.text.toString(),
                binding.noteText.text.toString(),
                "today",
                1
            )
            lifecycleScope.launch {
                vm.saveNote(note)
            }
        }
        return binding.root
    }
}