package com.example.emanager.activity.mainActivity

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.emanager.Analytics.AnalyticsFragment
import com.example.emanager.Profile.ProfileFragment
import com.example.emanager.R
import com.example.emanager.roomDatebaseTransaction.Reposetery
import com.example.emanager.adapter.TransactionAdapter
import com.example.emanager.databinding.ActivityMainBinding
import com.example.emanager.roomDatebaseTransaction.DatabaseInsatance
import com.example.emanager.model.Transaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val cal  = Calendar.getInstance()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var arr : ArrayList<Transaction>
    private lateinit var adapter : TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reposetery = Reposetery(DatabaseInsatance.getInstance(this).getDao())
        mainViewModel = ViewModelProvider(this,
            MainViewModelFactory(reposetery)
        ).get(MainViewModel::class.java)

        loadFragment(HomeActivityFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottommenu)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    loadFragment(HomeActivityFragment())
                    true
                }
                R.id.analytics ->{
                    loadFragment(AnalyticsFragment())
                    true
                }
//                R.id.tras -> {
//                    loadFragment(TransactionFragment())
//                    true
//                }
                R.id.profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false

            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }


}


