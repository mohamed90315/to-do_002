package com.example.to_doapp.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.R
import com.example.to_doapp.Task
import com.example.to_doapp.TaskAdapter
import android.widget.TextView

class HomeFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList: MutableList<Task> = mutableListOf()
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false  // Track the play/pause state

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize task list
        taskList.addAll(getTaskList())

        // Setup RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize adapter with the mutable task list
        taskAdapter = TaskAdapter(taskList)
        recyclerView.adapter = taskAdapter

        // Update total tasks and status
        val totalTasksTextView: TextView = view.findViewById(R.id.totalTasksTextView)
        totalTasksTextView.text = "Total Tasks: ${taskList.size}"
        // Display task status based on the number of tasks
        val taskStatusTextView: TextView = view.findViewById(R.id.taskStatusTextView)
        taskStatusTextView.text = getTaskStatus(taskList.size)

        // Add Task Button
        val addButton: ImageButton = view.findViewById(R.id.addTaskButton)
        addButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.main, AddTaskFragment())
                addToBackStack(null)
            }
        }

        // Play/Stop Audio Button
        val playStopButton: ImageButton = view.findViewById(R.id.playAudioButton)
        playStopButton.setOnClickListener {
            if (isPlaying) {
                stopAudio()  // Stop the audio if it's playing
            } else {
                playAudio()  // Play the audio if it's stopped
            }
        }

        return view
    }

    // Logic to determine task status based on the task count
    private fun getTaskStatus(taskCount: Int): String {
        return when {
            taskCount < 3 -> "Free Day"
            taskCount in 3..6 -> "Busy Day"
            else -> "Very Busy Day"
        }
    }

    // Sample task data
    private fun getTaskList(): List<Task> {
        return listOf(
            Task("Sport", "Morning workout"),
            Task("Food", "Buy groceries"),
            Task("Study", "Solve math problems"),
        )
    }

    // Play audio
    private fun playAudio() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.audio)  // Replace with your audio file name
            mediaPlayer?.start()
            isPlaying = true
        } else {
            mediaPlayer?.start()
            isPlaying = true
        }
    }

    // Stop audio
    private fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.prepare()  // Prepare for re-starting if necessary
        isPlaying = false
    }

    // Release MediaPlayer when the fragment is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
