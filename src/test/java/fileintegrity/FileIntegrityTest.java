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
        TezosWallet wallet1 = new TezosWallet("salon such fruit occur forest run cram fuel employ whisper danger decorate spend latin journey", 
        "testWalletPass");
        wallet1.setProvider("https://rpc.ghostnet.teztnets.xyz");
        String pathString = "C:\\GitHub Stuff\\TezosFileIntegrity\\FilesToHash";
        String smartContractAddr = "KT1W8oxKcTdzpEQQHQodnDifta1jfHBq5eSH";

        System.out.println("Starting File Upload:");
        FileIntegrity.uploadFileHash(pathString, wallet1, smartContractAddr); // Goes through every file in File Directory pathString. Doesn't return anything
        System.out.println("Upload Complete!");
    }
    @Test
    public void testVerifyFileHash() throws Exception
    {
        TezosWallet wallet1 = new TezosWallet("salon such fruit occur forest run cram fuel employ whisper danger decorate spend latin journey", 
        "testWalletPass");
        wallet1.setProvider("https://rpc.ghostnet.teztnets.xyz");
        String pathString = "C:\\GitHub Stuff\\TezosFileIntegrity\\FilesToHash";
        String smartContractAddr = "KT1W8oxKcTdzpEQQHQodnDifta1jfHBq5eSH";

        System.out.println("Starting File Verification:");
        FileIntegrity.verifyFileHash(pathString, wallet1, smartContractAddr);
        System.out.println("Verification Complete!");
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