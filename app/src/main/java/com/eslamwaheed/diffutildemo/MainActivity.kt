package com.eslamwaheed.diffutildemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eslamwaheed.diffutildemo.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userAdapter = UserAdapter()
    var userList: MutableList<User> = mutableListOf(
        User(1, "eslam"), User(2, "waheed"), User(3, "elbadry"),
        User(4, "esraa"), User(5, "yousef"), User(6, "mohamed"), User(7, "islam"),
        User(8, "ali"), User(9, "walaa"), User(10, "zeinab"),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvUsers.apply {
            setHasFixedSize(true)
            adapter = userAdapter
        }
        userAdapter.submitList(userList.toMutableList())
    }

    override fun onResume() {
        super.onResume()
        repeatFun().start()
    }

    private fun getRandomString(length: Int = 10): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun repeatFun(): Job {
        return GlobalScope.launch(IO) {
            while (isActive) {
                delay(1000)
                withContext(Main) {
                    val user = User(10, getRandomString())
                    userList[9] = user
                    userAdapter.submitList(userList.toMutableList())
                }
            }
        }
    }
}