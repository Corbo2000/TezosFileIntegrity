package fileintegrity;

import org.junit.Test;
import milfont.com.tezosj.model.TezosWallet;

/**
 * Unit test for simple App.
 */
public class FileIntegrityTest 
{
    /**
     * Rigorous Test 
     */
    @Test
    public void testUploadFileHash() throws Exception
    {
        TezosWallet wallet1 = new TezosWallet("cube security region mouse wash holiday rural pass pretty assist anxiety movie stay success zebra", 
        "CorboPassphrase");
        wallet1.setProvider("https://rpc.ghostnet.teztnets.xyz");
        String pathString = "G:\\FutureSenseCode\\fileintegrityartifactid\\FilesToHash";

        FileIntegrity.uploadFileHash(pathString, wallet1); // Goes through every file in File Directory pathString. Doesn't return anything
    }
    @Test
    public void testVerifyFileHash() throws Exception
    {
        TezosWallet wallet1 = new TezosWallet("cube security region mouse wash holiday rural pass pretty assist anxiety movie stay success zebra", 
        "CorboPassphrase");
        wallet1.setProvider("https://rpc.ghostnet.teztnets.xyz");
        String pathString = "G:\\FutureSenseCode\\fileintegrityartifactid\\FilesToHash";

        FileIntegrity.verifyFileHash(pathString, wallet1);
    }
    @Test
    public void createTezosWallet() throws Exception
    {
        TezosWallet wallet = new TezosWallet("testWalletPass");
        wallet.setProvider("https://rpc.ghostnet.teztnets.xyz");
        System.out.println(wallet.getMnemonicWords());
        System.out.println(wallet.getPublicKeyHash());
    }
}