package com.example.consumingmyfirstapi.network

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.consumingmyfirstapi.MainActivityDataJob
import com.example.consumingmyfirstapi.R
import com.example.consumingmyfirstapi.databinding.ItemJobBinding
import com.example.consumingmyfirstapi.model.Jobs

class JobAdapter(val jobs: List<Jobs>):RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding=ItemJobBinding.bind(view)

        fun bind(jobs: Jobs){
            binding.txtIdJob.text = jobs.id.toString()
            binding.txtCompania.text = jobs.companyName
            binding.txtPuesto.text = jobs.title
            binding.txtCategoria.text = jobs.category
            binding.txtJobType.text = jobs.jobType

            binding.btVerDetalle.setOnClickListener {
                var intent = Intent(this.itemView.context, MainActivityDataJob::class.java)
                intent.putExtra("url", jobs.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return JobViewHolder(layoutInflater.inflate(R.layout.item_job, parent, false))
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val item = jobs[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return jobs.size
    }
}