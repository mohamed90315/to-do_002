package com.example.to_doapp

import android.content.Context
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.completeCheckBox)
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val taskDetails: TextView = itemView.findViewById(R.id.taskDetails)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        val counterButton: Button = itemView.findViewById(R.id.counterButton) // Counter button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]

        // Set task details
        holder.taskName.text = currentTask.name
        holder.taskDetails.text = currentTask.details

        // Update CheckBox state
        holder.checkBox.isChecked = currentTask.isCompleted

        // Apply or remove strikethrough based on completion status
        toggleStrikeThrough(holder.taskName, currentTask.isCompleted)

        // Handle CheckBox changes
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            currentTask.isCompleted = isChecked // Update task completion status
            toggleStrikeThrough(holder.taskName, isChecked)
        }

        // Handle delete button click
        holder.deleteButton.setOnClickListener {
            val context: Context = holder.itemView.context // Retrieve the context from the itemView
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Task")
            builder.setMessage("Are you sure you want to delete this?")
            builder.setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
                taskList.removeAt(position) // Remove the task from the list
                notifyItemRemoved(position) // Notify that the item was removed
                notifyItemRangeChanged(position, taskList.size) // Update remaining items
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
                dialog.dismiss() // Close the dialog if "No" is clicked
            }
            builder.create().show() // Show the AlertDialog
        }

        // Handle counter button click
        holder.counterButton.setOnClickListener {
            showCounterDialog(holder.itemView.context)
        }
    }

    override fun getItemCount() = taskList.size

    // Helper function to apply/remove strikethrough
    private fun toggleStrikeThrough(textView: TextView, isCompleted: Boolean) {
        if (isCompleted) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    // Function to display the counter popup
    private fun showCounterDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_counter, null)
        val counterTextView: TextView = dialogView.findViewById(R.id.counterTextView)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Counter")
            .setView(dialogView)
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()

        dialog.show()

        // Initialize counter and start counting
        var counter = 0
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                counter++
                counterTextView.text = "Counter: $counter"
                handler.postDelayed(this, 1000) // Update every second
            }
        }
        handler.post(runnable)
    }
}
