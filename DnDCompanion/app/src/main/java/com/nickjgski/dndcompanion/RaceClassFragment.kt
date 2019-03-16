package com.nickjgski.dndcompanion

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT

class RaceClassFragment : Fragment() {

    private var selectedRace: String? = null
    private var selectedClass: String? = null
    private var charName: String? = null
    private var strMod: Int = 0
    private var dexMod: Int = 0
    private var conMod: Int = 0
    private var intMod: Int = 0
    private var wisMod: Int = 0
    private var chaMod: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_race_class, container, false)

        val raceSpinner = view.findViewById<Spinner>(R.id.races)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.races,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            raceSpinner.adapter = adapter
        }

        val classSpinner = view.findViewById<Spinner>(R.id.classes)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.classes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            classSpinner.adapter = adapter
        }
        raceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedRace = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedRace = parent?.getItemAtPosition(position).toString()
                updateSelections(selectedRace!!)
            }
        }
        classSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedClass = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedClass = parent?.getItemAtPosition(position).toString()
            }
        }

        view.findViewById<Button>(R.id.next).setOnClickListener {
            if(selectedRace != null && selectedClass != null && charName != null) {
                view.findNavController().navigate(
                    R.id.action_raceClassFragment_to_abilityFragment,
                    bundleOf(
                        "name" to charName, "race" to selectedRace, "class" to selectedClass,
                        "strMod" to strMod, "dexMod" to dexMod, "conMod" to conMod, "intMod" to intMod,
                        "wisMod" to wisMod, "chaMod" to chaMod
                    )
                )
            }
        }

        val field = view.findViewById<EditText>(R.id.nameInput)
        field.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.toString() == "") {
                    charName = null
                } else {
                    charName = s?.toString()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Intentionally empty
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Intentionally empty
            }

        })

        return view
    }

    fun updateSelections(race: String) {
        if(race.contains("Dragonborn")) {
            strMod = 2
            dexMod = 0
            conMod = 0
            intMod = 0
            wisMod = 0
            chaMod = 1
        } else if(race.contains("Half-Elf")) {
            strMod = 0
            dexMod = 0
            conMod = 0
            intMod = 0
            wisMod = 0
            chaMod = 2
        } else if(race.contains("Half-Orc")) {
            strMod = 2
            dexMod = 0
            conMod = 1
            intMod = 0
            wisMod = 0
            chaMod = 0
        } else if(race.contains("High Elf")) {
            strMod = 0
            dexMod = 2
            conMod = 0
            intMod = 1
            wisMod = 0
            chaMod = 0
        } else if(race.contains("Hill Dwarf")) {
            strMod = 0
            dexMod = 0
            conMod = 2
            intMod = 0
            wisMod = 1
            chaMod = 0
        } else if(race.contains("Human")) {
            strMod = 1
            dexMod = 1
            conMod = 1
            intMod = 1
            wisMod = 1
            chaMod = 1
        } else if(race.contains("Lightfoot Halfling")) {
            strMod = 0
            dexMod = 2
            conMod = 0
            intMod = 0
            wisMod = 0
            chaMod = 1
        } else if(race.contains("Rock Gnome")) {
            strMod = 0
            dexMod = 0
            conMod = 1
            intMod = 2
            wisMod = 0
            chaMod = 0
        } else if(race.contains("Tiefling")) {
            strMod = 0
            dexMod = 0
            conMod = 0
            intMod = 1
            wisMod = 0
            chaMod = 2
        }
    }

}
