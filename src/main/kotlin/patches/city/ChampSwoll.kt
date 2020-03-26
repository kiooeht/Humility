package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.city.Champ
import com.megacrit.cardcrawl.powers.StrengthPower

class ChampSwoll {
    @SpirePatch(
        clz = Champ::class,
        method = "usePreBattleAction"
    )
    class StartingStrength {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Champ) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, StrengthPower(__instance, 3), 3))
            }
        }
    }
}
