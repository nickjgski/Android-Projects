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
import android.R.id.message
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging


class Model(application: Application): AndroidViewModel(application) {

    companion object {
        fun sendNotificationToUser(user: String, message: String) {
            val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://dndcompanionserver.firebaseio.com/")
            var ref: DatabaseReference = database.reference
            val notifications = ref.child("notificationRequests")
            Log.d("SendAttempt", "Got notification ref")
            val notification = HashMap<String, String>()
            notification["username"] = user
            notification["message"] = message

            notifications.push().setValue(notification
            ) { databaseError, databaseReference ->
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.message)
                } else {
                    System.out.println("Data saved successfully.")
                }
            }
        }
    }

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
        subscribe()
    }

    private fun subscribe() {
        var mFBmessage = FirebaseMessaging.getInstance()
        allCharacters.value?.let {
            for (character in it) {
                mFBmessage.subscribeToTopic("user_${character.name}")
            }
        }
    }

    fun insertCharacter(character: Character) = scope.launch (Dispatchers.IO) {
        FirebaseMessaging.getInstance().subscribeToTopic("user_${character.name}")
        charRepo.insert(character)
    }

    fun deleteCharacter(name: String) = scope.launch (Dispatchers.IO) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("user_$name")
        charRepo.delete(charRepo.getCharacter(name))
    }

    fun deleteAllCharacters() = scope.launch (Dispatchers.IO){
        charRepo.deleteAll()
    }

    fun getCharacterWeapons(name: String): LiveData<List<Weapon>> {
        return weaponRepo.getWeapons(name)
    }

    fun insertWeapon(weapon: Weapon) = scope.launch (Dispatchers.IO) {
        weaponRepo.insert(weapon)
    }

}