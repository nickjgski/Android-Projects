package com.nickjgski.dndcompanion

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Delete
import androidx.room.Update



@Dao
interface WeaponDao {

    @Insert
    fun insertWeapon(weapon: Weapon)

    @Update
    fun update(weapon: Weapon)

    @Delete
    fun deleteWeapon(weapon: Weapon)

    @Query("SELECT * FROM weapon WHERE characterName = name")
    fun findWeaponsForChar(name: String): LiveData<List<Weapon>>

}