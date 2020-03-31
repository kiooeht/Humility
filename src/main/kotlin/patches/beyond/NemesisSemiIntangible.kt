package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.SemiIntangiblePower
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RollMoveAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.beyond.Nemesis
import javassist.CtBehavior

class NemesisSemiIntangible {
    @SpirePatch(
        clz = Nemesis::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class AddSemiIntangible {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addPreBattleAction(::doPreBattleAction)
            }

            @JvmStatic
            fun doPreBattleAction(__instance: Nemesis) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                        __instance,
                        __instance,
                        SemiIntangiblePower(__instance, 5).also { it.atEndOfTurn(false) }, // needed so it removes at the end of the first turn
                        5
                    )
                )
            }
        }
    }

    @SpirePatch(
        clz = Nemesis::class,
        method = "takeTurn"
    )
    class TakeTurn {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: Nemesis) {
                if (!__instance.hasPower(SemiIntangiblePower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(
                        ApplyPowerAction(
                            __instance,
                            __instance,
                            SemiIntangiblePower(__instance, 5),
                            5
                        )
                    )
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.NewExprMatcher(RollMoveAction::class.java)
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }
}
