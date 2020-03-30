package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.city.Byrd
import com.megacrit.cardcrawl.powers.StrengthPower

@SpirePatch(
    clz = Byrd::class,
    method = "usePreBattleAction"
)
class ByrdStrength {
    companion object {
        @JvmStatic
        fun Postfix(__instance: Byrd) {
            AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, StrengthPower(__instance, 1), 1))
        }
    }
}
