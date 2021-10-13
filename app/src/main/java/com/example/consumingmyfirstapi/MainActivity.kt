package com.example.consumingmyfirstapi

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumingmyfirstapi.databinding.ActivityMainBinding
import com.example.consumingmyfirstapi.interfaces.ApiServices
import com.example.consumingmyfirstapi.model.Jobs
import com.example.consumingmyfirstapi.model.jobsResponse
import com.example.consumingmyfirstapi.network.JobAdapter
import com.example.consumingmyfirstapi.util.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding:ActivityMainBinding //Para el viewBindig
    private lateinit var adapter:JobAdapter
    private var job = mutableListOf<Jobs>()
    private var selectItemRadioButton:String = "Por Trabajo o Descripción"
    private var dataLimit:Int = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.svJobs.setOnQueryTextListener(this)

        binding.btConfigFilter.setOnClickListener{
            val dialog = FilterDialogFragment()

            val bundle: Bundle = Bundle()

            var limit = "0"
            if(!dataLimit.toString().isNullOrEmpty()) limit = dataLimit.toString()

            bundle.putString("rbSelect", selectItemRadioButton)
            bundle.putString("limit", limit)

            dialog.show(supportFragmentManager, "filterDialog") //Mostramos el Fragment
            dialog.parentFragmentManager.setFragmentResult("keySent", bundle) //Enviamos los datos al fragment

            //Recibimos los datos del fragment
            dialog.parentFragmentManager
                .setFragmentResultListener("key", this,
                 FragmentResultListener() { requestKey, result ->
                     selectItemRadioButton = result.get("rbSelect").toString()
                     dataLimit = result.get("limit").toString().toInt()

                 })
        }

        binding.svJobs.setOnCloseListener(object: SearchView.OnCloseListener {
            override fun onClose(): Boolean{
                getDataByFilter("")
                return false
            }
        })

        getByLimitJob("$dataLimit")
        initRecyclerView()
    }

    private fun getDataByFilter(query: String) {
        job.clear()
        initRecyclerView()

        if(query.isNullOrEmpty()) {
            if(selectItemRadioButton == "Por cantidad registros") getByLimitJob("${dataLimit.toString()}")
            else getAllJob()
        }
        else {
            when (selectItemRadioButton) {
                "Por cantidad registros" -> getByLimitJob("${dataLimit.toString()}")
                "Por Categoría" -> getByCategoryJob("$query")
                "Por Compañia" -> getByCompanyJob("$query")
                "Por Trabajo o Descripción" -> getByJob("$query")
                else -> getAllJob()
            }
            initRecyclerView()
        }
    }

      private fun initRecyclerView() {
        adapter = JobAdapter(job)
        binding.rvJobs.layoutManager = LinearLayoutManager(this)
        binding.rvJobs.adapter = adapter
    }

    private fun getAllJob(){
        binding.pbSearch.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val call = getRetrofit().create(ApiServices::class.java).getAllJobs("")
                val jobs = call.body()

                    if (call.isSuccessful) {
                        //Show recyclerview
                        var jobs = jobs?.jobs ?: emptyList()
                        job.clear()
                        job.addAll(jobs)
                        adapter.notifyDataSetChanged()

                        binding.pbSearch.visibility = View.GONE

                    } else {
                        //Show error
                        Toast.makeText(
                            this@MainActivity,
                            "Error Occurred: El API no esta disponible  ${call.message()}",
                            Toast.LENGTH_LONG).show()
                        binding.pbSearch.visibility = View.GONE
                    }
                //}
            }
            catch (e:Exception){
                Log.v("APIDATA", "Exception: ${e.localizedMessage}")
                binding.pbSearch.visibility = View.GONE
            }
        }
    }

    private fun getByJob(query:String){
        binding.pbSearch.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                    val call = getRetrofit().create(ApiServices::class.java).getByJobs("?search=$query")
                    val jobs = call.body()

                        if (call.isSuccessful && call != null) {
                            //Show recyclerview
                            var jobs = jobs?.jobs ?: emptyList()
                            job.clear()
                            job.addAll(jobs)
                            adapter.notifyDataSetChanged()

                            binding.pbSearch.visibility = View.GONE

                        } else {
                            // Show API error.
                            Toast.makeText(
                                this@MainActivity,
                                "Error Occurred: El API no esta disponible ${call.message()}",
                                Toast.LENGTH_LONG).show()
                            binding.pbSearch.visibility = View.GONE
                        }

            }
            catch (e: Exception){
                Log.v("APIDATA", "Exception: ${e.localizedMessage}")
                binding.pbSearch.visibility = View.GONE
            }
        }
    }

    private fun getByLimitJob(query:String){
        binding.pbSearch.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val call = getRetrofit().create(ApiServices::class.java).getByLimitJobs("?limit=$query")
                val jobs = call.body()

                if (call.isSuccessful && call != null) {
                    //Show recyclerview
                    var jobs = jobs?.jobs ?: emptyList()
                    job.clear()
                    job.addAll(jobs)
                    adapter.notifyDataSetChanged()

                    binding.pbSearch.visibility = View.GONE

                } else {
                    // Show API error.
                    Toast.makeText(
                        this@MainActivity,
                        "Error Occurred: El API no esta disponible  ${call.message()}",
                        Toast.LENGTH_LONG).show()
                    binding.pbSearch.visibility = View.GONE
                }

            }
            catch (e: Exception){
                Log.v("APIDATA", "Exception: ${e.localizedMessage}")
                binding.pbSearch.visibility = View.GONE
            }
        }
    }

    private fun getByCategoryJob(query:String){
        binding.pbSearch.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val call = getRetrofit().create(ApiServices::class.java).getByJobs("?category=$query")
                val jobs = call.body()

                if (call.isSuccessful && call != null) {
                    //Show recyclerview
                    var jobs = jobs?.jobs ?: emptyList()
                    job.clear()
                    job.addAll(jobs)
                    adapter.notifyDataSetChanged()

                    binding.pbSearch.visibility = View.GONE

                } else {
                    // Show API error.
                    Toast.makeText(
                        this@MainActivity,
                        "Error Occurred: El API no esta disponible  ${call.message()}",
                        Toast.LENGTH_LONG).show()
                    binding.pbSearch.visibility = View.GONE
                }

            }
            catch (e: Exception){
                Log.v("APIDATA", "Exception: ${e.localizedMessage}")
                binding.pbSearch.visibility = View.GONE
            }
        }
    }

    private fun getByCompanyJob(query:String){
        binding.pbSearch.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val call = getRetrofit().create(ApiServices::class.java).getByJobs("?company_name=$query")
                val jobs = call.body()

                if (call.isSuccessful && call != null) {
                    //Show recyclerview
                    var jobs = jobs?.jobs ?: emptyList()
                    job.clear()
                    job.addAll(jobs)
                    adapter.notifyDataSetChanged()

                    binding.pbSearch.visibility = View.GONE

                } else {
                    // Show API error.
                    Toast.makeText(
                        this@MainActivity,
                        "Error Occurred: El API no esta disponible  ${call.message()}",
                        Toast.LENGTH_LONG).show()
                    binding.pbSearch.visibility = View.GONE
                }

            }
            catch (e: Exception){
                Log.v("APIDATA", "Exception: ${e.localizedMessage}")
                binding.pbSearch.visibility = View.GONE
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        getDataByFilter(query?:"")
        hideKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}