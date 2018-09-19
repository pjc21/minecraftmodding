package pjc21.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import pjc21.mod.Main;
import pjc21.mod.objects.blocks.BlockBase;
import pjc21.mod.objects.blocks.BlockCopperChest;
import pjc21.mod.objects.blocks.BlockDirts;
import pjc21.mod.objects.blocks.BlockLeaf;
import pjc21.mod.objects.blocks.BlockLogs;
import pjc21.mod.objects.blocks.BlockOres;
import pjc21.mod.objects.blocks.BlockPlank;
import pjc21.mod.objects.blocks.BlockRicePlant;
import pjc21.mod.objects.blocks.BlockSantaHat;
import pjc21.mod.objects.blocks.BlockSaplings;
import pjc21.mod.objects.blocks.BlockTeleporter;
import pjc21.mod.objects.blocks.machines.generator.BlockGlowstoneGenerator;
import pjc21.mod.objects.blocks.machines.miner.BlockMiner;
import pjc21.mod.objects.blocks.machines.sinterer.BlockSinteringFurnace;

public class BlockInit 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//Blocks
	public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.IRON, Main.PAULSTAB);
	public static final Block BLOCK_ALUMINIUM = new BlockBase("block_aluminium", Material.IRON, Main.PAULSTAB);
	
	public static final Block PLANKS = new BlockPlank("planks");
	public static final Block LOGS = new BlockLogs("log");
	public static final Block LEAVES = new BlockLeaf("leaves");
	public static final Block SAPLINGS = new BlockSaplings("sapling");
	
	public static final Block BLOCK_DIRT = new BlockDirts("dirts");
	
	public static final Block SANTA_HAT = new BlockSantaHat("santa_hat");
	
	public static final Block SINTERING_FURNACE = new BlockSinteringFurnace("sintering_furnace");
	public static final Block MINER = new BlockMiner("miner", Material.ROCK);
	public static final Block GLOWSTONE_GENERATOR = new BlockGlowstoneGenerator("glowstone_generator");
	
	public static final Block COPPER_CHEST = new BlockCopperChest("copper_chest");
	
	//Plants
	public static final Block RICE_PLANT = new BlockRicePlant("rice_plant");
	
	//Ores
	public static final Block ORE_END = new BlockOres("ore_end", "end");
	public static final Block ORE_OVERWORLD = new BlockOres("ore_overworld", "overworld");
	public static final Block ORE_NETHER = new BlockOres("ore_nether", "nether");
	
	//Teleport Block
	public static final Block TELEPORT_BLOCK = new BlockTeleporter("teleport_block");

}
