package com.example.challenge4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.challenge4.databinding.ActivityLoginBinding
import com.example.challenge4.databinding.ActivityRegisterBinding
import com.example.challenge4.helpers.InputValidation
import com.example.challenge4.model.User
import com.example.challenge4.sql.DatabaseHelper
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register) as ActivityRegisterBinding
        supportActionBar!!.hide()
        initListeners()
        initObjects()
    }
    private fun initListeners() {
        binding.btnRegister!!.setOnClickListener(this)
        binding.textViewLoginLink!!.setOnClickListener(this)
    }
    private fun initObjects() {
        inputValidation = InputValidation(this)
        databaseHelper = DatabaseHelper(this)
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnRegister -> postDataToSQLite()
            R.id.textViewLoginLink -> finish()
        }
    }
    private fun postDataToSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(binding.textInputEditTextName, binding.textInputLayoutName, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(binding.textInputEditTextEmail, binding.textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(binding.textInputEditTextPassword, binding.textInputLayoutPassword, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidation!!.isInputEditTextMatches(binding.textInputEditTextPassword, binding.textInputEditTextConfirmPassword,
                binding.textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return
        }
        if (!databaseHelper!!.checkUser(binding.textInputEditTextEmail!!.text.toString().trim())) {
            var user = User(name = binding.textInputEditTextName!!.text.toString().trim(),
                email = binding.textInputEditTextEmail!!.text.toString().trim(),
                password = binding.textInputEditTextPassword!!.text.toString().trim())
            databaseHelper!!.addUser(user)
            Snackbar.make(binding.constraintLayout!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
        } else {
            Snackbar.make(binding.constraintLayout!!, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
        }
    }
    private fun emptyInputEditText() {
        binding.textInputEditTextName!!.text = null
        binding.textInputEditTextEmail!!.text = null
        binding.textInputEditTextPassword!!.text = null
        binding.textInputEditTextConfirmPassword!!.text = null
    }
}