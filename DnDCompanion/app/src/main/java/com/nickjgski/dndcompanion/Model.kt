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

    private val charRepo: CharacterRepository
    private val weaponRepo: WeaponRepository
    var allCharacters: LiveData<List<Character>>

    init {
        val charsDao = CharacterRoomDatabase.getDatabase(application).charDao()
        charRepo = CharacterRepository(charsDao)
        allCharacters = charRepo.allCharacters
        val weaponDao = CharacterRoomDatabase.getDatabase(application).weaponDao()
        weaponRepo = WeaponRepository(weaponDao)
    }

    fun insertCharacter(character: Character) = scope.launch (Dispatchers.IO) {
        charRepo.insert(character)
    }

    fun deleteCharacter(character: Character) = scope.launch (Dispatchers.IO) {
        charRepo.delete(character)
    }

    fun deleteAllCharacters() = scope.launch (Dispatchers.IO){
        charRepo.deleteAll()
    }

    fun getCharacterWeapons(name: String): LiveData<List<Weapon>> {
        return weaponRepo.getWeapons(name)
    }

    fun insertWeapon(weapon: Weapon) = scope.launch (Dispatchers.IO) {

    }

}