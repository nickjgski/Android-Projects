package com.nickjgski.moviedb

data class Movies(val results: List<Movie>,
                  val total_pages: Int,
                  val page:Int
)