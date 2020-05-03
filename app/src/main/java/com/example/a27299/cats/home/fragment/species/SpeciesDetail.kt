package com.example.a27299.cats.home.fragment.species

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    }
}