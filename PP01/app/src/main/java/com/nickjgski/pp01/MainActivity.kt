package com.nickjgski.pp01

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val spinner: Spinner? = findViewById(R.id.brand_dropdown)
    val processors: RadioGroup? = findViewById(R.id.cpuRadio)
    val priceTag: TextView? = findViewById(R.id.price)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArrayAdapter.createFromResource(this, R.array.brands, android.R.layout.simple_spinner_item).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner?.adapter = adapter
        }

        var currentBrand:String = ""
        var cpu:String = ""
        var price:Int = 0

        spinner?.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {

                currentBrand = parent?.getItemAtPosition(pos) as String
                price = calculatePrice(brand =currentBrand, cpu = cpu)
            }


        })

        processors?.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == 0) {
                    cpu = "i3"
                }
                else if (p1 == 1){
                    cpu = "i5"
                }
                else if (p1 == 2) {
                    cpu = "i7"
                }
                else if (p1 == -1) {
                    cpu = "none"
                }
                price = calculatePrice(brand =currentBrand, cpu = cpu)
            }

        })

        priceTag?.setText("$$price")




    }
    fun calculatePrice(brand:String, cpu:String): Int {
        if (brand.equals("HP")) {
            if (cpu.equals("i3")) {
                return 300
            }
            if (cpu.equals("i5")) {
                return 500
            }
            if (cpu.equals("i7")) {
                return 700
            }
        }
        if (brand.equals("Lenovo")) {
            if (cpu.equals("i3")) {
                return 330
            }
            if (cpu.equals("i5")) {
                return 550
            }
            if (cpu.equals("i7")) {
                return 770
            }

        }
        return 0
    }
}
