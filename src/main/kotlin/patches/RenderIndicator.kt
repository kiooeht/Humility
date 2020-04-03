package com.evacipated.cardcrawl.mod.humilty.patches

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.ui.panels.TopPanel
import javassist.CtBehavior
import javassist.expr.ExprEditor
import javassist.expr.FieldAccess
import javassist.expr.MethodCall

class RenderIndicator {
    companion object {
        private const val COLOR = "#fc8403"
    }

    @SpirePatch(
        clz = TopPanel::class,
        method = "renderDungeonInfo"
    )
    class Display {
        companion object {
            @JvmStatic
            fun Instrument(): ExprEditor =
                object : ExprEditor() {
                    override fun edit(f: FieldAccess) {
                        if (f.isReader && f.fieldName == "isAscensionMode") {
                            f.replace("\$_ = true;")
                        }
                    }

                    private var count = 0
                    override fun edit(m: MethodCall) {
                        if (m.methodName == "renderFontLeftTopAligned") {
                            if (count > 0) {
                                m.replace(
                                    "if (${AbstractDungeon::class.qualifiedName}.ascensionLevel > 0) {" +
                                            "\$3 = \$3 + \" + [$COLOR]H[]\";" +
                                            "} else {" +
                                            "\$3 = \"[$COLOR]H[]\";" +
                                            "}" +
                                            "\$_ = \$proceed(\$\$);"
                                )
                            }
                            ++count
                        }
                    }
                }
        }
    }

    @SpirePatches(
        SpirePatch(
            clz = TopPanel::class,
            method = "setupAscensionMode"
        ),
        SpirePatch(
            clz = TopPanel::class,
            method = "updateAscensionHover"
        )
    )
    class SetupHitboxAndUpdate {
        companion object {
            @JvmStatic
            fun Instrument(): ExprEditor =
                object : ExprEditor() {
                    override fun edit(f: FieldAccess) {
                        if (f.isReader && f.fieldName == "isAscensionMode") {
                            f.replace("\$_ = true;")
                        }
                    }
                }
        }
    }

    @SpirePatch(
        clz = TopPanel::class,
        method = "setupAscensionMode"
    )
    class AddTooltipText {
        companion object {
            @JvmStatic
            @SpireInsertPatch(
                locator = Locator::class,
                localvars = ["sb"]
            )
            fun Insert(__instance: TopPanel, sb: StringBuilder) {
                sb.append(
                    (CardCrawlGame.languagePack.getUIString(HumilityMod.makeID("Humility"))?.TEXT?.get(0) ?: "[MISSING TEXT]")
                        .split(" ")
                        .map { "[$COLOR]$it" }
                        .joinToString(separator = " ") { it }
                )
                if (AbstractDungeon.ascensionLevel > 0) {
                    sb.append(" NL ")
                }
            }
        }

        private class Locator : SpireInsertLocator() {
            override fun Locate(ctBehavior: CtBehavior?): IntArray {
                val finalMatcher = Matcher.FieldAccessMatcher(AbstractDungeon::class.java, "ascensionLevel")
                return LineFinder.findInOrder(ctBehavior, finalMatcher)
            }
        }
    }
}
