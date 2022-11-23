package com.evacipated.cardcrawl.mod.humilty.powers

import com.evacipated.cardcrawl.mod.humilty.HumilityMod
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster

class FatedShapesPower(
    owner: AbstractCreature,
    private val linked: AbstractMonster
) : AbstractHumilityPower(POWER_ID, "heartDef") {
    companion object {
        val POWER_ID = HumilityMod.makeID("FatedShapes")
    }

    init {
        this.owner = owner
        updateDescription()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0].format(FontHelper.colorString(owner.name, "y"), FontHelper.colorString(linked.name, "y"))
    }

    override fun wasHPLost(info: DamageInfo?, damageAmount: Int) {
        super.wasHPLost(info, damageAmount)
        linked.currentHealth -= damageAmount
        if (linked.currentHealth < 0) {
            linked.currentHealth = 0
        }
        linked.healthBarUpdatedEvent()
    }

    override fun onDeath() {
        // Remove power from the other to avoid infinite loop calling each other back and forth
        linked.powers.removeIf { it.ID == POWER_ID }
        linked.die()
    }
}
