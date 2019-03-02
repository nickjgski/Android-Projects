package com.nickjgski.pp03


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController


class allFragment : Fragment() {

    private var model: ViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_all, container, false)

        model = activity?.let { ViewModelProviders.of(it).get(Model::class.java)}

        (model as Model?)?.getValues()?.observe(this, Observer<List<Int>> { values ->

            view.findViewById<TextView>(R.id.left).text = values[0].toString()
            view.findViewById<TextView>(R.id.middle).text = values[1].toString()
            view.findViewById<TextView>(R.id.right).text = values[2].toString()

        })

        view.findViewById<TextView>(R.id.left).setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                view.findNavController()?.navigate(R.id.action_allFragment_to_leftFragment)
            }
        })

        view.findViewById<TextView>(R.id.middle).setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                view.findNavController()?.navigate(R.id.action_allFragment_to_middleFragment)
            }
        })

        view.findViewById<TextView>(R.id.right).setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                view.findNavController()?.navigate(R.id.action_allFragment_to_rightFragment)
            }
        })

        view.findViewById<Button>(R.id.start).setOnClickListener{
            (model as Model?)?.slotMachineDraw()
        }

        view.findViewById<Button>(R.id.stop).setOnClickListener{
            (model as Model?)?.cancel()
        }

        return view

    }


}
