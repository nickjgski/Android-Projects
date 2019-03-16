package com.nickjgski.dndcompanion


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_race_class.*

class AbilityFragment : Fragment() {

    private var charName: String? = null
    private var charRace: String? = null
    private var charClass: String? = null
    private var base = IntArray(6)
    private var modifiers = IntArray(6)
    private var result = IntArray(6)
    private lateinit var strResult: TextView
    private lateinit var dexResult: TextView
    private lateinit var conResult: TextView
    private lateinit var intResult: TextView
    private lateinit var wisResult: TextView
    private lateinit var chaResult: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_ability, container, false)
        strResult = view.findViewById(R.id.strResult)
        dexResult = view.findViewById(R.id.dexResult)
        conResult = view.findViewById(R.id.conResult)
        intResult = view.findViewById(R.id.intResult)
        wisResult = view.findViewById(R.id.wisResult)
        chaResult = view.findViewById(R.id.chaResult)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charName = this.arguments?.getString("name")
        charRace = this.arguments?.getString("race")
        charClass = this.arguments?.getString("class")
        modifiers[0] = this.arguments?.getInt("strMod")!!
        modifiers[1] = this.arguments?.getInt("dexMod")!!
        modifiers[2] = this.arguments?.getInt("conMod")!!
        modifiers[3] = this.arguments?.getInt("intMod")!!
        modifiers[4] = this.arguments?.getInt("wisMod")!!
        modifiers[5] = this.arguments?.getInt("chaMod")!!

        
        view.findViewById<TextView>(R.id.strMod).text = "${modifiers[0]}"
        view.findViewById<TextView>(R.id.dexMod).text = "+${modifiers[1]}"
        view.findViewById<TextView>(R.id.conMod).text = "+${modifiers[2]}"
        view.findViewById<TextView>(R.id.intMod).text = "+${modifiers[3]}"
        view.findViewById<TextView>(R.id.wisMod).text = "+${modifiers[4]}"
        view.findViewById<TextView>(R.id.chaMod).text = "+${modifiers[5]}"

        for(i in 0..5) {
            result[i] = base[i] + modifiers[i]
        }

        strResult.text = "${result[0]}"
        dexResult.text = "${result[1]}"
        conResult.text = "${result[2]}"
        intResult.text = "${result[3]}"
        wisResult.text = "${result[4]}"
        chaResult.text = "${result[5]}"
    }


}
