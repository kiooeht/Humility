# Humility
Humility is a difficulty mod that buffs (almost) every enemy in Slay the Spire while avoiding simple number buffs like increased HP or damage.
## Changelist

### General
* Vulnerable has been changed so that if the player if afflicted with it during the monsters' turn, monsters that act that turn will no longer suddenly deal increased damage.
	* This allows monsters that are not the last to act in a turn to apply Vulnerable without affecting the damage of monsters that act after them.
	* Note: This means Fungi Beast will no longer alter damage if it does during the monsters' turn from effects like Thorns or Lightning Orbs.

### Exordium

#### Events
* Dead Adventurer
	* No longer gives a hint at which Elite the player might fight

#### Normal Enemies
* Cultist
	* Attack also adds a Void into the discard pile
* Gremlin (Fat)
	* Starts with 3 times more HP than usual
	* Starts with Slow
* Gremlin (Mad)
	* Anger replaced with Strength Up (gain Strength each turn)
* Gremlin (Shield)
	* Gives 1 Buffer instead of Block
* Gremlin (Sneaky)
	* Starts with 10 Thievery
	* Escapes on turn 4
* Gremlin (Wizard)
	* Takes 1 fewer turns to charge up attack
* Jaw Worm
	* Always in Hard Mode
* Large Slimes
	* Splits at 75% HP instead of 50%
* Looter
	* Skips Smoke Bomb prep turn
* Louse
	* Regains Curl Up after uncurling
* Slaver (Blue)
	* New Move: Trip
		* Big Debuff
		* Applies No block to player
		* Can be used multiple times (but not two turns in a row)
* Slaver (Red)
	* Can use Entangle multiple times (but not two turns in a row)
	* Cannot use Entangle on the same turn Blue Slaver uses Trip (see above)
* Slime (Small Acid/Spike)
	* Starts with 1 Extra Slimy
	* Slime Coating:
		* At the end of its turn, adds X Slimed into your discard pile.
* Slime (Medium Acid/Spike)
	* Starts with 2 Extra Slimy

#### Elites
* Gremlin Nob
	* Summons 2 gremlin minions on turn 1
* Lagavulin
	* Starts with 3 Demon Form
	* Removes Demon Form when waking up
* Sentries
	* A 4th Sentry is added to the fight

#### Bosses
* The Guardian
	* Skips first Offensive-Defensive cycle, starting combat in Defensive Mode about to perform Twin Slam
* Hexaghost
	* Name changed to Pentaghost
	* Skips turn 1 Activate, starts combat using Divider
	* Divider deals H x 5 damage instead of H x 6
	* H is calculated as `Current HP / 10 + 1` instead of `Current HP / 12 + 1`
	* Skips the last Sear in its move rotation, cutting the move rotation before Inferno down to 5 turns instead of 6 turns
* Slime Boss
	* Splits at 75% HP instead of 50%

### City

#### Events
* The Colosseum
	* Second fight changed to double Gremlin Nob instead of Taskmaster + Gremlin Nob
	* The Gremlin Nobs in this fight will not summon gremlin minions
* Masked Bandits
	* Pointy
		* Starts with 2 Thorns
		* When Bear dies, gains 1 Enrage
	* Romeo
		* Mock changed to Debuff, applies 5 Weak to player
		* When Bear dies, gains 1 Enrage
	* Bear
		* Bear Hug also applies 2 Frail (before the negative Dexterity)
		* When Romeo dies, gains 2 Enrage and 2 Strength

#### Normal Enemies
* Byrds
	* No longer stunned when Flight is removed, instead goes straight into Headbutt
* Centurion and Mystic
	* Centurion can only take 10 damage per turn while Mystic is alive
* Chosen
	* Hex now shuffles a Dazed into the draw pile whenever you play ANY card
* Fungi Beast
	* Spore Cloud goes off automatically after 2-4 turns as well as on death
* Mugger
	* Skips Smoke Bomb prep turn
* Shelled Parasite
	* Plated Armor is replaced with Metallicize
* Snake Plant
	* New Move:
		* Attack + Debuff
		* 17 damage
		* Applies -3 Strength and 2 Shackled to player
* Snecko
	* Starts with 1 Snecko Bot
	* Snecko Bot:
		* Whenever you play a card, choose a random number from 0 to 3. If the number is equal to the cost of the card played, gains X Strength.
* Spheric Guardian
	* Starts with 3 Buffer

#### Elites
* Book of Stabbing
	* Name changed to Book of Punching
	* Painful Stabs replaced with Painful Punches
	* Painful Punches is identical to Painful Stabs but puts the Wounds on top of your draw pile instead of into your discard pile.
* Gremlin Leader
	* Maximum number of gremlin minions increased to 4
	* Spawns up to 3 gremlins at a time with Rally
* Taskmaster
	* Attack is changed to 7 x N, where N = turn / 2 rounded up

#### Bosses
* Bronze Automaton
	* Skips spawn turn, starts combat with Bronze Orbs already spawned
	* Bronze Orb
		* Stasis move now steals 2 cards
* The Champ
	* Starts with 3 Strength
	* Cannot use Defensive Stance move
	* Gloat and Anger moves give 1 additional Strength
* The Collector
	* Maximum number of Torch Heads increased to 3
	* Spawns up to 3 Torch Heads at a time

### Beyond

#### Events
* Mind Bloom
	* I am War option changed to fight 2 Act 1 Bosses in a row
* Mysterious Sphere
	* 50% of the time the fight will be Snecko and Mystics instead of Orb Walkers

#### Normal Enemies
* Darklings
	* A 4th Darkling is added to the fight
* Jaw Worm Horde
	* Starts with Life Link
* The Maw
	* Always uses Slam on turn 2
	* Starts with 2 Strength
* Orb Walker
	* Starts with 2 Artifact
	* Gives player upgraded Burns
* Shapes (Exploder)
	* Starts with -1 turn on Explode
* Shapes (Repulsor)
	* On death, puts 2 Dazed on top of the draw pile
* Shapes (Spiker
	* Gives ALL monsters 2 Thorns when buffing
* Spire Growth
	* Constrict move (Big Debuff) changed to also deal damage equal to Quick Tackle
* Transient
	* Starts with +1 Fading
	* Starts with 999 Regenerate
* Writhing Mass
	* Starts with 8 Regenerate

#### Elites
* Giant Head
	* Remove Slow
* Nemesis
	* On non-Intangible turns, has Semi-Intangible
	* Semi-Intangible: Reduces damage received by 5
* Reptomancer
	* Retomancer and Daggers apply 2 Venom when dealing unblocked damage
	* Venom:
		* Poison but blockable

#### Bosses
* Awakened One
    * Added a third phase:
        * 200 Max HP (220 on Asc 9+)
        * Gains 10 Ritual
* Donu and Deca
    * Now have a single, shared health pool
* Time Eater
    * Starts with Real Time
    * Real Time:
        * Every 30 seconds, gains 1 Strength
    * An accessibility option exists in the config menu to replace Real Time with Double Time
    * Double Time:
        * Whenever you play 6 cards, gains 1 Strength

### Ending

#### Elite
* Spire Shield and Spire Spear
	* Both start with Barricade

#### Boss
* Corrupt Heart
	* Debilitate move also applies -1 draw per turn and -1 energy per turn
