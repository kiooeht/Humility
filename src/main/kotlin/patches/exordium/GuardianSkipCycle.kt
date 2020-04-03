package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian

class GuardianSkipCycle {
    @SpirePatch(
        clz = TheGuardian::class,
        method = "usePreBattleAction"
    )
    class Skip {
        companion object {
            @JvmStatic
            fun Postfix(__instance: TheGuardian, ___TWINSLAM_NAME: String, ___twinSlamDamage: Int) {
                __instance.state.setAnimation(0, "defensive", true)
                __instance.changeState("Defensive Mode")
                __instance.state.setAnimation(0, "defensive", true)
                AbstractDungeon.actionManager.addToBottom(object : AbstractGameAction() {
                    override fun update() {
                        __instance.currentBlock = 0
                        isDone = true
                    }
                })
                TheGuardian::class.java.getDeclaredMethod("useCloseUp")?.let {
                    it.isAccessible = true
                    it.invoke(__instance)
                }
                __instance.setMove(___TWINSLAM_NAME, 4, AbstractMonster.Intent.ATTACK_BUFF, ___twinSlamDamage, 2, true)
                __instance.createIntent()
            }
        }
    }
}
