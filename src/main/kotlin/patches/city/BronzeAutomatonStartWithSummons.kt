package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton

@SpirePatch(
    clz = BronzeAutomaton::class,
    method = "usePreBattleAction"
)
class BronzeAutomatonStartWithSummons {
    companion object {
        @JvmStatic
        fun Postfix(__instance: BronzeAutomaton) {
            __instance.createIntent()
            __instance.takeTurn()
        }
    }
}
