// SignInFragment.kt
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

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val signinButton = view.findViewById<Button>(R.id.signinButton)
        val signupButton = view.findViewById<Button>(R.id.signupButton)

        signinButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username == "admin" && password == "admin") {
                parentFragmentManager.commit {
                    replace(R.id.main, HomeFragment())
                    addToBackStack(null)
                }
            } else {
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        signupButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.main, SignUpFragment())
                addToBackStack(null)
            }
        }

        return view
    }
}