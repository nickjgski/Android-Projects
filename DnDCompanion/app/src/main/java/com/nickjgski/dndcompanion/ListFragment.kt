package com.nickjgski.dndcompanion


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProviders.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment() {

    private var model: Model? = null
    private var charAdapter = CharacterListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list, container, false)
        view.findViewById<Button>(R.id.createChar).setOnClickListener {
            view.findNavController().navigate(R.id.action_listFragment_to_raceClassFragment)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.charList)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = charAdapter
        model = activity?.let { of(it).get(Model::class.java) }

        model?.allCharacters?.observe(
            this,
            Observer<List<Character>>{ characters ->
                characters?.let {
                    charAdapter.setCharacters(it)
                }
            }
        )

        return view
    }

    inner class CharacterListAdapter(): RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

        private var chars = emptyList<Character>()

        internal fun setCharacters(characters: List<Character>) {
            this.chars = characters
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return chars.size
        }

        override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
            holder.view.findViewById<TextView>(R.id.title).text = chars[position].name
            holder.view.findViewById<TextView>(R.id.race_class_info).text =
                """${chars[position].race} ${chars[position].charClass}"""
            holder.itemView.setOnClickListener{
                holder.view.findNavController().navigate(R.id.action_listFragment_to_detailFragment,
                    bundleOf("name" to chars[position].name, "charClass" to chars[position].charClass,
                        "race" to chars[position].race, "level" to chars[position].level,
                        "strength" to chars[position].str, "dexterity" to chars[position].dex,
                        "constitution" to chars[position].con, "intelligence" to chars[position].int,
                        "wisdom" to chars[position].wis, "charisma" to chars[position].cha)
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
            return CharacterViewHolder(v)
        }

        inner class CharacterViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){

            }
        }
    }

}
