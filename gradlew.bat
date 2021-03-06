package com.example.lesson_07_android_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_07_android_recyclerview.data.ApiServices
import com.example.lesson_07_android_recyclerview.data.PlaceListAdapter
import com.example.lesson_07_android_recyclerview.databinding.ActivityMainBinding
import com.example.lesson_07_android_recyclerview.model.CountriesResponse
import com.example.lesson_07_android_recyclerview.model.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnQueryTextListener {

    private lateinit var binding:ActivityMainBinding

    private var adaptar: PlaceListAdapter?=null
    private var countryList:ArrayList<Place>?=null
    private var layoutManager:RecyclerView.LayoutManager?=null

    //private lateinit var myRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        countryList=ArrayList<Place>()
        layoutManager=LinearLayoutManager(this)
        adaptar= PlaceListAdapter(countryList!!,this)

        //myRecyclerView = findViewById(R.id.myRecyclerView)

        binding.myRecyclerView.layoutManager=layoutManager
        binding.myRecyclerView.adapter=adaptar

        var countryNameList:Array<String> = arrayOf(
            "Canada","USA", "Mexico", "Clumbia", "Malaysia", "Singapore", "Turke", "Nicaragua",
            "India", "Italy", "Tunisia", "Chile", "Argentina", "Spain", "Per"
        )

        var citiesNameList:Array<String> = arrayOf(
            "Ottawa","Washington, D.C.", "Mexico City", "Bogota", "Kuala Lumpur", "Singapore", "Ankara",
            "Managua", "New Delhi", "Rome", "Tunis", "Santiago", "Buenos Aires", "Madrid", "Lima"
        )

        /*for (i in 0..14){
            var countries=Countries()
            countries.countryName = countryNameList[i]
            countries.capitalName = citiesNameList[i]
            countryList?.add(countries)
        }
        adaptar!!.notifyDataSetChanged()*/
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.countrylayer.com/v2/") //Pasamos la URL base
            .addConverterFactory(Gson