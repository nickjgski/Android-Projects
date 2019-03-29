package com.nickjgski.dndcompanion

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
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class DetailFragment : Fragment() {

    var name: String? = null
    var charClass: String? = null
    var charRace: String? = null
    var level: Int? = null
    var str: Int? = null
    var dex: Int? = null
    var con: Int? = null
    var int: Int? = null
    var wis: Int? = null
    var cha: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        name = this?.arguments?.getString("name")
        charClass = this?.arguments?.getString("charClass")
        charRace = null
        level = null
        str = null
        dex = null
        con = null
        int = null
        wis = null
        cha = null
        
    }

}
