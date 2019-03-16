package com.nickjgski.dndcompanion

data class Character(var name: String,
                     var charClass: String,
                     var race: String,
                     var level: Int,
                     var abilityScores: IntArray,
                     var armorClass: Int,
                     var weapons: ArrayList<Weapon>)