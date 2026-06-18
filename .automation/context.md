# TCIntegrations -- Project Context

## What This Is
Tinkers' Construct integrations and tweaks mod. Adds materials, modifiers, and tool/armor abilities sourced from other mods into Tinkers' Construct -- with a focus on meaningful, balanced integration rather than dumping every mod's material in as a TC variant.

Almost all integration content is datapack-driven and can be overridden. Some integrations
are hard-coded to match specific mod capabilities, by design.

Started with Botania mana-repair integration; expanded from there.

## Project Structure
Forge-only. No multi-loader structure -- Tinkers' Construct has been on 1.20.1 for an extended period, so NeoForge and newer MC versions are blocked by TC's update pace. Single `src/` at root.

## Branch Convention
| Branch | Modloader |
|--------|-----------|
| 1.18.2 | Forge     |
| 1.19.2 | Forge     |
| 1.20.1 | Forge     |

Maintained: 1.20.1. Older branches are legacy.

## Dependencies
- Mantle (required)
- Tinkers' Construct (required)
- WhiteNoise (runtimeOnly -- not jarJar/include)

### Optional Integration Targets
Wendall911 mods: ActuallyHarvest, BetterDays, ChargedCharms, CreeperFireworks, Homeostatic, MagicalPsiRevival, SimpleTextOverlay, TinkerSurvival, ReadyPlayerFun

Third-party: Botania, MythicBotany, Create, Ars Nouveau, Ars Elemental, Aquaculture 2, Alex's Mobs, Malum, The Undergarden, ProjectE, Beyond Earth / Ad Astra, Immersive Engineering, Mekanism, Ice and Fire: Dragons, Consecration, Deeper and Darker, Twilight Forest

## Distribution
Side: both (clientRequired = true, serverRequired = true) CurseForge + Modrinth

## Version Lock Note
This mod's update pace is gated by Tinkers' Construct. Until TC releases for a newer Minecraft version, TCIntegrations stays on 1.20.1 Forge. Do not plan NeoForge or 1.21+ work until TC's update situation changes.

## Release Process
Follow the standard wendall911 release process in `../docs/minecraft/MINECRAFT_DEVELOPMENT_NOTES.md`.
