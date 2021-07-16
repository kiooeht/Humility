package com.evacipated.cardcrawl.mod.humilty.patches.beyond

import com.evacipated.cardcrawl.mod.humilty.patches.utils.addPreBattleAction
import com.evacipated.cardcrawl.mod.humilty.powers.VenomStrikesPower
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.beyond.Reptomancer
import com.megacrit.cardcrawl.monsters.beyond.SnakeDagger
import javassist.CtBehavior

class ReptomancerVenom {
    companion object {
        private const val VENOM_AMT = 2
    }

    @SpirePatch(
        clz = Reptomancer::class,
        method = "usePreBattleAction"
    )
    class AddVenomToReptomancer {
        companion object {
            @JvmStatic
            fun Postfix(___m: Reptomancer) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(___m, ___m, VenomStrikesPower(___m, VENOM_AMT), VENOM_AMT))
            }
        }
    }

    @SpirePatch(
        clz = SnakeDagger::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class AddVenomToDaggers {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.addPreBattleAction(::doPreBattleAction)
            }

            @JvmStatic
            fun doPreBattleAction(___m: SnakeDagger) {
                AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(___m, ___m, VenomStrikesPower(___m, VENOM_AMT), VENOM_AMT))
            }
        }
    }

    @SpirePatch2(
        clz = SpawnMonsterAction::class,
        method = "update"
    )
    class AddVenomToSpawnDaggers {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(___m: AbstractMonster) {
                if (___m is SnakeDagger) {
                    AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(___m, ___m, VenomStrikesPower(___m, VENOM_AMT), VENOM_AMT))
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.FieldAccessMatcher(SpawnMonsterAction::class.java, "minion")
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }
}
