package com.evacipated.cardcrawl.mod.humilty.patches.events

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.events.exordium.DeadAdventurer

@SpirePatch(
    clz = DeadAdventurer::class,
    method = SpirePatch.STATICINITIALIZER
)
class DeadAdventurerNoHint {
    companion object {
        @JvmStatic
        fun Postfix() {
            arrayOf(3, 4, 5).forEach { i ->
                DeadAdventurer.DESCRIPTIONS[i] = "... "
            }
        }
    }
}
