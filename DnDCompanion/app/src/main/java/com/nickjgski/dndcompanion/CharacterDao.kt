package com.nickjgski.dndcompanion

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table order BY  name ASC")
    fun getAllCharacters(): LiveData<List<Character>>

    @Query("SELECT * FROM character_table WHERE name LIKE :searchName")
    fun getCharacter(searchName: String): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: Character)

    @Delete
    fun deleteCharacter(character: Character)

    @Query("DELETE FROM character_table")
    fun deleteAll()

}