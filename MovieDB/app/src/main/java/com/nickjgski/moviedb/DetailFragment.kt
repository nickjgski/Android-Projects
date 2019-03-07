package com.nickjgski.moviedb


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class DetailFragment : Fragment() {

    var title: String? = null
    var date: String? = null
    var overview: String? = null
    var poster_path: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = this.arguments?.getString("title")
        date = this.arguments?.getString("date")
        overview = this.arguments?.getString("overview")
        poster_path = this.arguments?.getString("poster")

        (view.findViewById(R.id.title) as TextView).text = title
        (view.findViewById(R.id.rating) as TextView).text = date
        (view.findViewById(R.id.overview) as TextView).text = overview
        Glide.with(this@DetailFragment).load(resources.getString(R.string.picture_base_url)+poster_path).apply( RequestOptions().override(512, 512)).into(view.findViewById(R.id.poster))
    }


}
