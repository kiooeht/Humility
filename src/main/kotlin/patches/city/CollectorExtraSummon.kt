package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.badlogic.gdx.math.MathUtils
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.city.TheCollector
import com.megacrit.cardcrawl.monsters.city.TorchHead
import javassist.CtBehavior

@SpirePatch(
    clz = TheCollector::class,
    method = "takeTurn"
)
class CollectorExtraSummon {
    companion object {
        @JvmStatic
        @SpireInsertPatch(
            locator = Locator::class
        )
        fun Insert(__instance: TheCollector, ___spawnX: Float, ___enemySlots: HashMap<Int, AbstractMonster>) {
            val i = ___enemySlots.size + 1
            val m = TorchHead(___spawnX + -185f * i, MathUtils.random(-5f, 25f))
            AbstractDungeon.actionManager.addToBottom(SFXAction("MONSTER_COLLECTOR_SUMMON"))
            AbstractDungeon.actionManager.addToBottom(SpawnMonsterAction(m, true))
            ___enemySlots[i] = m
        }
    }

    private class Locator : SpireInsertLocator() {
        override fun Locate(ctBehavior: CtBehavior?): IntArray {
            val finalMatcher = Matcher.FieldAccessMatcher(TheCollector::class.java, "initialSpawn")
            return LineFinder.findInOrder(ctBehavior, finalMatcher)
        }
    }
}
