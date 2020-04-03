package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.SlimeCoatingPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_S
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_M
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_S
import javassist.CtBehavior

@SpirePatches(
    SpirePatch(
        clz = AcidSlime_S::class,
        method = "takeTurn"
    ),
    SpirePatch(
        clz = AcidSlime_M::class,
        method = "takeTurn"
    ),
    SpirePatch(
        clz = SpikeSlime_S::class,
        method = "takeTurn"
    ),
    SpirePatch(
        clz = SpikeSlime_M::class,
        method = "takeTurn"
    )
)
class SlimesCoating {
    companion object {
        @JvmStatic
        fun Raw (ctBehavior: CtBehavior) {
            ctBehavior.addPreBattleAction(::doPreBattleAction)
        }

        @JvmStatic
        fun doPreBattleAction(__instance: AbstractMonster) {
            AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, SlimeCoatingPower(__instance, 1), 1))
        }
    }
}
