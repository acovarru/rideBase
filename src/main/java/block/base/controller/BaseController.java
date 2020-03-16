package block.base.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.algorand.algosdk.account.Account;
import com.algorand.algosdk.algod.client.AlgodClient;
import com.algorand.algosdk.algod.client.ApiException;
import com.algorand.algosdk.algod.client.api.AlgodApi;
import com.algorand.algosdk.algod.client.auth.ApiKeyAuth;
import com.algorand.algosdk.algod.client.model.NodeStatus;
import com.algorand.algosdk.algod.client.model.Block;

import block.base.model.BlockChain;
import block.base.util.BlockUtil;

@Controller
public class BaseController
{
	final String ALGOD_API_ADDR = "http://localhost:4001";
    final String ALGOD_API_TOKEN = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	
    @GetMapping("/demo")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="Ride") String name, Model model) throws NoSuchAlgorithmException {
    	model.addAttribute("name", name);


    	//Create an instance of the algod API client
    	AlgodClient client = (AlgodClient) new AlgodClient();
    	client.setBasePath(ALGOD_API_ADDR);
    	ApiKeyAuth api_key = (ApiKeyAuth) client.getAuthentication("api_key");
    	api_key.setApiKey(ALGOD_API_TOKEN);
    	
    	//Creaste algodApiInstance
    	AlgodApi algodApiInstance = new AlgodApi(client); 
    	
    	//Instantiate new block
    	Block block = new Block();
    	block.setProposer(ALGOD_API_TOKEN);
    	block.setUpgradePropose("block1");
    	block.isUpgradeApprove();
    	
    	Account myAccount = new Account();
        System.out.println("My Address: " + myAccount.getAddress());
        System.out.println("My Passphrase: " + myAccount.toMnemonic());
        
    	try {
    		NodeStatus status = algodApiInstance.getStatus();

    		System.out.println("Algorand network status: " + status);
    		System.out.println("Block hash: " + block.getHash());
    		System.out.println("Block approved?: " + block.isUpgradeApprove());
    	} catch (ApiException e) {
    		System.err.println("Exception when calling algod#getStatus");
    		e.printStackTrace();
    	}

    	return "greeting";
    }
	
	@GetMapping("/addBlock")
    public String addBlock(@RequestParam(name="name", required=false, defaultValue="Block") String name, Model model) {
        model.addAttribute("name", name);
        
        System.out.println("Trying to Mine block 1... ");
       // BlockUtil.addBlock(new Block("Yo im the second block",BlockChain.getBlockChain().get(BlockChain.getBlockChain().size()-1).hash));
	     
		System.out.println("\nThe block chain: ");
		System.out.println(BlockChain.getBlockChain());
		 
        return "greeting";
    }
	
	@GetMapping("/validateChain")
    public String validateBlockChain(@RequestParam(name="name", required=false, defaultValue="Ride") String name, Model model) {
        model.addAttribute("name", name);
        
        System.out.println("\nBlockchain is Valid: " + BlockUtil.isChainValid());
		 
        return "greeting";
    }

}
