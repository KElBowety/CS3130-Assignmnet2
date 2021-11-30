package com.example.cs3130_assignmnet2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.cs3130_assignmnet2.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    private var fragmentLogInBinding: FragmentLogInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLogInBinding.inflate(inflater, container, false)
        fragmentLogInBinding = binding

        binding.loginButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_homeFragment)
        }

        binding.registerButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_homeFragment)
        }

        return binding.root
    }

}