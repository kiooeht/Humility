package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.evacipated.cardcrawl.mod.humilty.powers.DoubleTimePower
import com.evacipated.cardcrawl.mod.humilty.powers.RealTimePower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.beyond.TimeEater

class TimeEaterRealTime {
    @SpirePatch(
        clz = TimeEater::class,
        method = "usePreBattleAction"
    )
    class AddDoubleTime {
        companion object {
            @JvmStatic
            fun Postfix(__instance: TimeEater) {
                val power = if (HumilityMod.disableRealTime()) {
                    DoubleTimePower(__instance)
                } else {
                    RealTimePower(__instance)
                }
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, power))
            }
        }
    }
}
