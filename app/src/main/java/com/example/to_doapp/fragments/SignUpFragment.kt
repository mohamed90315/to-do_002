package com.example.to_doapp.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.to_doapp.R

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEt: EditText = view.findViewById(R.id.emailEt)
        val passEt: EditText = view.findViewById(R.id.passEt)
        val repassEt: EditText = view.findViewById(R.id.repassEt)
        val signinButton: Button = view.findViewById(R.id.signinButton)
        val authButton: Button = view.findViewById(R.id.authButton)

        signinButton.setOnClickListener {
            validateAndNavigate(emailEt, passEt, repassEt)
        }

        authButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.main, SignInFragment())
                addToBackStack(null)
            }
        }
    }

    private fun validateAndNavigate(emailEt: EditText, passEt: EditText, repassEt: EditText) {
        val email = emailEt.text.toString().trim()
        val password = passEt.text.toString().trim()
        val rePassword = repassEt.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                return
            }
            if (password.length < 6) {
                Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return
            }
            if (password != rePassword) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return
            }
            parentFragmentManager.commit {
                replace(R.id.main, HomeFragment())
                addToBackStack(null)
            }
        } else {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}