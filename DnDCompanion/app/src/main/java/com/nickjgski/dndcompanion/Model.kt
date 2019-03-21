package com.nickjgski.dndcompanion

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Model(application: Application): AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: CharacterRepository
    var allCharacters: LiveData<List<Character>>

    init {
        val charsDao = CharacterRoomDatabase.getDatabase(application).charDao()
        repository = CharacterRepository(charsDao)
        allCharacters = repository.allCharacters
    }

    fun insert(character: Character) = scope.launch (Dispatchers.IO) {
        repository.insert(character)
    }

    fun delete(character: Character) = scope.launch (Dispatchers.IO) {
        repository.delete(character)
    }

    fun deleteAll() = scope.launch (Dispatchers.IO){
        repository.deleteAll()
    }

}