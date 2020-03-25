package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.modthespire.lib.ByRef
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.monsters.exordium.JawWorm

@SpirePatch(
    clz = JawWorm::class,
    method = SpirePatch.CONSTRUCTOR,
    paramtypez = [Float::class, Float::class, Boolean::class]
)
class JawWormAlwaysHardMode {
    companion object {
        @JvmStatic
        fun Prefix(__instance: JawWorm, x: Float, y: Float, @ByRef hard: Array<Boolean>) {
            hard[0] = true
        }
    }
}
