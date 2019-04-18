package com.nickjgski.dndcompanion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class CharacterRoomDatabase: RoomDatabase() {

    abstract fun charDao(): CharacterDao

    abstract fun weaponDao(): WeaponDao

    companion object {
        @Volatile
        private var INSTANCE: CharacterRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): CharacterRoomDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterRoomDatabase::class.java,
                    "Character_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}