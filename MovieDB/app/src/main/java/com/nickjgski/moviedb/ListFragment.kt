package com.nickjgski.moviedb


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.DateFormat
import java.text.SimpleDateFormat


class ListFragment : Fragment() {

    private var sortBy: String = "Title"
    private var favOn = false
    private val adapterTitle = MovieListAdapter()
    private val adapterRating = MovieListAdapter()
    private var model: MovieViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.movie_list)
        recyclerView.adapter = adapterTitle
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        var favorites = view.findViewById<Button>(R.id.favorites)
        model = activity?.let { of(it).get(MovieViewModel::class.java) }

        model?.allMoviesByTitle?.observe(
            this,
            Observer<List<Movie>>{ movies ->
                movies?.let{
                    adapterTitle.setMovies(it)
                }
            }
        )

        model?.allMoviesByRating?.observe(
            this,
            Observer<List<Movie>>{ movies ->
                movies?.let{
                    adapterRating.setMovies(it)
                }
            }
        )

        val spinner: Spinner = view.findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.sort_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sortBy = parent?.getItemAtPosition(position).toString()
                changeAdapter(recyclerView, favorites)
            }
        }

        (view.findViewById<Button>(R.id.refresh)).setOnClickListener{
            model?.refreshMovies(1)
        }


        favorites.setOnClickListener {
            favOn = !favOn
            changeAdapter(recyclerView, favorites)
        }

        return view
    }

    fun changeAdapter(recyclerView: RecyclerView, favorites: Button) {
        if(sortBy == "Title") {
            recyclerView.adapter = adapterTitle
        } else {
            recyclerView.adapter = adapterRating
        }
        if(favOn) {
            favorites.setText("Favorites")
            (recyclerView.adapter as MovieListAdapter).restore()
        } else {
            favorites.setText("All")
            (recyclerView.adapter as MovieListAdapter).filterFav()
        }
    }

    inner class MovieListAdapter():
        RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){
        private var movies = emptyList<Movie>()
        private var moviesBackup = emptyList<Movie>()

        internal fun setMovies(movies: List<Movie>) {
            moviesBackup = movies
            this.movies = movies
            notifyDataSetChanged()
        }

        fun filterFav() {

            movies = movies.filter {model?.favorites?.contains(it.title) as Boolean}

            notifyDataSetChanged()

        }

        fun restore(){

            movies = moviesBackup
            notifyDataSetChanged()
        }


        override fun getItemCount(): Int {

            return movies.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {


            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
            return MovieViewHolder(v)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {


            //holder.bindItems(movieList[position])

            Glide.with(this@ListFragment).load(resources.getString(R.string.picture_base_url)+movies[position].poster_path).apply( RequestOptions().override(128, 128)).into(holder.view.findViewById(R.id.poster))
            holder.view.findViewById<TextView>(R.id.title).text=movies[position].title
            holder.view.findViewById<TextView>(R.id.rating).text=movies[position].vote_average.toString()
            holder.itemView.setOnClickListener{
                var pattern: String = "MM/dd/yyyy"
                var df: DateFormat = SimpleDateFormat(pattern)
                var date: String = df.format(movies[position].release_date)
                holder.view.findNavController().navigate(R.id.action_listFragment_to_detailFragment,
                    bundleOf("title" to movies[position].title, "date" to date,
                        "overview" to movies[position].overview, "poster" to movies[position].poster_path,
                        "id" to movies[position].id))
            }

        }


        inner class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){

                Log.d("movies2019","list tap ")
                if (view != null) {


                }
            }
        }
    }


}
