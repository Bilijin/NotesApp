package com.mobolajia.notesapp.adapter

import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobolajia.notesapp.R
import com.mobolajia.notesapp.databinding.NoteListItemBinding
import com.mobolajia.notesapp.ui.fragment.NotesListFragmentDirections
import com.mobolajia.notesapp.viewmodel.NotesListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesListAdapter(private val vm: NotesListViewModel, private val context: Fragment) :
    RecyclerView.Adapter<NotesListAdapter.MyViewHolder>() {
    private var notes = vm.listOfNotes

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = NoteListItemBinding.inflate(context.layoutInflater, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notes.value[position]

        holder.binding.noteTitle.text = note.title
        holder.binding.noteContent.text = note.text

        holder.binding.root.setOnClickListener {
            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteFragment(note)
            context.findNavController().navigate(action)
        }

        holder.binding.delNote.setOnClickListener {
            vm.deleteNote(note.id)
            context.lifecycleScope.launch {
                vm.delNote.collect {
                    when (it) {
                        "true" -> Toast.makeText(
                            context.requireContext(),
                            context.getString(R.string.note_deleted),
                            Toast.LENGTH_SHORT
                        ).show()
                        "false" -> Toast.makeText(
                            context.requireContext(),
                            context.getString(R.string.note_delete_fail),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = notes.value.size

    class MyViewHolder(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root)
}