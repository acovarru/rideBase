package block.base.util;

import block.base.model.Block;
import block.base.model.BlockChain;

public class BlockUtil {
	
	public static int difficulty = 5;
	
	public static void addBlock(Block block) {
		block.mineBlock(difficulty);
		BlockChain.getBlockChain().add(block);
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < BlockChain.getBlockChain().size(); i++) {
			currentBlock = BlockChain.getBlockChain().get(i);
			previousBlock = BlockChain.getBlockChain().get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			
		}
		return true;
	}

}
