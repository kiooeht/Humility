package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addEscape
import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.DivineProtectionPower
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ChangeStateAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.city.Healer
import javassist.CtBehavior

class CenturionDivineProtection {
    @SpirePatch(
        clz = Healer::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class Add {
        companion object {
            private const val AMT = 10

            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addPreBattleAction(::doPreBattleAction)
            }

            @JvmStatic
            fun doPreBattleAction(healer: Healer) {
                AbstractDungeon.actionManager.addToBottom(ChangeStateAction(healer, "STAFF_RAISE"))
                AbstractDungeon.actionManager.addToBottom(WaitAction(0.25f))
                if (AbstractDungeon.getMonsters().monsters.count { it is Healer && !it.isDeadOrEscaped } <= 1) {
                    AbstractDungeon.getMonsters().monsters
                        .filterNot { it is Healer || it.isDeadOrEscaped }
                        .forEach {
                            AbstractDungeon.actionManager.addToBottom(
                                ApplyPowerAction(it, healer, DivineProtectionPower(it, healer, AMT), AMT)
                            )
                        }
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
                            RemoveSpecificPowerAction(it, __instance, DivineProtectionPower.POWER_ID)
                        )
                    }
            }
        }
    }

    @SpirePatch(
        clz = Healer::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class Escape {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addEscape((Remove)::Prefix)
            }
        }
    }
}
