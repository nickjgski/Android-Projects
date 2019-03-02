package com.nickjgski.pp03


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class rightFragment : Fragment() {

    private var model: ViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_left, container, false)

        model = activity?.let { ViewModelProviders.of(it).get(Model::class.java)}

        (model as Model?)?.getValues()?.observe(this, Observer<List<Int>> { values ->

            view.findViewById<TextView>(R.id.leftBig).text = values[0].toString()

        })

        return view
    }


}
