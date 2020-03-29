package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.DeathRattlePower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.beyond.Repulsor
import javassist.CtBehavior

@SpirePatch(
    clz = Repulsor::class,
    method = SpirePatch.CONSTRUCTOR
)
class RepulsorDazeOnDeath {
    companion object {
        @JvmStatic
        fun Raw(ctBehavior: CtBehavior) {
            ctBehavior.addPreBattleAction(::doPreBattleAction)
        }

        @JvmStatic
        fun doPreBattleAction(__instance: Repulsor) {
            AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(__instance, __instance, DeathRattlePower(__instance, 2), 2))
        }
    }
}
