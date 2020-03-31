package com.evacipated.cardcrawl.mod.humilty.patches.ending

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.ending.SpireShield
import com.megacrit.cardcrawl.monsters.ending.SpireSpear
import com.megacrit.cardcrawl.powers.BarricadePower

class SpireElitesBarricade {
    @SpirePatches(
        SpirePatch(
            clz = SpireShield::class,
            method = "usePreBattleAction"
        ),
        SpirePatch(
            clz = SpireSpear::class,
            method = "usePreBattleAction"
        )
    )
    class AddLifeLink {
        companion object {
            @JvmStatic
            fun Postfix(__instance: AbstractMonster) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, BarricadePower(__instance)))
            }
        }
    }
}
