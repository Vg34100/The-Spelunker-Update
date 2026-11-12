package net.vg.spelunkery.validation;

import java.util.List;
import java.util.Collection;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.common.util.FakePlayer;
import net.vg.spelunkery.block.CrucibleBlock;
import net.vg.spelunkery.block.entity.FoundryBlockEntity;
import net.vg.spelunkery.item.*;
import net.vg.spelunkery.menu.*;
import net.vg.spelunkery.registry.*;

/** Focused integration fixtures cover the distinct 1.20.1 data and world boundaries. */
@Mod("spelunkery_port_tests")
@GameTestHolder("spelunkery_port_tests")
public final class ForgePortTests {
    @GameTestGenerator
    public static Collection<TestFunction> tests() {
        String selected = System.getProperty("spelunkery.port.cases", "");
        return List.of(test("machine_and_menu", ForgePortTests::machine),
                test("equipment_and_projectile", ForgePortTests::equipment),
                test("ore_and_cave_features", ForgePortTests::worldgen), test("loot_semantics", ForgePortTests::loot)).stream()
                .filter(test -> selected.isEmpty() || java.util.Arrays.stream(selected.split(",")).anyMatch(name -> test.getTestName().endsWith(":" + name))).toList();
    }
    private static TestFunction test(String name, java.util.function.Consumer<GameTestHelper> body) {
        return new TestFunction("spelunkery_port_tests", "spelunkery_port_tests:" + name,
                "spelunkery_port_tests:empty", 400, 0, true, body);
    }
    private static FakePlayer player(GameTestHelper h) {
        return new FakePlayer(h.getLevel(), new GameProfile(UUID.randomUUID(), "SpelunkTest"));
    }
    private static void machine(GameTestHelper h) {
        BlockPos pos = h.absolutePos(new BlockPos(2, 3, 2));
        var level = h.getLevel();
        level.setBlockAndUpdate(pos.below(), SpelunkeryBlocks.CRUCIBLE.get().defaultBlockState().setValue(CrucibleBlock.LEVEL, 3));
        level.setBlockAndUpdate(pos, SpelunkeryBlocks.FOUNDRY.get().defaultBlockState());
        FoundryBlockEntity foundry = (FoundryBlockEntity) level.getBlockEntity(pos);
        foundry.setItem(0, new ItemStack(Items.COPPER_INGOT, 3));
        foundry.setItem(1, new ItemStack(SpelunkeryItems.TIN_INGOT.get()));
        for (int tick = 0; tick < 100; tick++) FoundryBlockEntity.serverTick(level, pos, level.getBlockState(pos), foundry);
        CompoundTag saved = foundry.saveWithoutMetadata();
        FoundryBlockEntity restored = new FoundryBlockEntity(pos, level.getBlockState(pos));
        restored.load(saved); restored.setLevel(level);
        h.assertTrue(restored.getData().get(0) == 100 && restored.getItem(0).getCount() == 3, "Foundry save lost progress or inventory");
        for (int tick = 0; tick < 100; tick++) FoundryBlockEntity.serverTick(level, pos, level.getBlockState(pos), restored);
        h.assertTrue(restored.getItem(3).is(SpelunkeryItems.BRONZE_INGOT.get()) && restored.getItem(3).getCount() == 4, "Bronze recipe failed");
        h.assertTrue(level.getBlockState(pos.below()).getValue(CrucibleBlock.LEVEL) == 2, "Incorrect lava cost");
        FakePlayer player = player(h);
        h.assertTrue(restored.createMenu(1, player.getInventory(), player) instanceof FoundryMenu, "Foundry menu failed");
        var anvil = new InvarAnvilMenu(2, player.getInventory(), ContainerLevelAccess.create(level, pos));
        var access = (net.vg.spelunkery.mixin.AnvilMenuAccessor) anvil;
        h.assertTrue(access.spelunkery$cost() != null && access.spelunkery$repairItemCountCost() == 0, "Anvil accessors failed");
        h.succeed();
    }
    private static void equipment(GameTestHelper h) {
        FakePlayer player = player(h);
        for (MinerHelmetGem gem : MinerHelmetGem.values()) {
            ItemStack helmet = MinerHelmetHelper.socket(new ItemStack(SpelunkeryItems.MINERS_HELMET.get()), gem);
            helmet = ItemStack.of(helmet.save(new CompoundTag()));
            h.assertTrue(MinerHelmetHelper.getGem(helmet) == gem, "Socket did not persist: " + gem);
            player.setItemSlot(EquipmentSlot.HEAD, helmet);
            MinerHelmetHelper.updateEquippedHelmet(player);
        }
        ItemStack armor = new ItemStack(Items.IRON_CHESTPLATE);
        armor.setDamageValue(100);
        ItemStack nickel = ItemStack.of(ArmorUpgradeHelper.applyUpgrade(armor, ArmorUpgrade.NICKEL).save(new CompoundTag()));
        h.assertTrue(nickel.getMaxDamage() == (int) Math.ceil(armor.getMaxDamage() * 1.2), "Nickel durability failed");
        h.assertTrue(ArmorUpgradeHelper.getUpgrade(nickel) == ArmorUpgrade.NICKEL, "Upgrade did not persist");
        ItemStack gold = ArmorUpgradeHelper.applyUpgrade(armor, ArmorUpgrade.ROSE_GOLD);
        h.assertTrue(gold.hasFoil(), "Rose gold glint failed");
        player.setItemSlot(EquipmentSlot.CHEST, ArmorUpgradeHelper.applyUpgrade(armor, ArmorUpgrade.SILVER));
        h.assertTrue(!player.addEffect(new MobEffectInstance(MobEffects.WITHER, 100)), "Silver wither immunity failed");
        ItemStack brew = new ItemStack(SpelunkeryItems.SPELUNKERS_BREW.get());
        brew.finishUsingItem(h.getLevel(), player);
        h.assertTrue(player.hasEffect(SpelunkeryEffects.SPELUNKING.get()), "Tonic effect failed");
        var arrow = ((SilverArrowItem) SpelunkeryItems.SILVER_ARROW.get()).createArrow(h.getLevel(), new ItemStack(SpelunkeryItems.SILVER_ARROW.get()), player);
        h.assertTrue(arrow instanceof net.vg.spelunkery.common.entity.projectile.SilverArrow && h.getLevel().addFreshEntity(arrow), "Silver arrow creation failed");
        arrow.discard(); h.succeed();
    }
    private static void loot(GameTestHelper h) {
        var level = h.getLevel();
        var pos = h.absolutePos(new BlockPos(1, 2, 1));
        ItemStack plain = new ItemStack(Items.DIAMOND_PICKAXE);
        ItemStack silk = plain.copy(); silk.enchant(net.minecraft.world.item.enchantment.Enchantments.SILK_TOUCH, 1);
        var state = SpelunkeryBlocks.TIN_ORE.get().defaultBlockState();
        var raw = net.minecraft.world.level.block.Block.getDrops(state, level, pos, null, player(h), plain);
        var ore = net.minecraft.world.level.block.Block.getDrops(state, level, pos, null, player(h), silk);
        h.assertTrue(raw.size() == 1 && raw.get(0).is(SpelunkeryItems.RAW_TIN.get()), "Normal ore loot lost its condition");
        h.assertTrue(ore.size() == 1 && ore.get(0).is(SpelunkeryBlocks.TIN_ORE.get().asItem()), "Silk Touch loot failed");
        var slab = SpelunkeryBlocks.MARBLE_SLAB.get().defaultBlockState().setValue(net.minecraft.world.level.block.SlabBlock.TYPE, net.minecraft.world.level.block.state.properties.SlabType.DOUBLE);
        var drops = net.minecraft.world.level.block.Block.getDrops(slab, level, pos, null, player(h), plain);
        h.assertTrue(drops.size() == 1 && drops.get(0).getCount() == 2, "Double slab count failed");
        h.succeed();
    }
    private static void worldgen(GameTestHelper h) {
        var level = h.getLevel();
        BlockPos sample = h.absolutePos(new BlockPos(2, 3, 2));
        BlockPos origin = new BlockPos((sample.getX() & ~15) + 8, sample.getY(), (sample.getZ() & ~15) + 8);
        for (int x = -8; x <= 8; x++) for (int y = -3; y <= 3; y++) for (int z = -8; z <= 8; z++) {
            level.setBlock(origin.offset(x, y, z), Blocks.STONE.defaultBlockState(), 2);
        }
        var registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        var ore = registry.get(new ResourceLocation("spelunkery", "tin_ore"));
        h.assertTrue(ore != null && ore.place(level, level.getChunkSource().getGenerator(), level.random, origin), "Ore placement failed");
        for (int x = -8; x <= 8; x++) for (int z = -8; z <= 8; z++) {
            for (int y = 0; y < 12; y++) level.setBlock(origin.offset(x, y, z), Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(origin.offset(x, -1, z), Blocks.MYCELIUM.defaultBlockState(), 2);
        }
        var mushrooms = registry.get(new ResourceLocation("spelunkery", "fungal_giant_brown_mushroom"));
        h.assertTrue(mushrooms != null && mushrooms.feature() instanceof net.vg.spelunkery.worldgen.GiantMushroomFeature giant && giant.growFromOrigin(level, origin, level.random), "Custom cave mushroom placement failed");
        for (String biome : List.of("crystal_caverns", "marble_caves", "magma_vaults", "fungal_grottos")) {
            h.assertTrue(level.registryAccess().registryOrThrow(Registries.BIOME).containsKey(new ResourceLocation("spelunkery", biome)), "Missing cave biome: " + biome);
        }
        h.succeed();
    }
}
