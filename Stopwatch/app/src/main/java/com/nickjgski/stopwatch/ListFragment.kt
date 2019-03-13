package com.nickjgski.stopwatch


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private val lapItems = ArrayList<LapItem>()
    private var model: ViewModel? = null
    private var localList: ArrayList<Long>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_list, container, false)

        model = activity?.let { ViewModelProviders.of(it).get(ViewModel::class.java)}

        localList = model?.getLapTimes()

        model?.getListSize()?.observe(this, Observer<Int>{ size ->

            localList = model?.getLapTimes()
            createArray(lapItems)

        })

        recyclerView = view.findViewById(R.id.recycler_view)

        // This tells the recyclerview that we want to show our data items in a vertical list. We could do a horizontal list,
        // a grid or even something custom in order to display the data items.
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewAdapter = RecyclerViewAdapter(lapItems, activity as MainActivity)

        recyclerView.adapter = viewAdapter


        return view
    }

    private fun createArray(myDataset: ArrayList<LapItem>) {

        myDataset.clear()
        localList?.forEach {
            var minutes = it /60
            var seconds =  it % 60
            var formatted = String.format("%02d", minutes) + ":" +
                    String.format("%02d", seconds)
            myDataset.add(LapItem(formatted))
        }
        viewAdapter.notifyDataSetChanged()

    }

}

class RecyclerViewAdapter(private val myDataset: ArrayList<LapItem>, private val activity: MainActivity) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerViewAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerviewitem, parent, false)


        return ViewHolder(v, activity)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItems(myDataset[position])

    }

    class ViewHolder(private val view: View, private val activity: MainActivity) : RecyclerView.ViewHolder(view) {
        fun bindItems(lapItem: LapItem) {
            val lap: TextView = itemView.findViewById(R.id.lapInfo)
            lap.text = lapItem.text

        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

}