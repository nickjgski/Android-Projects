package com.nickjgski.burgermaker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    var calorieText:TextView? = null
    var priceText:TextView? = null
    var mushroomButton:Switch? = null
    var mayoButton:Switch? = null
    var lettuceButton:Switch? = null
    var picklesButton:Switch? = null
    var tomatoButton:Switch? = null
    var mustardButton:Switch? = null
    private var selectedPatty:String = "None"
    private var selectedBun:String = "None"
    private var calories:Int = 0
    private var numBurgers:Int = 0
    private var numToppings:Int = 0
    private var mushrooms:Boolean = false
    private var mayo:Boolean = false
    private var lettuce:Boolean = false
    private var pickles:Boolean = false
    private var tomato:Boolean = false
    private var mustard:Boolean = false
    private var price:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calorieText = findViewById(R.id.calorieTotal)
        priceText = findViewById(R.id.priceText)
        mushroomButton = findViewById(R.id.mushroomSwitch)
        mayoButton = findViewById(R.id.mayoSwitch)
        lettuceButton = findViewById(R.id.lettuceSwitch)
        picklesButton = findViewById(R.id.picklesSwitch)
        tomatoButton = findViewById(R.id.tomatoSwitch)
        mustardButton = findViewById(R.id.mustardSwitch)

        val field = findViewById<EditText>(R.id.numberField)
        field.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.toString() == "") {
                    numBurgers = 0
                    updateCaloriesAndPrice()
                } else {
                    numBurgers = s?.toString()?.toInt() ?: numBurgers
                    updateCaloriesAndPrice()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Intentionally empty
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Intentionally empty
            }

        })

        val spinner: Spinner = this.findViewById(R.id.pattySpinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.patties,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPatty = parent?.getItemAtPosition(position).toString()
                updateCaloriesAndPrice()
            }
        }

        mushroomButton?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
                mushrooms = isChecked
                updateCaloriesAndPrice()
            }
        })

        mayoButton?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
                mayo = isChecked
                updateCaloriesAndPrice()
            }
        })

        lettuceButton?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
                lettuce = isChecked
                updateCaloriesAndPrice()
            }
        })

        picklesButton?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
                pickles = isChecked
                updateCaloriesAndPrice()
            }
        })

        tomatoButton?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
                tomato = isChecked
                updateCaloriesAndPrice()
            }
        })

        mustardButton?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
                mustard = isChecked
                updateCaloriesAndPrice()
            }
        })

    }

    //Handles the bun buttons being selected
    fun radioButtonSelected(view: View) {

        selectedBun = view.tag.toString()
        updateCaloriesAndPrice()

    }

    /* Updates the value of calories and price
       whenever a different bun or patty is selected
     */
    private fun updateCaloriesAndPrice() {

        price = 0.0
        calories = 0
        numToppings = 0
        //Changes the price and calories based on currently selected bun
        when (selectedBun) {
            "white" -> {
                price += 1
                calories += 140
            }
            "wheat" -> {
                price += 1
                calories += 100
            }
        }
        //Changes price and calories based on the currently selected patty
        when (selectedPatty) {
            "Beef" -> {
                price += 5.5
                calories += 240
            }
            "Grilled Chicken" -> {
                price += 5
                calories += 180
            }
            "Turkey" -> {
                price += 5
                calories += 190
            }
            "Salmon" -> {
                price += 7.5
                calories += 95
            }
            "Veggie" -> {
                price += 4.5
                calories += 80
            }
        }
        //Adjusts price based on toppings
        if(mushrooms) {
            price += 1
            calories += 60
            numToppings++
        }
        if(mayo) {
            calories += 100
            numToppings++
        }
        if(pickles) {
            price += 0.5
            calories += 30
            numToppings++
        }
        if(lettuce) {
            price += 0.3
            calories += 20
            numToppings++
        }
        if(tomato) {
            price += 0.3
            calories += 20
            numToppings++
        }
        if(mustard) {
            calories += 60
            numToppings++
        }

        price *= numBurgers
        calories *= numBurgers
        updateText()

    }

    private fun updateText() {

        //Format the price to a decimal with 2 places
        var decimal= BigDecimal(price).setScale(2, RoundingMode.HALF_EVEN)
        //The text for calorieText
        var calorieString = "Calorie total: $calories"
        //The text for priceText
        var priceString = "Price total: $$decimal"
        if(numToppings > 3) {
            calorieText?.text = "Calorie total: Too many toppings"
            priceText?.text = "Price total: Too many toppings"
        } else {
            calorieText?.text = calorieString
            priceText?.text = priceString
        }

    }

}
