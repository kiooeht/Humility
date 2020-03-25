package com.evacipated.cardcrawl.mod.humilty.powers

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.powers.AbstractPower

abstract class AbstractHumilityPower(
    id: String,
    regionName: String = id.removePrefix(HumilityMod.makeID(""))
) : AbstractPower() {
    protected val powerStrings: PowerStrings
    protected val NAME: String
    protected val DESCRIPTIONS: Array<String>

    init {
        ID = id
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID)
        NAME = powerStrings.NAME
        name = NAME
        DESCRIPTIONS = powerStrings.DESCRIPTIONS
        loadRegion(regionName)
    }

    final override fun loadRegion(fileName: String?) {
        region48 = powerAtlas?.findRegion("48/$fileName")
        region128 = powerAtlas?.findRegion("128/$fileName")

        if (region48 == null && region128 == null) {
            super.loadRegion(fileName)
        }
    }

    companion object {
        private val powerAtlas: TextureAtlas? =
            HumilityMod.assets.loadAtlas(HumilityMod.assetPath("images/powers/powers.atlas"))
    }
}
