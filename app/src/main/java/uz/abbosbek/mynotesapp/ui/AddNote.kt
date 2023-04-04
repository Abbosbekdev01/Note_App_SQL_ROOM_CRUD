package uz.abbosbek.mynotesapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uz.abbosbek.mynotesapp.R
import uz.abbosbek.mynotesapp.databinding.ActivityAddNoteBinding
import uz.abbosbek.mynotesapp.models.Note
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {

    private val binding by lazy { ActivityAddNoteBinding.inflate(layoutInflater) }
    private lateinit var note: Note
    private lateinit var old_note:Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            old_note = intent.getSerializableExtra("current_note") as Note
            binding.edTitle.setText(old_note.title)
            binding.edNote.setText(old_note.note)
            isUpdate = true
        }catch (e:Exception){
            e.printStackTrace()
        }

        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.imageCheck.setOnClickListener {
            val title = binding.edTitle.text.toString()
            val note_desc = binding.edNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()){

                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm: a")

                if (isUpdate){

                    note = Note(
                        old_note.id,title,note_desc,formatter.format(Date())
                    )
                }else{
                    note = Note(
                        null,title,note_desc,formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else{
                Toast.makeText(this, "Please enter some data ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}