package com.mobolajia.notesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mobolajia.notesapp.databinding.FragmentNoteBinding
import com.mobolajia.notesapp.model.Note

class NoteFragment : Fragment() {

    private lateinit var binding : FragmentNoteBinding
    private val args: NoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)

        setupViews(args.note)
        return binding.root
    }

    private fun setupViews (note : Note) {
        binding.noteTitle.text = note.title
        binding.noteText.text = note.text
    }
}