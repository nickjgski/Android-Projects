package com.nickjgski.dndcompanion

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class Character(@PrimaryKey @ColumnInfo(name = "name") var name: String,
                     @ColumnInfo(name = "charClass") var charClass: String,
                     @ColumnInfo(name = "race") var race: String,
                     @ColumnInfo(name = "level") var level: Int,
                     @ColumnInfo(name = "str") var str: Int,
                     @ColumnInfo(name = "dex") var dex: Int,
                     @ColumnInfo(name = "con") var con: Int,
                     @ColumnInfo(name = "wis") var wis: Int,
                     @ColumnInfo(name = "int") var int: Int,
                     @ColumnInfo(name = "cha") var cha: Int,
                     @ColumnInfo(name = "armorClass") var armorClass: Int
                    ) {
                        constructor() : this("", "", "", 1, 10, 10, 10, 10, 10, 10, 10)
                    }