package com.nickjgski.moviedb


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.*
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
        var view = inflater.inflate(R.layout.fragment_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = this.arguments?.getString("title")
        date = this.arguments?.getString("date")
        overview = this.arguments?.getString("overview")
        poster_path = this.arguments?.getString("poster")

        var likeButton = view.findViewById<Button>(R.id.favButton)

        val model = activity?.let { of(it).get(MovieViewModel::class.java) }
        var liked = model?.favorites?.contains(title)
        if(liked!!) {
            Log.d("Open", "Movie is liked")
            likeButton.setText(R.string.unlike_button_text)
        } else {
            Log.d("Open", "Movie is not liked")
            likeButton.setText(R.string.favorite_button_text)
        }

        likeButton.setOnClickListener {
            if(liked!!) {
                Log.d("Liked", "Unliking")
                model?.removeFromFavorites(title)
                liked = false
                likeButton.setText(R.string.favorite_button_text)
            } else {
                Log.d("Liked", "Liking")
                model?.addToFavorites(title)
                liked = true
                likeButton.setText(R.string.unlike_button_text)
            }
        }

        (view.findViewById(R.id.title) as TextView).text = title
        (view.findViewById(R.id.rating) as TextView).text = date
        (view.findViewById(R.id.overview) as TextView).text = overview
        Glide.with(this@DetailFragment).load(resources.getString(R.string.picture_base_url)+poster_path).apply( RequestOptions().override(512, 512)).into(view.findViewById(R.id.poster))
    }


}
