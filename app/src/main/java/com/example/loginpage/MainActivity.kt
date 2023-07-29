package com.example.loginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.loginpage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    // our machine name
    private val stateMachineName = "State Machine 1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onEmailFoucus()
        onPasswordFoucus()
        eyePostionChange()
        subscribeOnClickEvents()
    }

    private fun subscribeOnClickEvents() {
        binding!!.loginBtn.setOnClickListener {
            binding!!.password.clearFocus()

            val email = binding!!.email.text.toString()
            val password = binding!!.password.text.toString()

            // input field validation
            if(email.isNotEmpty() && password.isNotEmpty()) {
                LoginAccount(email, password)
            } else {
                Toast.makeText(this, "Enter Id and Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun LoginAccount(email: String, password: String) {

        if (email == "admin@gmail.com" && password == "123456"){
            Handler(mainLooper).postDelayed({
                binding!!.loginCharacter.controller.fireState(stateMachineName, "success")
            }, 2000)
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
        }else{
            Handler(mainLooper).postDelayed({
                binding!!.loginCharacter.controller.fireState(stateMachineName, "fail")
            },2000)

            Toast.makeText(this, "Invalid Id or Password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eyePostionChange() {
        binding!!.email.addTextChangedListener(object  : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    binding!!.loginCharacter.controller.setNumberState(stateMachineName, "Look", 2*p0!!.length.toFloat())
                } catch (_: Exception) {

                }
            }

            override fun afterTextChanged(p0: Editable?) {


            }

        })
    }

    private fun onPasswordFoucus() {
        binding!!.password.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding!!.loginCharacter.controller.setBooleanState(stateMachineName, "hands_up", true)
            } else {
                binding!!.loginCharacter.controller.setBooleanState(stateMachineName, "hands_up", false)
            }
        }
    }

    private fun onEmailFoucus() {
        binding!!.email.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding!!.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "Check",
                    value = true
                )
            } else {
                binding!!.loginCharacter.controller.setBooleanState(
                    stateMachineName = stateMachineName,
                    inputName = "Check",
                    value = false
                )
            }
        }
    }
}