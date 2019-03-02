package com.nickjgski.tutorial02

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private val newsItems = ArrayList<NewsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initArray(newsItems)

        var view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        viewAdapter = RecyclerViewAdapter(newsItems, activity as MainActivity)

        recyclerView.adapter = viewAdapter

        return view
    }

    private fun initArray(myDataset: MutableList<NewsItem>) {

        myDataset.clear()

        myDataset.add(NewsItem("CS", Color.GRAY, getString(R.string.CS)))
        myDataset.add(NewsItem("ECE",Color.YELLOW,getString(R.string.ECE)))
        myDataset.add(NewsItem("MATH",Color.WHITE,getString(R.string.MATH)))
        myDataset.add(NewsItem("STAT",Color.MAGENTA, getString(R.string.STAT)))
        myDataset.add(NewsItem("PHYS",Color.RED,getString(R.string.PHYS)))

    }

}


class RecyclerViewAdapter(private val myDataset: ArrayList<NewsItem>, private val activity: MainActivity) :
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

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size

    class ViewHolder(private val view: View, private val activity: MainActivity) : RecyclerView.ViewHolder(view){

        fun bindItems(newsItem: NewsItem) {
            val dept: TextView = itemView.findViewById(R.id.dept)
            val news:TextView = itemView.findViewById(R.id.news)
            itemView.setBackgroundColor(newsItem.color)


            dept.text = newsItem.dept
            news.text = newsItem.content.substring(0,50)

            itemView.setOnClickListener {

            }
        }

    }


}



