package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat
import com.megacrit.cardcrawl.powers.SlowPower
import javassist.CtBehavior

class GremlinFatSlow {
    @SpirePatch(
        clz = GremlinFat::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class AddSlowAndMoreHP {
        companion object {
            @JvmStatic
            fun Postfix(__instance: GremlinFat, x: Float, y: Float) {
                __instance.currentHealth *= 3
                __instance.maxHealth = __instance.currentHealth
            }

            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addPreBattleAction(::doPreBattleAction)
            }

            @JvmStatic
            fun doPreBattleAction(__instance: GremlinFat) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, SlowPower(__instance, 0)))
            }
        }
    }
}
