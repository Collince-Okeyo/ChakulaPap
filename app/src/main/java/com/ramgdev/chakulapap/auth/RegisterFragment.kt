package com.ramgdev.chakulapap.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramgdev.chakulapap.R
import com.ramgdev.chakulapap.databinding.FragmentRegisterBinding
import com.ramgdev.chakulapap.model.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val userCollectionRef = Firebase.firestore.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            registerWithEmailAndPAssword()
        }

        binding.haveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        return binding.root
    }

    //Login with Email and Password
    private fun registerWithEmailAndPAssword() {
        val email = binding.enterEmail.editText?.text.toString().trim()
        val password = binding.enterPassword.editText?.text.toString().trim()
        val name = binding.fullName.editText?.text.toString().trim()
        val phone = binding.phoneNumber.editText?.text.toString().trim()
        val user = Users(name, email, phone)


        when {
            binding.fullName.editText?.text.toString().isEmpty() -> {
                binding.fullName.editText?.error = "Enter First Name"
            }
            binding.enterEmail.editText?.text.toString().isEmpty() -> {
                binding.enterEmail.editText?.error = "Enter Email"
            }
            binding.enterPassword.editText?.text.toString().isEmpty() -> {
                binding.enterPassword.editText?.error = "Enter Password"
            }
            binding.phoneNumber.editText?.text.toString().isEmpty() -> {
                binding.phoneNumber.editText?.error = "Enter Phone Number"
            }
            else -> {
                binding.registerProgressBar.visibility = VISIBLE
                binding.registerButton.isEnabled = false

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                            val firebaseUser = firebaseAuth.currentUser
                            firebaseUser!!.sendEmailVerification().await()
                            saveUserDetails(user)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    requireContext(),
                                    "Account created successfully. Please check your email to verify",
                                    Toast.LENGTH_LONG
                                ).show()
                                binding.fullName.editText?.setText("")
                                binding.enterEmail.editText?.setText("")
                                binding.enterPassword.editText?.setText("")
                                binding.phoneNumber.editText?.setText("")

                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG)
                                    .show()

                                binding.registerProgressBar.visibility = GONE
                                binding.registerButton.isEnabled = true

                                binding.fullName.editText?.setText("")
                                binding.enterEmail.editText?.setText("")
                                binding.enterPassword.editText?.setText("")
                                binding.phoneNumber.editText?.setText("")
                            }
                        }
                    }
                }
            }
        }
    }

    //saving user details
    private fun saveUserDetails(user: Users) = CoroutineScope(Dispatchers.IO).launch {
        try {
            userCollectionRef.add(user).await()
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}