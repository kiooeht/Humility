package com.evacipated.cardcrawl.mod.humilty.patches.ending

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.evacipated.cardcrawl.mod.humilty.powers.DrawDownPower
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.status.Dazed
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower
import javassist.CtBehavior

@SpirePatch(
    clz = CorruptHeart::class,
    method = "takeTurn"
)
class CorruptHeartDebuffs {
    companion object {
        private val powerStrings by lazy { CardCrawlGame.languagePack.getPowerStrings(HumilityMod.makeID(EnergyDownPower.POWER_ID)) }

        @JvmStatic
        @SpireInsertPatch(
            locator = Locator::class
        )
        fun Insert(__instance: CorruptHeart) {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    AbstractDungeon.player,
                    __instance,
                    EnergyDownPower(AbstractDungeon.player, 1).also { it.name = powerStrings.NAME },
                    1
                )
            )
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    AbstractDungeon.player,
                    __instance,
                    DrawDownPower(AbstractDungeon.player, -1),
                    -1
                )
            )
        }
    }

    private class Locator : SpireInsertLocator() {
        override fun Locate(ctBehavior: CtBehavior?): IntArray {
            val finalMatcher = Matcher.NewExprMatcher(Dazed::class.java)
            return LineFinder.findInOrder(ctBehavior, finalMatcher)
        }
    }
}
