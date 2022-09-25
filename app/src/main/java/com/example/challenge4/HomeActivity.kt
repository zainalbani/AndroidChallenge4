package com.example.challenge4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge4.adapter.NoteClickDeleteInterface
import com.example.challenge4.adapter.NoteClickInterface
import com.example.challenge4.adapter.NoteRVAdapter
import com.example.challenge4.databinding.ActivityHomeBinding
import com.example.challenge4.entity.NoteEntity
import com.example.challenge4.model.User
import com.example.challenge4.preference.PrefManager

class HomeActivity : AppCompatActivity(), NoteClickInterface,NoteClickDeleteInterface {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var prefManager: PrefManager
    private lateinit var email : String
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_home) as ActivityHomeBinding

        prefManager = PrefManager(this)
        email = prefManager.getEmail().toString()
        checkLogin()

        binding.btnLogout.setOnClickListener {
            prefManager.removeData()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.notesRV.layoutManager = LinearLayoutManager(this)
        val noteRVAdapter = NoteRVAdapter(this, this, this)
        binding.notesRV.adapter = noteRVAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.
                noteRVAdapter.updateList(it)
            }
        })
        binding.idFAB.setOnClickListener{
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onNoteClick(note: NoteEntity) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: NoteEntity) {
        // in on note click method we are calling delete
        // method from our view modal to delete our not.
        viewModel.deleteNote(note)
        // displaying a toast message
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
    private fun checkLogin() {
        if (prefManager.isLogin() == false){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}