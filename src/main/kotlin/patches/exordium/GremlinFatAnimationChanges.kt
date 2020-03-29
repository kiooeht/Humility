package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.esotericsoftware.spine.Skeleton
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat
import javassist.CtBehavior

class GremlinFatAnimationChanges {
    @SpirePatch(
        clz = AbstractMonster::class,
        method = "render"
    )
    class Fatter {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: AbstractMonster, sb: SpriteBatch, ___skeleton: Skeleton) {
                if (__instance is GremlinFat) {
                    ___skeleton.findBone("root")?.setScale(1.7f)
                    ___skeleton.findBone("weapon")?.setScale(1f / 1.7f)
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
