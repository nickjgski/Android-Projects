package com.nickjgski.stopwatch


import android.content.res.Configuration
import android.os.Bundle
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


class ControlFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_control, container, false)

        val model = activity?.let { ViewModelProviders.of(it).get(ViewModel::class.java)}

        model?.getElapsedTime()?.observe(this, Observer<Long>{ time ->

            var minutes = time/60
            var seconds = time % 60
            view?.findViewById<TextView>(R.id.time)?.text = String.format("%02d", minutes) + ":" +
                    String.format("%02d", seconds)

        })

        if(activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            view.findViewById<Button>(R.id.lapsButton).setOnClickListener {
                view.findNavController().navigate(R.id.action_controlFragment_to_listFragment)
            }
        }

        view.findViewById<Button>(R.id.startstop).setOnClickListener{
            model?.startstop()
        }

        view.findViewById<Button>(R.id.clear).setOnClickListener{
            model?.reset()
        }

        view.findViewById<Button>(R.id.lap).setOnClickListener{
            model?.slowLapCapture()
        }

        return view
    }

}
