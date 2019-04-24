package com.nickjgski.dndcompanion

import androidx.room.*

import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = arrayOf(ForeignKey(
    entity = Character::class,
    parentColumns = arrayOf("name"),
    childColumns = arrayOf("characterName"),
    onDelete = CASCADE)),
    indices = [Index("characterName")])

data class Weapon(@PrimaryKey @ColumnInfo(name = "name") var name: String,
                  var base: Int,
                  var ability: String,
                  var modifier: Int,
                  var characterName: String)