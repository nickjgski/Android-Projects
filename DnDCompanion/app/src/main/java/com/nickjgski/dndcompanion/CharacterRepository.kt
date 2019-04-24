package com.nickjgski.dndcompanion

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CharacterRepository(private val charDao: CharacterDao) {

    val allCharacters: LiveData<List<Character>> = charDao.getAllCharacters()

    @WorkerThread
    suspend fun getCharacter(name: String): Character {
        return charDao.getCharacter(name)
    }

    @WorkerThread
    suspend fun insert(character: Character) {
        charDao.insertCharacter(character)
    }

    @WorkerThread
    suspend fun delete(character: Character) {
        charDao.deleteCharacter(character)
    }

    @WorkerThread
    suspend fun deleteAll() {
        charDao.deleteAll()
    }

}