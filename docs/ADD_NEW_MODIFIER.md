# Add New Modifier

Instructions for adding a new modifier.

1. Create new modifier under items/modifiers/{armor|tool|traits}
1. Register modifier in items/TCIntegrationsModifiers.java
1. Add conditional recipe (mod needs to be present) for modifier in data/tcon/ModifierRecipeProvider.java
1. Add translation strings for modifier in src/main/resources/assets/tcintegrations/lang/en_us.json
    1. modifier.tcintegrations.{modifier_name}
    1. modifier.tcintegrations.{modifier_name}.flavor
    1. modifier.tcintegrations.{modifier_name}.description
    1. Add translation strings to other language translation files
1. Add modifier color to src/main/resources/assets/tcintegrations/mantle/colors.json
