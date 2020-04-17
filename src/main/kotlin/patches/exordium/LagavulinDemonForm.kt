package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.mod.humilty.powers.DemonFormMonsterPower
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ReducePowerAction
import com.megacrit.cardcrawl.audio.MusicMaster
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin
import javassist.CtBehavior

class LagavulinDemonForm {
    @SpirePatch(
        clz = Lagavulin::class,
        method = "usePreBattleAction"
    )
    class Add {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Lagavulin, ___asleep: Boolean) {
                if (___asleep) {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            __instance,
                            __instance,
                            DemonFormMonsterPower(__instance, 3),
                            3
                        )
                    )
                }
            }
        }
    }

    @SpirePatch(
        clz = Lagavulin::class,
        method = "changeState"
    )
    class Remove {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: Lagavulin, stateName: String) {
                AbstractDungeon.actionManager.addToBottom(
                    ReducePowerAction(
                        __instance,
                        __instance,
                        DemonFormMonsterPower.POWER_ID,
                        3
                    )
                )
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.MethodCallMatcher(MusicMaster::class.java, "unsilenceBGM")
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }
}
