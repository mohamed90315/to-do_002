// AddTaskFragment.kt
package com.example.to_doapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.to_doapp.R
import com.google.android.material.datepicker.MaterialDatePicker

class AddTaskFragment : Fragment() {

    private lateinit var taskNameEditText: EditText
    private lateinit var chooseDateButton: Button
    private lateinit var taskDescriptionEditText: EditText
    private lateinit var addTaskButton: Button
    private lateinit var backToHomeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_task_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        taskNameEditText = view.findViewById(R.id.taskName)
        chooseDateButton = view.findViewById(R.id.chooseDateButton)
        taskDescriptionEditText = view.findViewById(R.id.taskDescription)
        addTaskButton = view.findViewById(R.id.addTaskButton)
        backToHomeButton = view.findViewById(R.id.backToHomeButton)

        // Set up date picker for the "Choose Date" button
        chooseDateButton.setOnClickListener {
            showDatePicker()
        }

        // Set up the "Add Task" button
        addTaskButton.setOnClickListener {
            val taskName = taskNameEditText.text.toString()
            val taskDescription = taskDescriptionEditText.text.toString()

            if (taskName.isNotEmpty() && taskDescription.isNotEmpty()) {
                // Add task logic here, e.g., saving to a database or shared preferences
                // Display confirmation, toast, etc.
                showConfirmation("Task added successfully")

                // Navigate back to the home screen
                parentFragmentManager.commit {
                    replace(R.id.main, HomeFragment())
                    addToBackStack(null)
                }
            } else {
                showConfirmation("Please fill in all fields")
            }
        }

        // Set up the "Back to Home" button
        backToHomeButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.main, HomeFragment())
                addToBackStack(null)
            }
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.addOnPositiveButtonClickListener { date ->
            // Convert date to a readable format and display it (or use it as needed)
            val formattedDate = datePicker.headerText
            chooseDateButton.text = formattedDate
        }
        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    private fun showConfirmation(message: String) {
        // Show a confirmation message (could be a Toast or Snackbar)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}