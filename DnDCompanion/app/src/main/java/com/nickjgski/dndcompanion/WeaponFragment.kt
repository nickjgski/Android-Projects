package com.nickjgski.dndcompanion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class WeaponFragment : Fragment() {

    var name: String? = null
    private var weaponAdapter = WeaponListAdapter()
    private var model: Model? = null
    private var selected: String = ""
    private lateinit var weaponList: RecyclerView

    private lateinit var weaponSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weapon, container, false)

        model = activity?.let { ViewModelProviders.of(it).get(Model::class.java) }

        weaponList = view.findViewById(R.id.weaponList)
        weaponList.layoutManager = LinearLayoutManager(view.context)
        weaponList.adapter = weaponAdapter

        weaponSpinner = view.findViewById(R.id.weaponSelector)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.weapons,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            weaponSpinner.adapter = adapter
        }

        weaponSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selected = parent?.getItemAtPosition(position).toString()
            }

        }

        view.findViewById<Button>(R.id.addWeapon).setOnClickListener {
            addWeapon()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = this.arguments?.getString("name")
        model?.getCharacterWeapons(name!!)?.observe(
            this,
            Observer<List<Weapon>>{ weapons ->
                weapons?.let {
                    weaponAdapter.setWeapons(it)
                }
            }
        )
    }

    private fun addWeapon() {

        var weaponName = selected.substringBefore(',')
        var modifier = selected.substringAfter(", ").substringBefore("d").toInt()
        var base = selected.substringAfter('d').substringBeforeLast(',').toInt()
        var ability = selected.substring(selected.length - 3)
        var newWeapon = Weapon(weaponName, base, ability, modifier, name!!)
        model?.insertWeapon(newWeapon)

    }

    inner class WeaponListAdapter(): RecyclerView.Adapter<WeaponListAdapter.WeaponViewHolder>() {

        private var weapons = emptyList<Weapon>()

        internal fun setWeapons(weapons: List<Weapon>) {
            this.weapons = weapons
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return weapons.size
        }

        override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
            holder.view.findViewById<TextView>(R.id.title).text = weapons[position].name
            holder.view.findViewById<TextView>(R.id.extra_info).text = "${weapons[position].modifier}d${weapons[position].base}"
            holder.itemView.setOnClickListener {
                holder.view.findNavController().navigate(R.id.action_weaponFragment_to_combatFragment,
                    bundleOf("name" to weapons[position].name, "base" to weapons[position].base, "modifier" to weapons[position].modifier,
                        "ability" to weapons[position].ability))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {

            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
            return WeaponViewHolder(v)
        }

        inner class WeaponViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


}
