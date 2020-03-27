package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.esotericsoftware.spine.Skeleton
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.exordium.Cultist
import javassist.CtBehavior

@SpirePatch(
    clz = AbstractMonster::class,
    method = "render"
)
class CultistBiggerSticks {
    companion object {
        @JvmStatic
        @SpireInsertPatch(
            locator = Locator::class
        )
        fun Insert(__instance: AbstractMonster, sb: SpriteBatch, ___skeleton: Skeleton) {
            if (__instance is Cultist) {
                arrayOf("weaponleft", "weaponright").forEach { boneName ->
                    ___skeleton.findBone(boneName)?.setScale(1.6f)
                }
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
