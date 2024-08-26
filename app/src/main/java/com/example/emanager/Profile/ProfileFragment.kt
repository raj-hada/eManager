package com.example.emanager.Profile

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emanager.databinding.FragmentProfileBinding
import com.example.emanager.utils.SP

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        sharedPreferences= SP.getSharedPreferenceInstance(requireContext())

        loadData()

        // Inflate the layout for this fragment
        return binding.root
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

}