package com.nickjgski.dndcompanion

data class Weapon(var damage: Int,
                  var base: Int,
                  var proficient: Boolean,
                  var ability: Abilities,
                  var spell: Boolean,
                  var modifier: Int)