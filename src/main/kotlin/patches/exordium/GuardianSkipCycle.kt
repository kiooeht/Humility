package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian
import com.megacrit.cardcrawl.powers.BarricadePower

class GuardianSkipCycle {
    @SpirePatch(
        clz = TheGuardian::class,
        method = "usePreBattleAction"
    )
    class Skip {
        companion object {
            @JvmStatic
            fun Postfix(__instance: TheGuardian, ___TWINSLAM_NAME: String, ___twinSlamDamage: Int, ___DEFENSIVE_MODE: String) {
                __instance.state.setAnimation(0, "defensive", true)
                __instance.changeState(___DEFENSIVE_MODE)
                __instance.state.setAnimation(0, "defensive", true)
                TheGuardian::class.java.getDeclaredMethod("useCloseUp")?.let {
                    it.isAccessible = true
                    it.invoke(__instance)
                }
                __instance.setMove(___TWINSLAM_NAME, 4, AbstractMonster.Intent.ATTACK_BUFF, ___twinSlamDamage, 2, true)
                __instance.createIntent()
            }
        }
    }

    @SpirePatch(
        clz = TheGuardian::class,
        method = "changeState"
    )
    class DefensiveModeBarricade {
        companion object {
            @JvmStatic
            fun Prefix(__instance: TheGuardian, stateName: String, ___DEFENSIVE_MODE: String, ___OFFENSIVE_MODE: String) {
                when (stateName) {
                    ___DEFENSIVE_MODE -> AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(__instance, __instance, BarricadePower(__instance))
                    )
                    ___OFFENSIVE_MODE -> AbstractDungeon.actionManager.addToBottom(
                        RemoveSpecificPowerAction(__instance, __instance, BarricadePower.POWER_ID)
                    )
                }
            }
        }
    }
}
