package com.evacipated.cardcrawl.mod.humilty.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches
import com.megacrit.cardcrawl.ui.campfire.RestOption
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect
import javassist.CodeConverter
import javassist.CtBehavior
import javassist.CtClass
import javassist.bytecode.Bytecode
import javassist.bytecode.CodeIterator
import javassist.bytecode.ConstPool
import javassist.bytecode.Opcode
import javassist.convert.Transformer

class WorseResting {
    @SpirePatch(
        clz = RestOption::class,
        method = SpirePatch.STATICINITIALIZER
    )
    class ChangeTextAmount {
        companion object {
            @JvmStatic
            fun Postfix() {
                RestOption.TEXT[3] = RestOption.TEXT[3].replace("30", "25")
            }
        }
    }

    @SpirePatches(
        SpirePatch(
            clz = RestOption::class,
            method = SpirePatch.CONSTRUCTOR
        ),
        SpirePatch(
            clz = CampfireSleepEffect::class,
            method = SpirePatch.CONSTRUCTOR
        )
    )
    class ChangePercentAmount {
        companion object {
            @JvmStatic
            fun Raw(ctBehavior: CtBehavior) {
                ctBehavior.instrument(object : CodeConverter() {
                    init {
                        transformers = TransformFloat(transformers, 0.3f, 0.25f)
                    }
                })
            }
        }
    }

    class TransformFloat(next: Transformer?, private val from: Float, private val to: Float) : Transformer(next) {
        override fun transform(clazz: CtClass, pos: Int, iterator: CodeIterator, cp: ConstPool?): Int {
            val c = iterator.byteAt(pos)
            if (c == Opcode.LDC) {
                val fromFIndex = iterator.byteAt(pos + 1)
                val ldcValue = cp?.getLdcValue(fromFIndex)
                if (ldcValue is Float && ldcValue == from) {
                    iterator.writeByte(Opcode.NOP, pos)
                    iterator.writeByte(Opcode.NOP, pos + 1)
                    val bytecode = Bytecode(cp)
                    bytecode.addFconst(to)
                    iterator.insert(pos, bytecode.get())
                }
            }
            return pos
        }
    }
}
