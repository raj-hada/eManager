package com.example.emanager.activity.Setting

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emanager.databinding.ActivityProfileBinding
import com.example.emanager.utils.SP

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private lateinit var sharedPreferences : SharedPreferences
    private var isTrue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences= SP.getSharedPreferenceInstance(this)

        loadData()

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }

        binding.save.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val mobile = binding.mobile.text.toString()
            var selectedGender = ""
            val selectedRadioButtonId = binding.radioGroupGender.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = binding.root.findViewById<RadioButton>(selectedRadioButtonId)
                selectedGender = selectedRadioButton.text.toString()
            }
            if(TextUtils.isEmpty(name)){
                binding.name.error = "Enter your name"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(email)){
                binding.email.error = "Enter your email"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(mobile)){
                binding.mobile.error = "Enter your mobile"
                return@setOnClickListener
            }
            if(selectedGender.isEmpty()){
                Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val editor = SP.getEditor(this)
            editor.putString("NAME",name)
            editor.putString("EMAIL",email)
            editor.putString("MOBILE",mobile)
            editor.putString("GENDER",selectedGender)
            editor.apply()

            notEnabled()
            loadData()
        }



        binding.edit.setOnClickListener{
            if(!isTrue) {
                isEnable()
            }
            else{
                notEnabled()
            }
        }



        binding.cancel.setOnClickListener {
            notEnabled()
        }



    }

    private fun loadData() {
        val name = sharedPreferences.getString("NAME","")
        val email = sharedPreferences.getString("EMAIL","")
        val mobile = sharedPreferences.getString("MOBILE","")
        val gender = sharedPreferences.getString("GENDER","")

        binding.titleName.text = name
        binding.name.setText(name)
        binding.mobile.setText(mobile)
        binding.email.setText(email)
        binding.gender.setText(gender)


    }

    private fun isEnable() {
        binding.name.isEnabled = true
        binding.mobile.isEnabled = true
        binding.genderRadio.visibility = View.VISIBLE
        binding.genderLayout.visibility = View.GONE
        binding.email.isEnabled = true
        binding.btn.visibility = View.VISIBLE
        isTrue = !isTrue
    }

    private fun notEnabled() {
        binding.name.isEnabled = false
        binding.mobile.isEnabled = false
        binding.radioGroupGender.isEnabled = false
        binding.genderRadio.visibility = View.GONE
        binding.genderLayout.visibility = View.VISIBLE
        binding.email.isEnabled = false
        binding.btn.visibility = View.GONE
        isTrue = !isTrue
    }
}
