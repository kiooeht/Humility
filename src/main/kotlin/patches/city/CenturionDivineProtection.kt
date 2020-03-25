package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.evacipated.cardcrawl.mod.humilty.powers.DivineProtectionPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ChangeStateAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.city.Healer
import javassist.CannotCompileException
import javassist.CtBehavior
import javassist.CtNewMethod

class CenturionDivineProtection {
    companion object {
        private val POWER_ID = HumilityMod.makeID("DivineProtection")
    }

    @SpirePatch(
        clz = Healer::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class Add {
        companion object {
            private const val AMT = 10

            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                val ctClass = ctBehavior.declaringClass
                val pool = ctClass.classPool
                var method = CtNewMethod.make(
                    "void usePreBattleAction() {}",
                    ctClass
                )
                try {
                    ctClass.addMethod(method)
                } catch (e: CannotCompileException) {
                    e.printStackTrace()
                    method = ctClass.getDeclaredMethod("usePreBattleAction")
                }

                method.insertBefore("${Add::class.qualifiedName}.doPreBattleAction(this);")
            }

            @JvmStatic
            fun doPreBattleAction(healer: Healer) {
                AbstractDungeon.actionManager.addToBottom(ChangeStateAction(healer, "STAFF_RAISE"))
                AbstractDungeon.actionManager.addToBottom(WaitAction(0.25f))
                AbstractDungeon.getMonsters().monsters
                    .filterNot { it == healer || it.isDeadOrEscaped }
                    .forEach {
                        AbstractDungeon.actionManager.addToBottom(
                            ApplyPowerAction(it, healer, DivineProtectionPower(it, healer, AMT), AMT)
                        )
                    }
            }
        }
    }

    @SpirePatch(
        clz = Healer::class,
        method = "die"
    )
    class Remove {
        companion object {
            @JvmStatic
            fun Prefix(__instance: Healer) {
                AbstractDungeon.getMonsters().monsters
                    .filterNot { it.isDeadOrEscaped }
                    .forEach {
                        AbstractDungeon.actionManager.addToBottom(
                            RemoveSpecificPowerAction(it, __instance, POWER_ID)
                        )
                    }
            }
        }
    }
}
