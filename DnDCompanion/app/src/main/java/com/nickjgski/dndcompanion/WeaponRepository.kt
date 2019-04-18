package com.nickjgski.dndcompanion

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class WeaponRepository(private val weaponDao: WeaponDao) {

    fun getWeapons(name: String): LiveData<List<Weapon>> {
        return weaponDao.findWeaponsForChar(name)
    }

    @WorkerThread
    suspend fun insert(weapon: Weapon) {
        weaponDao.insertWeapon(weapon)
    }

    @WorkerThread
    suspend fun update(weapon: Weapon) {
        weaponDao.update(weapon)
    }

    @WorkerThread
    suspend fun delete(weapon: Weapon) {
        weaponDao.deleteWeapon(weapon)
    }

}