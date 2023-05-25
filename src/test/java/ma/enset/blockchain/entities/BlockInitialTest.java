package ma.enset.blockchain.entities;

import org.junit.jupiter.api.Test;

public class BlockInitialTest {

    @Test
    void validateBlock(){
        BlockInitial block=new BlockInitial(0,null,"First Block");
        System.out.println(block.validateBlock());
        //assertEquals()
    }
}
