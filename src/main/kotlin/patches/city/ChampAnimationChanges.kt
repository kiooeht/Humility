package com.evacipated.cardcrawl.mod.humilty.patches.city

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.esotericsoftware.spine.Skeleton
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.city.Champ
import javassist.CtBehavior

class ChampAnimationChanges {
    @SpirePatch(
        clz = Champ::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class RemoveShield {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Champ, ___skeleton: Skeleton) {
                arrayOf("shield", "handle").forEach { slotName ->
                    ___skeleton.findSlot(slotName)?.let { slot ->
                        slot.attachment = null
                    }
                }
            }
        }
    }

    @SpirePatch(
        clz = AbstractMonster::class,
        method = "render"
    )
    class BiggerSword {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: AbstractMonster, sb: SpriteBatch, ___skeleton: Skeleton) {
                if (__instance is Champ) {
                    ___skeleton.findBone("Arm_L_3")?.setScale(2f)
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
