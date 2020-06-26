package com.example.mynotes.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mynotes.R
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDB
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        NoteDB(requireActivity())
        doneBtn.setOnClickListener {
            val noteTitle = title.text.toString().trim()
            val noteBody = note.text.toString().trim()

            if (noteTitle.isEmpty()) {
                title.error = "Title required"
                title.requestFocus()
                return@setOnClickListener
            }
            if (noteBody.isEmpty()) {
                note.error = "Note required"
                note.requestFocus()
                return@setOnClickListener
            }

            saveNote(Note(noteTitle, noteBody))
        }
    }

    private fun saveNote(note: Note) {
        class SaveNote : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                NoteDB(requireActivity()).getNoteDao().addNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(requireContext(), "Note Saved", Toast.LENGTH_SHORT).show()
            }
        }
        SaveNote().execute()
    }
}
