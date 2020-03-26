package com.evacipated.cardcrawl.mod.humilty.patches.exordium

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.exordium.Hexaghost
import com.megacrit.cardcrawl.monsters.exordium.HexaghostOrb
import javassist.CodeConverter
import javassist.CtBehavior
import javassist.CtClass
import javassist.bytecode.CodeAttribute
import javassist.bytecode.CodeIterator
import javassist.bytecode.ConstPool
import javassist.bytecode.Opcode
import javassist.convert.Transformer

class Pentaghost {
    @SpirePatch(
        clz = Hexaghost::class,
        method = SpirePatch.CONSTRUCTOR
    )
    class NameAndInfernoHits {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Hexaghost, @ByRef ___infernoHits: Array<Int>) {
                __instance.name = CardCrawlGame.languagePack.getMonsterStrings(HumilityMod.makeID(Hexaghost.ID)).NAME
                ___infernoHits[0] = 5
            }
        }
    }

    @SpirePatch(
        clz = Hexaghost::class,
        method = "createOrbs"
    )
    class CreateOrbs {
        companion object {
            @JvmStatic
            fun Prefix(__instance: Hexaghost, ___orbs: ArrayList<HexaghostOrb>): SpireReturn<Unit?> {
                ___orbs.add(HexaghostOrb( -90f, 380f, ___orbs.size))
                ___orbs.add(HexaghostOrb(  90f, 380f, ___orbs.size))
                ___orbs.add(HexaghostOrb( 140f, 220f, ___orbs.size))
                ___orbs.add(HexaghostOrb(   0f, 120f, ___orbs.size))
                ___orbs.add(HexaghostOrb(-140f, 220f, ___orbs.size))
                return SpireReturn.Return(null)
            }
        }
    }

    @SpirePatch(
        clz = Hexaghost::class,
        method = "takeTurn"
    )
    class ChangeInitialAttackTimes {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.instrument(object : CodeConverter() {
                    init {
                        transformers = TransformBipush(transformers, 6, 5)
                        transformers = TransformBipush(transformers, 12, 10)
                    }
                })
            }
        }

        class TransformBipush(next: Transformer?, private val from: Int, private val to: Int) : Transformer(next) {
            var codeAttr: CodeAttribute? = null

            override fun initialize(cp: ConstPool?, attr: CodeAttribute?) {
                codeAttr = attr
            }

            override fun transform(clazz: CtClass, pos: Int, iterator: CodeIterator, cp: ConstPool?): Int {
                val c = iterator.byteAt(pos)
                if (c == Opcode.BIPUSH) {
                    val v = iterator.byteAt(pos + 1)
                    if (v == from) {
                        iterator.writeByte(to, pos + 1)
                    }
                }
                return pos
            }
        }
    }

    @SpirePatch(
        clz = Hexaghost::class,
        method = "usePreBattleAction"
    )
    class SkipActivationTurn {
        companion object {
            @JvmStatic
            fun Postfix(__instance: Hexaghost) {
                __instance.createIntent()
                __instance.takeTurn()
            }
        }
    }

    @SpirePatch(
        clz = Hexaghost::class,
        method = "changeState"
    )
    class SkipOrbCycle {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class
            )
            fun Insert(__instance: Hexaghost, stateName: String, @ByRef ___orbActiveCount: Array<Int>) {
                if (___orbActiveCount[0] == 5) {
                    ___orbActiveCount[0] = 6
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.FieldAccessMatcher(Hexaghost::class.java, "orbActiveCount")
                val matchers = listOf<Matcher>(
                    finalMatcher,
                    finalMatcher,
                    finalMatcher
                )
                return LineFinder.findInOrder(ctBehavior, matchers, finalMatcher)
            }
        }
    }
}
