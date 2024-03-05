package com.example.lista_de_tareas_app

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_tareas_app.Adapter.ToDoAdapter
import com.example.lista_de_tareas_app.Utils.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Collections

class MainActivity : AppCompatActivity(), DialogCloseListener {
    private lateinit var db: DatabaseHandler
    private lateinit var btnAddTask: FloatingActionButton
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private var tasklist: List<ToDoModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        db = DatabaseHandler(this)
        db.OpenDatabase()

        btnAddTask = findViewById(R.id.btnAdd)
        taskRecyclerView = findViewById(R.id.RecycleView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(db, this)
        taskRecyclerView.adapter = tasksAdapter

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(taskRecyclerView)

        tasklist = db.getAllTasks()
        Collections.reverse(tasklist)
        btnAddTask.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }
    }


    override fun handleDialogClose(dialog: DialogInterface) {
        tasklist = db.getAllTasks()
        Collections.reverse(tasklist)
        tasksAdapter.setTasks(tasklist)
        tasksAdapter.notifyDataSetChanged()
    }
}
