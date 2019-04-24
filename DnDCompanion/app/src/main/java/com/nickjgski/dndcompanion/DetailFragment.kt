package com.nickjgski.dndcompanion

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    var name: String? = null
    var charClass: String? = null
    var charRace: String? = null
    var HP: Int? = null
    var str: Int? = null
    var dex: Int? = null
    var con: Int? = null
    var int: Int? = null
    var wis: Int? = null
    var cha: Int? = null

    private var model: Model? = null


    private lateinit var nameText: TextView
    private lateinit var raceClass: TextView
    private lateinit var strText: TextView
    private lateinit var dexText: TextView
    private lateinit var conText: TextView
    private lateinit var intText: TextView
    private lateinit var wisText: TextView
    private lateinit var chaText: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        model = activity?.let { ViewModelProviders.of(it).get(Model::class.java) }

        view.findViewById<Button>(R.id.weaponButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_detailFragment_to_weaponFragment,
                bundleOf("name" to name))
        }

        view.findViewById<Button>(R.id.deleteButton).setOnClickListener {
            model?.deleteCharacter(name!!)
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        name = this.arguments?.getString("name")
        charClass = this.arguments?.getString("charClass")
        charRace = this.arguments?.getString("race")
        HP = this.arguments?.getInt("HP")
        str = this.arguments?.getInt("strength")
        dex = this.arguments?.getInt("dexterity")
        con = this.arguments?.getInt("constitution")
        int = this.arguments?.getInt("intelligence")
        wis = this.arguments?.getInt("wisdom")
        cha = this.arguments?.getInt("charisma")

        nameText = view.findViewById(R.id.name)
        raceClass = view.findViewById(R.id.raceClassText)
        strText = view.findViewById(R.id.strText)
        dexText = view.findViewById(R.id.dexText)
        conText = view.findViewById(R.id.conText)
        intText = view.findViewById(R.id.intText)
        wisText = view.findViewById(R.id.wisText)
        chaText = view.findViewById(R.id.chaText)

        nameText.text = name
        raceClass.text = "$charRace $charClass"
        strText.text = str.toString()
        dexText.text = dex.toString()
        conText.text = con.toString()
        intText.text = int.toString()
        wisText.text = wis.toString()
        chaText.text = cha.toString()



        
    }

}
