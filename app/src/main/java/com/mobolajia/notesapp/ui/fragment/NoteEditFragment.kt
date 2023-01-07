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
import androidx.navigation.fragment.navArgs
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.FragmentNoteEditBinding
import com.mobolajia.notesapp.model.Note
import com.mobolajia.notesapp.utils.SharedPrefHelper
import com.mobolajia.notesapp.viewmodel.NoteEditViewModel
import kotlinx.coroutines.launch

class NoteEditFragment : Fragment() {

    private lateinit var binding: FragmentNoteEditBinding
    private lateinit var sharedPrefs: SharedPrefHelper
    private val vm: NoteEditViewModel by viewModels()
    private val args: NoteEditFragmentArgs by navArgs()
    private var noteCount = 0
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteEditBinding.inflate(layoutInflater)
        sharedPrefs = SharedPrefHelper(this.requireActivity())
        note = args.note
        noteCount = sharedPrefs.getInt("note_count", 0)
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        note?.let {
            binding.noteText.setText(it.text)
            binding.noteTitle.setText(it.title)
            binding.saveBtn.text = "Update"
        }

        binding.saveBtn.setOnClickListener {
            if (note != null) {
                note?.let {
                    it.text = binding.noteText.text.toString()
                    it.title = binding.noteTitle.text.toString()
                    vm.saveNote(it)
                }
            } else {
                note = Note(
                    binding.noteTitle.text.toString(),
                    binding.noteText.text.toString(),
                    "today",
                    ++noteCount
                )
                note?.let {vm.saveNote(it)}
            }

            lifecycleScope.launch {
                vm.noteSaveStatus.collect {
                    when (it) {
                        "success" -> {
                            Toast.makeText(
                                this@NoteEditFragment.context,
                                "Note Saved successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            sharedPrefs.saveInt("note_count", noteCount)
                            vm.updateNoteCount(noteCount)
                            val action = NoteFragmentDirections.actionNoteFragmentToNoteEditFragment(note)
                            findNavController().navigate(action)
                        }
                        else -> {
                            Toast.makeText(this@NoteEditFragment.context, it, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}