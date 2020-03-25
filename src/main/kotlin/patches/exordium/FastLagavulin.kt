package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import basemod.ReflectionHacks
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin

@SpirePatch(
    clz = Lagavulin::class,
    method = SpirePatch.CONSTRUCTOR
)
class FastLagavulin {
    companion object {
        @JvmStatic
        fun Postfix(__instance: Lagavulin, setAsleep: Boolean) {
            ReflectionHacks.setPrivate(__instance, Lagavulin::class.java, "idleCount", 2)
        }
    }
}
