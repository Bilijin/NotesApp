package com.mobolajia.notesapp.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mobolajia.notesapp.databinding.NoteListItemBinding
import com.mobolajia.notesapp.viewmodel.NotesListViewModel

class NotesListAdapter(vm: NotesListViewModel, private val context: Fragment) :
    RecyclerView.Adapter<NotesListAdapter.MyViewHolder>() {
    private var notes = vm.listOfNotes

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = NoteListItemBinding.inflate(context.layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notes.value[position]

        holder.binding.noteTitle.text = note.title
        holder.binding.noteContent.text = note.text
    }

    override fun getItemCount(): Int = notes.value.size

    class MyViewHolder(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root)
}