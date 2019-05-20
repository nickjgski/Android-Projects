package com.nickjgski.dndcompanion


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders


class CombatFragment : Fragment() {

    private lateinit var weaponText: TextView
    private lateinit var specifier: EditText
    private lateinit var send: Button

    private var model: Model? = null

    private var target: String = ""
    private var damage: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_combat, container, false)

        model = activity?.let { ViewModelProviders.of(it).get(Model::class.java) }

        weaponText = view.findViewById(R.id.weaponDes)
        specifier = view.findViewById(R.id.specify)

        specifier.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s != null) {
                    target = s?.toString()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Intentionally empty
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Intentionally empty
            }

        })
        send = view.findViewById(R.id.attack)

        send.setOnClickListener {
            sendAttack()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var weapon = this.arguments?.getString("name")
        weaponText.text = weapon
        var times = this.arguments?.getInt("modifier")
        var base = this.arguments?.getInt("base")
        for(i in 1..times!!) {
            damage += (1..base!!).random()
        }

    }

    private fun sendAttack() {
        if(target != "") {
            Log.d("SendAttempt", target)
            Model.sendNotificationToUser(target, "$damage")
        }
    }


}
