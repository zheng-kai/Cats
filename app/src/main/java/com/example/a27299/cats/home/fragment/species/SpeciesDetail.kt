package com.example.a27299.cats.home.fragment.species

import android.graphics.Color
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.a27299.cats.R
import com.example.a27299.cats.module.Module
import kotlinx.android.synthetic.main.activity_species_detail.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SpeciesDetail : AppCompatActivity() {
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species_detail)
        position = intent.getIntExtra("position", 0)
        toolbar_species_detail.setNavigationOnClickListener {
            onBackPressed()
        }
        Module.speciesLiveData.value?.let {

            tv_species_detail_title.text = it[position].breeds[0].name
            Glide.with(this@SpeciesDetail)
                    .load(it[position].url)
                    .into(iv_species_detail_backdrop)
        }
        rv_species_detail.adapter = DetailAdapter(parseData(), Module.speciesLiveData.value?.get(position)?.breeds?.get(0)?.description
                ?: "无")

        rv_species_detail.layoutManager = LinearLayoutManager(this)
    }

    private fun parseData(): ArrayList<Pair<String, Int>> {
        val list = ArrayList<Pair<String, Int>>()
        Module.speciesLiveData.value?.let {
            it[position].breeds[0].apply {

                list.add(Pair("affection_level", affection_level))
                list.add(Pair("child_friendly", child_friendly))
                list.add(Pair("dog_friendly", dog_friendly))
                list.add(Pair("energy_level", energy_level))
                list.add(Pair("health_issues", health_issues))
                list.add(Pair("intelligence", intelligence))
                list.add(Pair("social_needs", social_needs))

            }
        }
        return list
    }
}