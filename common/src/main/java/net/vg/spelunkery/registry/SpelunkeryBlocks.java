package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.block.CrucibleBlock;
import net.vg.spelunkery.block.FoundryBlock;
import net.vg.spelunkery.block.InvarAnvilBlock;
import net.vg.spelunkery.block.RopeBlock;

import java.util.function.Supplier;

public final class SpelunkeryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> TIN_ORE = registerBlock(
            "tin_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.0F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> DEEPSLATE_TIN_ORE = registerBlock(
            "deepslate_tin_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(4.5F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
    );

    public static final RegistrySupplier<Block> RAW_TIN_BLOCK = registerBlock(
            "raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> TIN_BLOCK = registerBlock(
            "tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> NICKEL_ORE = registerBlock(
            "nickel_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 4), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4.0F, 4.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> DEEPSLATE_NICKEL_ORE = registerBlock(
            "deepslate_nickel_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 5), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(5.0F, 4.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
    );

    public static final RegistrySupplier<Block> RAW_NICKEL_BLOCK = registerBlock(
            "raw_nickel_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> NICKEL_BLOCK = registerBlock(
            "nickel_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> SILVER_ORE = registerBlock(
            "silver_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.5F, 3.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> DEEPSLATE_SILVER_ORE = registerBlock(
            "deepslate_silver_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(4.5F, 4.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
    );

    public static final RegistrySupplier<Block> RAW_SILVER_BLOCK = registerBlock(
            "raw_silver_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> SILVER_BLOCK = registerBlock(
            "silver_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> TOPAZ_BLOCK = registerBlock(
            "topaz_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(1.5F, 1.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 5))
    );

    public static final RegistrySupplier<Block> RUBY_BLOCK = registerBlock(
            "ruby_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.7F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 5))
    );

    public static final RegistrySupplier<Block> SAPPHIRE_BLOCK = registerBlock(
            "sapphire_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(1.7F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 5))
    );

    public static final RegistrySupplier<Block> GEM_POCKET = registerBlock(
            "gem_pocket",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(2.4F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST))
    );

    public static final RegistrySupplier<Block> MARBLE = registerBlock(
            "marble",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.TUFF))
    );

    public static final RegistrySupplier<Block> MARBLE_STAIRS = registerBlock(
            "marble_stairs",
            () -> new StairBlock(MARBLE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(MARBLE.get()))
    );

    public static final RegistrySupplier<Block> MARBLE_SLAB = registerBlock(
            "marble_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE.get()))
    );

    public static final RegistrySupplier<Block> MARBLE_WALL = registerBlock(
            "marble_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> POLISHED_MARBLE = registerBlock(
            "polished_marble",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MARBLE.get()).sound(SoundType.POLISHED_DEEPSLATE))
    );

    public static final RegistrySupplier<Block> POLISHED_MARBLE_STAIRS = registerBlock(
            "polished_marble_stairs",
            () -> new StairBlock(POLISHED_MARBLE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED_MARBLE.get()))
    );

    public static final RegistrySupplier<Block> POLISHED_MARBLE_SLAB = registerBlock(
            "polished_marble_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_MARBLE.get()))
    );

    public static final RegistrySupplier<Block> POLISHED_MARBLE_WALL = registerBlock(
            "polished_marble_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_MARBLE.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> MARBLE_BRICKS = registerBlock(
            "marble_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MARBLE.get()).sound(SoundType.DEEPSLATE_BRICKS))
    );

    public static final RegistrySupplier<Block> MARBLE_BRICK_STAIRS = registerBlock(
            "marble_brick_stairs",
            () -> new StairBlock(MARBLE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(MARBLE_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> MARBLE_BRICK_SLAB = registerBlock(
            "marble_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> MARBLE_BRICK_WALL = registerBlock(
            "marble_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE_BRICKS.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> MARBLE_TILES = registerBlock(
            "marble_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MARBLE.get()).sound(SoundType.DEEPSLATE_TILES))
    );

    public static final RegistrySupplier<Block> MARBLE_TILE_STAIRS = registerBlock(
            "marble_tile_stairs",
            () -> new StairBlock(MARBLE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(MARBLE_TILES.get()))
    );

    public static final RegistrySupplier<Block> MARBLE_TILE_SLAB = registerBlock(
            "marble_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE_TILES.get()))
    );

    public static final RegistrySupplier<Block> MARBLE_TILE_WALL = registerBlock(
            "marble_tile_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MARBLE_TILES.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> PRISMATIC_STONE = registerBlock(
            "prismatic_stone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(1.8F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 4))
    );

    public static final RegistrySupplier<Block> PRISMATIC_STONE_STAIRS = registerBlock(
            "prismatic_stone_stairs",
            () -> new StairBlock(PRISMATIC_STONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(PRISMATIC_STONE.get()))
    );

    public static final RegistrySupplier<Block> PRISMATIC_STONE_SLAB = registerBlock(
            "prismatic_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_STONE.get()))
    );

    public static final RegistrySupplier<Block> PRISMATIC_STONE_WALL = registerBlock(
            "prismatic_stone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_STONE.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> POLISHED_PRISMATIC_STONE = registerBlock(
            "polished_prismatic_stone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_STONE.get()).sound(SoundType.AMETHYST_CLUSTER))
    );

    public static final RegistrySupplier<Block> POLISHED_PRISMATIC_STONE_STAIRS = registerBlock(
            "polished_prismatic_stone_stairs",
            () -> new StairBlock(POLISHED_PRISMATIC_STONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED_PRISMATIC_STONE.get()))
    );

    public static final RegistrySupplier<Block> POLISHED_PRISMATIC_STONE_SLAB = registerBlock(
            "polished_prismatic_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_PRISMATIC_STONE.get()))
    );

    public static final RegistrySupplier<Block> POLISHED_PRISMATIC_STONE_WALL = registerBlock(
            "polished_prismatic_stone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_PRISMATIC_STONE.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> PRISMATIC_BRICKS = registerBlock(
            "prismatic_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_STONE.get()).sound(SoundType.AMETHYST))
    );

    public static final RegistrySupplier<Block> PRISMATIC_BRICK_STAIRS = registerBlock(
            "prismatic_brick_stairs",
            () -> new StairBlock(PRISMATIC_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(PRISMATIC_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> PRISMATIC_BRICK_SLAB = registerBlock(
            "prismatic_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> PRISMATIC_BRICK_WALL = registerBlock(
            "prismatic_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_BRICKS.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> PRISMATIC_TILES = registerBlock(
            "prismatic_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_STONE.get()).sound(SoundType.AMETHYST))
    );

    public static final RegistrySupplier<Block> PRISMATIC_TILE_STAIRS = registerBlock(
            "prismatic_tile_stairs",
            () -> new StairBlock(PRISMATIC_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(PRISMATIC_TILES.get()))
    );

    public static final RegistrySupplier<Block> PRISMATIC_TILE_SLAB = registerBlock(
            "prismatic_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_TILES.get()))
    );

    public static final RegistrySupplier<Block> PRISMATIC_TILE_WALL = registerBlock(
            "prismatic_tile_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PRISMATIC_TILES.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> BLUE_CRYSTAL = registerBlock(
            "blue_crystal",
            () -> new PointedDripstoneBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(1.5F)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .randomTicks()
                    .noOcclusion()
                    .lightLevel(state -> 4))
    );

    public static final RegistrySupplier<Block> GREEN_CRYSTAL = registerBlock(
            "green_crystal",
            () -> new PointedDripstoneBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(1.5F)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .randomTicks()
                    .noOcclusion()
                    .lightLevel(state -> 4))
    );

    public static final RegistrySupplier<Block> RED_CRYSTAL = registerBlock(
            "red_crystal",
            () -> new PointedDripstoneBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.5F)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .randomTicks()
                    .noOcclusion()
                    .lightLevel(state -> 4))
    );

    public static final RegistrySupplier<Block> YELLOW_CRYSTAL = registerBlock(
            "yellow_crystal",
            () -> new PointedDripstoneBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(1.5F)
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .randomTicks()
                    .noOcclusion()
                    .lightLevel(state -> 4))
    );

    public static final RegistrySupplier<Block> CINDER_ROCK = registerBlock(
            "cinder_rock",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.7F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.TUFF))
    );

    public static final RegistrySupplier<Block> CINDER_ROCK_STAIRS = registerBlock(
            "cinder_rock_stairs",
            () -> new StairBlock(CINDER_ROCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CINDER_ROCK.get()))
    );

    public static final RegistrySupplier<Block> CINDER_ROCK_SLAB = registerBlock(
            "cinder_rock_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CINDER_ROCK.get()))
    );

    public static final RegistrySupplier<Block> CINDER_ROCK_WALL = registerBlock(
            "cinder_rock_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CINDER_ROCK.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> POLISHED_CINDER = registerBlock(
            "polished_cinder",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CINDER_ROCK.get()).sound(SoundType.POLISHED_DEEPSLATE))
    );

    public static final RegistrySupplier<Block> POLISHED_CINDER_STAIRS = registerBlock(
            "polished_cinder_stairs",
            () -> new StairBlock(POLISHED_CINDER.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED_CINDER.get()))
    );

    public static final RegistrySupplier<Block> POLISHED_CINDER_SLAB = registerBlock(
            "polished_cinder_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_CINDER.get()))
    );

    public static final RegistrySupplier<Block> POLISHED_CINDER_WALL = registerBlock(
            "polished_cinder_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_CINDER.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> CINDER_BRICKS = registerBlock(
            "cinder_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CINDER_ROCK.get()).sound(SoundType.DEEPSLATE_BRICKS))
    );

    public static final RegistrySupplier<Block> CINDER_BRICK_STAIRS = registerBlock(
            "cinder_brick_stairs",
            () -> new StairBlock(CINDER_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CINDER_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> CINDER_BRICK_SLAB = registerBlock(
            "cinder_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CINDER_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> CINDER_BRICK_WALL = registerBlock(
            "cinder_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CINDER_BRICKS.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> CINDER_TILES = registerBlock(
            "cinder_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CINDER_ROCK.get()).sound(SoundType.DEEPSLATE_TILES))
    );

    public static final RegistrySupplier<Block> CINDER_TILE_STAIRS = registerBlock(
            "cinder_tile_stairs",
            () -> new StairBlock(CINDER_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CINDER_TILES.get()))
    );

    public static final RegistrySupplier<Block> CINDER_TILE_SLAB = registerBlock(
            "cinder_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CINDER_TILES.get()))
    );

    public static final RegistrySupplier<Block> CINDER_TILE_WALL = registerBlock(
            "cinder_tile_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CINDER_TILES.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> ASHEN_DIRT = registerBlock(
            "ashen_dirt",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(0.6F)
                    .sound(SoundType.ROOTED_DIRT))
    );

    public static final RegistrySupplier<Block> SCORCHED_DRIPSTONE = registerBlock(
            "scorched_dripstone",
            () -> new PointedDripstoneBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.5F)
                    .sound(SoundType.POINTED_DRIPSTONE)
                    .randomTicks()
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> CHARRED_BONES = registerBlock(
            "charred_bones",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(2.0F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BONE_BLOCK))
    );

    public static final RegistrySupplier<Block> MYCELIUM_MUD = registerBlock(
            "mycelium_mud",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(0.6F)
                    .sound(SoundType.MUD))
    );

    public static final RegistrySupplier<Block> MYCELIUM_MUD_BRICKS = registerBlock(
            "mycelium_mud_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MYCELIUM_MUD.get()).requiresCorrectToolForDrops().sound(SoundType.MUD_BRICKS))
    );

    public static final RegistrySupplier<Block> MYCELIUM_MUD_BRICK_STAIRS = registerBlock(
            "mycelium_mud_brick_stairs",
            () -> new StairBlock(MYCELIUM_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(MYCELIUM_MUD_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> MYCELIUM_MUD_BRICK_SLAB = registerBlock(
            "mycelium_mud_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MYCELIUM_MUD_BRICKS.get()))
    );

    public static final RegistrySupplier<Block> MYCELIUM_MUD_BRICK_WALL = registerBlock(
            "mycelium_mud_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MYCELIUM_MUD_BRICKS.get()).forceSolidOn())
    );

    public static final RegistrySupplier<Block> FUNGAL_MAT = registerBlock(
            "fungal_mat",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(0.2F)
                    .sound(SoundType.MOSS))
    );

    public static final RegistrySupplier<Block> BIOLUMINESCENT_MOSS = registerBlock(
            "bioluminescent_moss",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_CYAN)
                    .strength(0.2F)
                    .sound(SoundType.MOSS)
                    .lightLevel(state -> 8))
    );

    public static final RegistrySupplier<Block> GLOWCAP = registerBlock(
            "glowcap",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_CYAN)
                    .noCollission()
                    .instabreak()
                    .noOcclusion()
                    .sound(SoundType.FUNGUS)
                    .lightLevel(state -> 10))
    );

    public static final RegistrySupplier<Block> BRONZE_BLOCK = registerBlock(
            "bronze_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> BRONZE_TILES = registerBlock(
            "bronze_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> BRONZE_GRATE = registerBlock(
            "bronze_grate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> BRONZE_BARS = registerBlock(
            "bronze_bars",
            () -> new IronBarsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.CHAIN)
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> BRONZE_CHAIN = registerBlock(
            "bronze_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.CHAIN)
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> BRONZE_LANTERN = registerBlock(
            "bronze_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.LANTERN)
                    .noOcclusion()
                    .lightLevel(state -> 15))
    );

    public static final RegistrySupplier<Block> INVAR_BLOCK = registerBlock(
            "invar_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.5F, 6.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> INVAR_TILES = registerBlock(
            "invar_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.5F, 6.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> DAMAGED_INVAR_ANVIL = registerBlock(
            "damaged_invar_anvil",
            () -> new InvarAnvilBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(6.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL), null)
    );

    public static final RegistrySupplier<Block> CHIPPED_INVAR_ANVIL = registerBlock(
            "chipped_invar_anvil",
            () -> new InvarAnvilBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(6.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL), DAMAGED_INVAR_ANVIL::get)
    );

    public static final RegistrySupplier<Block> INVAR_ANVIL = registerBlock(
            "invar_anvil",
            () -> new InvarAnvilBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(6.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL), CHIPPED_INVAR_ANVIL::get)
    );

    public static final RegistrySupplier<Block> ROSE_GOLD_BLOCK = registerBlock(
            "rose_gold_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> ROSE_GOLD_TILES = registerBlock(
            "rose_gold_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> ELECTRUM_BLOCK = registerBlock(
            "electrum_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> ELECTRUM_TILES = registerBlock(
            "electrum_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> ROPE = registerBlockWithoutItem(
            "rope",
            () -> new RopeBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOL)
                    .strength(0.3F)
                    .sound(SoundType.CHAIN)
                    .noCollission()
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> CRUCIBLE = registerBlock(
            "crucible",
            () -> new CrucibleBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> FOUNDRY = registerBlock(
            "foundry",
            () -> new FoundryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(4.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    private static boolean initialized;

    private SpelunkeryBlocks() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        BLOCKS.register();
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> supplier) {
        RegistrySupplier<T> block = BLOCKS.register(name, supplier);
        SpelunkeryItems.registerBlockItem(name, block::get);
        return block;
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockWithoutItem(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }
}
