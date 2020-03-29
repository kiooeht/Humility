package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.esotericsoftware.spine.Skeleton
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.GremlinTsundere
import javassist.CtBehavior

class GremlinTsundereAnimationChanges {
    @SpirePatch(
        clz = AbstractMonster::class,
        method = "render"
    )
    class BiggerShield {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: AbstractMonster, sb: SpriteBatch, ___skeleton: Skeleton) {
                if (__instance is GremlinTsundere) {
                    ___skeleton.findBone("shield")?.setScale(1.4f)
                }
            }
        }
        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.MethodCallMatcher(Skeleton::class.java, "updateWorldTransform")
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }
}
