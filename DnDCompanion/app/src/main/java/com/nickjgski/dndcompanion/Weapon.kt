package com.nickjgski.dndcompanion

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = arrayOf(ForeignKey(
    entity = Character::class,
    parentColumns = arrayOf("name"),
    childColumns = arrayOf("characterName"),
    onDelete = CASCADE)))

data class Weapon(@PrimaryKey var name: String,
                  var damage: Int,
                  var base: Int,
                  var proficient: Boolean,
                  var ability: Abilities,
                  var spell: Boolean,
                  var modifier: Int,
                  var characterName: String)