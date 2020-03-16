package block.base.model;

import java.util.ArrayList;

public class BlockChain {
	
	public static ArrayList<Block> blockChain = new ArrayList<Block>();

	public static ArrayList<Block> getBlockChain() {
		return blockChain;
	}

	public static void setBlockChain(ArrayList<Block> blockChain) {
		BlockChain.blockChain = blockChain;
	}

}
