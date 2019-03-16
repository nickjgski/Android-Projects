package com.nickjgski.dndcompanion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Model(application: Application): AndroidViewModel(application) {

    val characters = ArrayList<Character>()
    val numChars = MutableLiveData<Int>()

    init {
        numChars.value = 0
    }

    fun addCharacter(character: Character) {
        characters.add(character)
        numChars.postValue(characters.size)
    }

    fun removeCharacter(character: Character) {
        characters.remove(character)
        numChars.postValue(characters.size)
    }

}