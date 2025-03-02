## Tinkers' Integrations and Tweaks [![Project](http://cf.way2muchnoise.eu/full_602680_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/tcintegrations) [![Project](https://modrinth.roughness.technology/full_tcintegrations_downloads.svg)](https://modrinth.com/mod/tcintegrations)
[![](https://modrinth.roughness.technology/versions/tcintegrations.svg)](https://modrinth.com/mod/tcintegrations/versions)
[![](https://img.shields.io/badge/Forge-39.0+-green.svg?longCache=true&style=flat)](https://legacy.curseforge.com/minecraft/mc-mods/tcintegrations/files)
![MIT](https://img.shields.io/badge/license-MIT-blue.svg?longCache=true&style=flat)

A Minecraft 1.18+ mod designed to provide modpack integrations with other mods for
[Tinkers' Construct](https://github.com/SlimeKnights/TinkersConstruct), as well as some other tweaks.

The main goal here is to not just add a bunch of random new materials from mods and call it "integration". The goal is to add materials where it makes sense, and capture the capabilities of other mods tools and armors in Tinkers' Construct. This started with Botania and wanting to have the ability to use mana generating inventory items to automatically repair tools. This entire concept has worked out well, and the idea moving forward is balanced integration.

Almost everything in the mod is datapack driven and can be overridden via datapack. Some of the mod integration abilities are hard coded to match the mod capability for the integration, which is intentional.

**Will take requests for other tweaks and integrations!**

## Mod Integrations
### 🔵 Botania
 * Adds Manasteel to smeltery
 * Manasteel parts add "Mana" modifier
   * "**Mana**" modifer allows tools to repair with any mana generating inventory item (60 mana cost)
 * Adds Livingwood as tool part material
   * "**Livingwood**" modifier reduces material repair costs by 75%
 * Adds Livingstone as tool part material
   * Better stats than rock
 * Adds "**Terra**" upgrade modifier for primary weapons
   * Repairs weapon if any mana generating inventory item is present (100 mana cost)
   * When swung and the attack recharge meter is full, it will use 100 Mana and create a damaging Mana burst that deals 7 points (3.5 hearts) of damage to the hit mob
 * Adds "**Elemental**" upgrade modifier for primary weapons
   * Repairs weapon if any mana generating inventory item is present (70 mana cost)
   * 5% chance to spawn a Pixie on hit
   * Beheading chance if a Zombie, Skeleton, Wither Skeleton, Creeper, the Guardian of Gaia, should be implemented with similar chance to the Elementium Axe
 * Adds "**Terrestrial**" upgrade modifier for armor as a complete "set" bonus
   * Passive generation of 1 Mana every 10 ticks on Mana Tablets
   * 20% decrease of Mana cost on all Mana-using TCon items (Can't translate to Botania items)
   * Passive regeneration of 2 health every 4 seconds if their hunger bar isn't full
 * Adds "**Great Fairy**" upgrade modifier for armor as a complete "set" bonus
   * 10% decrease of the Mana cost on all Mana-using TCon items (Can't translate to Botania items)
   * If the player is attacked while wearing this armor piece, there's an % chance (depends on armor piece, same as Botania) to spawn a Pixie after being attacked.
   * Pixies spawned by the wearer will inflict a random potion effect for 2 seconds: either Blindness, Wither, Slowness or Weakness.
### 🟠 MythicBotany
 * Adds "**Alf**" upgrade modifier for primary weapons
   * Requires "**Terra**" upgrade modifier to upgrade
   * Adds additional repair if any mana generating inventory item is present (100 mana cost)
   * Acts as an item magnet when using interact (normal interact when holding shift)
 * Adds "**Alfheim**" upgrade modifier for armor
   * Requires "**Terrestrial**" modifier to upgrade
   * Set bonus increases armor repair rate
   * Helmet increases reach distance
   * Chestplate adds knockback resistance
   * Leggings increase run/swim speed
   * Boots increase jump height when sprinting
### 🟡 Create
 * Adds tool material for Brass
   * Material has similar stats to Bronze
   * Adds "**Moderate**" modifier that makes the tool mine faster in moderate temperatures
 * Adds "**Engineer's Goggles**" ability
   * Can upgrade helmet with Engineer's Goggles, adding same functionality
 * Adds "**Mechanical Arm**" ability 
   * Adds ability to attack with any melee weapon in the offhand
### 🟢 Aquaculture 2
 * Adds Neptunium to smeltery
 * Neptunium parts add "**Water Powered**" modifier
   * Tools resist damage while in water
 * Adds "**Poseidon**" modifier for armor
   * Helmet - Improves underwater vision
   * Chestplate - Underwater breathing
   * Leggings - Weightless underwater
   * Boots - Increases swim speed
 * Adds "**Siren**" modifier for tools
   * Melee Weapons - Increased damage against enemies underwater
   * Harvest tools - Removes underwater mining speed penalty
   * Hoe tools - Tilled farmland will stay moist
### 🟣 Ars Nouveau
 * Adds Source Gem to smeltery
 * Adds new "**Novice**", "**Mage**" or "**Archmage**" modifier to armor
   * Uses mana to repair item
   * Provides Mana Boost enchantment based on level
   * Provides Mana Regen enchantment based on level
 * Adds "**Enchanter's Shield**" modifier for Chestplates
   * Upon blocking damage, the user will gain a short duration of Mana Regeneration and Spell Damage.
   * Uses mana to repair item
## Ars Elemental
 * Adds **Aethermancer's Set**, **Aquamancer's Set**, **Geomancer's Set** and **Pyromancer's Set** modifiers to armor
   * Uses mana to repair item
   * Provides one Mana Boost enchantment level that can be combined with Ars Nouveau Modifier Levels for a Max of 4
   * Provides one Mana Regen enchantment level that can be combined with Ars Nouveau Modifier Levels for a Max of 4
   * Provides damage protection and mana regeneration depending on the specific set bonus when an entire set is equipped
### ⚪ Alex's Mobs
 * "**Roadrunner Boots**" upgrade modifier - Boots
   * Adds a speed boost per level on sand
 * "**Frontier Cap**" upgrade modifier - Helmet
   * Adds sneak speed bonus per level while sneaking
 * "**Spiked Turtle Shell**" ability modifier - Helmet
   * Water breathing
 * "**Bison Fur**" ability modifier - Boots
   * Insulated from Snow
 * "**Shield of the Deep**" upgrade modifier- Chestplate
   * Gives Exsanguination effect to nearby entities when attacked
   * Increases underwater air supply when attacked
 * "**Mosquito**" upgrade modifier - Armor
   * Nearby players hear persistent mosquito sound
   * Nearby players get minor visual effect, no damage
 * "**Crocodile**" upgrade modifier - Chestplate
   * Swim speed bonus
   * Armor bonus
### ⚫ Malum
 * Adds Soul Stained Steel to smeltery
 * Soul Stained Steel parts add "**Soul Stained**" modifier
   * Adds Magic Resistance and Soul Ward Capacity to Armor items with modifier
   * Adds Magic damage to weapons and tools
 * Adds "Soul Stained" defense modifier for Armor
### 🔴 The Undergarden
 * Adds Cloggrum to smeltery
 * Adds Frosteel to smeltery
 * Adds Forgotten to smeltery
 * Adds "**Masticate**" modifier for Armor
  * Adds a scaled thorns damage effect
 * Adds "**Utherium**" modifier for melee weapons
   * Increases damage to Rotspawn by 1.5x
 * Adds "**Froststeel**" modifier for tools
   * Adds slowing effect to hit entities
   * Increased amplifier for weapons
 * Adds "**Forgotten**" modifier for tools
   * 1.5x speed increase for harvest tools while in the Undergarden
   * 1.5x attack damage against mobs while in the Undergarden
### ProjectE
 * Adds EMC for Cobalt
### Beyond Earth / Ad Astra
 * Improved Tinkers' Construct support
   * Add ore smelting for Desh, Calorite and Ostrum
 * Adds "**Oxegenated**" material property to Desh, Calorite and Ostrum
   * When harvesting, creates an oxygen bubble around player
 * Adds "**Cheesy**" modifier
   * Can eat tool to restore some hunger
### Immersive Engineering
 * Adds an ability modifier "**MultiVision**"
   * Allows multimeter visual overlay to always be enabled
### Mekanism
 * Tweak to Osmium material to increase stats slightly to align with Mekanism Tools
 * Adds "**Kinetic**" trait to Osmium material
   * When using the tool, a single energy using item gains small recharge amount
 * Adds "**Glow Up**" modifier
   * Adds glowing outline to hit entities, 30s per level
### Ice and Fire: Dragons
 * Adds "**Flamed**" upgrade modifier - Weapons
   * Knocks back target
   * Sets target on fire for 5 seconds
   * Bonus damage against Ice Dragons
 * Adds "**Iced**" upgrade modifier - Weapons
   * Knocks back target
   * Adds slowness to target for 5 seconds
   * Bonus damage against Fire Dragons
 * Adds "**Zapped**" upgrade modifier - Weapons
   * Knocks back target
   * Lighting strikes hit target
   * Bonus damage against Ice and Fire Dragons
 * Adds "**Phantasmal**" upgrade modifier - Swords
   * Launches Ghost Sword when swinging sword in air
### Consecration
 * Enables support for Holy Damage that was disabled due to the glacial pace that Tinkers' Construct updates.
### Deeper and Darker
 * Adds "**Skulking** modifier - Armor
   * Removes blindness and darkness effects.
### Twightlight Forest
 * Adds **Precipitate** ability modifier - Weapons / Harvest Tools
   * Mining speed increases as heath decreases.
   * Attack Speed increases as health decreases.
   * Arrow velocity increases as health decreases.
 * Adds **Twilit** upgrade modifier
   * 200% mining speed when using tool in the Twilight Forest
   * 10% arrow velocity increase when in the Twilight Forest
   * 150% attack damage when NOT in the Twilight Forest
 * Adds alternative recipe for the **Fiery** modifier
   * Increases max level to 7 from 5
 * Adds alternative recipe for the **Freezing** modifier
   * Increases max level from 3 to 5
 * Adds alternative recipe for the **Autosmelt** modifier
### Smeltery Additions
 * Bronze
   * Adds back bronze block/ingot/nuggets
   * Adds alternative recipe for bronze 3 copper ingots + 1 obsidion = 4 bronze ingots
     * Recipe only exists if modpack does not have Tin
 * Manasteel (see Botania)
 * Neptunium (see Aquaculture 2)
 * Source Gem (see Ars Nouveau)
 * Soul Stained Steel (see Malum)
 * Cloggrum, Froststeel, Forgotten (See The Undergarden)
 * Desh, Calorite and Ostrum (See Beyond Earth)

## Links of Interest

+ [Tinkers' Integrations and Tweaks Curseforge Page](https://www.curseforge.com/minecraft/mc-mods/tcintegrations)
+ [Tinkers' Integrations and Tweaks Modrinth Page](https://modrinth.com/mod/tcintegrations)
