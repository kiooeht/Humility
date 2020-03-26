package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.modthespire.lib.ByRef
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin

@SpirePatch(
    clz = Lagavulin::class,
    method = SpirePatch.CONSTRUCTOR
)
class FastLagavulin {
    companion object {
        @JvmStatic
        fun Postfix(__instance: Lagavulin, setAsleep: Boolean, @ByRef ___idleCount: Array<Int>) {
            ___idleCount[0] = 2
        }
    }
}
