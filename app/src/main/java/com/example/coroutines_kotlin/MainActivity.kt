package com.example.coroutines_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*GlobalScope.launch {
            delay(1000L)
            val ans1=doNetworkCall()
            Log.d("MainActivity","This is the coroutine from the main thread $ans1")
            val ans2=doNetworkCall1()

            Log.d("MainActivity","This is the coroutine from the main thread $ans2")
        }*/

        //coroutine contexts ; dispatchers
       /* GlobalScope.launch { Dispatchers.IO
            Log.d("MainActivity","This is the coroutine from the main thread ${Thread.currentThread().name}")
            val ans1=doNetworkCall()
            delay(3000L)
            withContext(Dispatchers.Main){
                Log.d("MainActivity","This is the coroutine from the main thread $ans1")
            }
        }*/

        //run blocking
        /*runBlocking {
            Log.d("MainActivity","This is the coroutine from the main thread ${Thread.currentThread().name}")
            delay(3000L)
            Log.d("MainActivity","This is the coroutine from the main thread ${Thread.currentThread().name}")
        }*/

        //join wait and cancel
       /* val job=GlobalScope.launch(Dispatchers.Default) {
            repeat(5){
                Log.d("MainActivity","This is the coroutine from the main thread ${Thread.currentThread().name}")
                delay(1000L)
            }
        }
        runBlocking {
            delay(2000L)
            job.cancel()
           //job.join()
            Log.d("MainActivity","Main thread is continuing...")
        }*/

        //async call
        GlobalScope.launch(Dispatchers.IO) {
            val time= measureTime {
                //this method is not efficient
                /*var ans1:String?=null
                var ans2:String?=null
                val job1=launch { ans1=doNetworkCall() }
                val job2=launch { ans2=doNetworkCall1() }
                job1.join()
                job2.join()
                Log.d("MainActivity","This is the coroutine from the main thread $ans1")
                Log.d("MainActivity","This is the coroutine from the main thread $ans2")*/

                val ans1=async { doNetworkCall() }
                val ans2=async { doNetworkCall1() }
                Log.d("MainActivity","This is the coroutine from the main thread ${ans1.await()}")
                Log.d("MainActivity","This is the coroutine from the main thread ${ans2.await()}")
            }
            Log.d("MainActivity","time is $time ms")

        }
        Log.d("MainActivity","This is the the main thread ${Thread.currentThread().name}")
    }

    private suspend fun doNetworkCall():String{
        delay(3000L)
        return "This is network call function"
    }
    private suspend fun doNetworkCall1():String{
        delay(10000L)
        return "This is network call function1"
    }
}