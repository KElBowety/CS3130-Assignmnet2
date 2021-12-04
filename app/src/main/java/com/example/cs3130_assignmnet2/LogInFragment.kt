package com.example.cs3130_assignmnet2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.Navigation
import com.example.cs3130_assignmnet2.FirebaseUtils.firebaseAuth
import com.example.cs3130_assignmnet2.FirebaseUtils.firebaseUser
import com.example.cs3130_assignmnet2.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseUser

class LogInFragment : Fragment() {
    private var fragmentLogInBinding: FragmentLogInBinding? = null
    lateinit var userEmail: String
    lateinit var userPassword: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLogInBinding.inflate(inflater, container, false)
        fragmentLogInBinding = binding

        binding.loginButton.setOnClickListener {
            logIn()
        }

        binding.registerButton.setOnClickListener {
            register()
        }

        binding.backButton.setOnClickListener {
            backToLogIn()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            goToHome()
        }
    }

    private fun register() {
        if (fragmentLogInBinding!!.confirmPasswordText.visibility == View.GONE) {
            fragmentLogInBinding!!.apply {
                confirmPasswordText.visibility = View.VISIBLE
                loginButton.visibility = View.GONE
                backButton.visibility = View.VISIBLE

                val constraints = ConstraintSet()
                constraints.clone(layout)
                constraints.clear(registerButton.id, ConstraintSet.TOP)
                constraints.connect(
                    registerButton.id,
                    ConstraintSet.TOP,
                    confirmPasswordText.id,
                    ConstraintSet.BOTTOM,
                    (context!!.resources.displayMetrics.density * 24).toInt()
                )
                layout.setConstraintSet(constraints)
            }
        } else {
            if (notEmptyRegister()) {
                if (identical()) {
                    userEmail = fragmentLogInBinding!!.emailText.editText!!.text.toString().trim()
                    userPassword =
                        fragmentLogInBinding!!.passwordText.editText!!.text.toString().trim()

                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity, "Account created", Toast.LENGTH_SHORT)
                                    .show()
                                sendEmailVerification()
                                goToHome()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Account creation failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(activity, "Passwords must match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logIn() {
        if (notEmptyUser()) {
            userEmail = fragmentLogInBinding!!.emailText.editText!!.text.toString().trim()
            userPassword = fragmentLogInBinding!!.passwordText.editText!!.text.toString().trim()

            firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        goToHome()
                    } else {
                        Toast.makeText(activity, "Log in failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToLogIn() {
        fragmentLogInBinding!!.apply {
            confirmPasswordText.visibility = View.GONE
            loginButton.visibility = View.VISIBLE
            backButton.visibility = View.GONE

            val constraints = ConstraintSet()
            constraints.clone(layout)
            constraints.clear(registerButton.id, ConstraintSet.TOP)
            constraints.connect(
                registerButton.id, ConstraintSet.TOP, loginButton.id, ConstraintSet.BOTTOM,
                (context!!.resources.displayMetrics.density * 16).toInt()
            )
            layout.setConstraintSet(constraints)
        }
    }

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Email sent to $userEmail", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToHome() = view?.let {
        Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_homeFragment)
    }

    private fun notEmptyUser(): Boolean =
        fragmentLogInBinding!!.emailText.editText!!.text.toString().trim().isNotEmpty() &&
                fragmentLogInBinding!!.passwordText.editText!!.text.toString().trim().isNotEmpty()

    private fun notEmptyRegister(): Boolean =
        fragmentLogInBinding!!.emailText.editText!!.text.toString().trim().isNotEmpty() &&
                fragmentLogInBinding!!.passwordText.editText!!.text.toString().trim()
                    .isNotEmpty() &&
                fragmentLogInBinding!!.confirmPasswordText.editText!!.text.toString().trim()
                    .isNotEmpty()

    private fun identical(): Boolean =
        fragmentLogInBinding!!.passwordText.editText!!.text.toString().trim() ==
                fragmentLogInBinding!!.confirmPasswordText.editText!!.text.toString().trim()

}