package fileintegrity;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.math.BigDecimal;
import org.json.JSONObject;

import milfont.com.tezosj.domain.Crypto;
import milfont.com.tezosj.helper.Global;
import milfont.com.tezosj.model.TezosWallet;

public class FileIntegrity {

   static String calculateHash(MessageDigest digest, File file) throws IOException{
    
      //Get file input stream for reading the file content
      FileInputStream fis = new FileInputStream(file);

      //Create byte array to read data in chunks
      byte[] byteArray = new byte[1024];
      int bytesCount = 0; 
        
      //Read file data and update in message digest
      while ((bytesCount = fis.read(byteArray)) != -1) {
        digest.update(byteArray, 0, bytesCount);
      };
      
      //close the stream; We don't need it now.
      fis.close();
      
      //Get the hash's bytes
      byte[] bytes = digest.digest();
      
      //This bytes[] has bytes in decimal format;
      //Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for(int i=0; i< bytes.length ;i++)
      {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      //System.out.println(sb.toString());

      return sb.toString();
   }

   public static void verifyFileHash(String path, TezosWallet wallet) throws NoSuchAlgorithmException, IOException, Exception{

    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    File root = new File( path );
    File[] list = root.listFiles();

    if (list == null) return;
    for ( File f : list ) {
        if ( f.isDirectory() ) {
            System.out.println( "Dir:" + f.getAbsoluteFile() );
            verifyFileHash( f.getAbsolutePath() , wallet);
        }
        else {
            //System.out.println( "File:" + f.getName() );

            BigDecimal amount = new BigDecimal("0");
            BigDecimal fee = new BigDecimal("0.1");
            // This Entry Point just needs the right Contract Address
            JSONObject jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), "KT1BaBwc7XnLULgK9VndxFmKrLsTfes9oPki", amount,
                                                                    fee, "", "", "fileCheck",
                                                                    new String[]{f.getName(), calculateHash(digest, f.getAbsoluteFile())},
                                                                    false, Global.GENERIC_STANDARD);
            String opHash = (String) jsonObject.get("result");
            while(opHash.length() != 54){
              jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), "KT1BaBwc7XnLULgK9VndxFmKrLsTfes9oPki", amount,
                                                                    fee, "", "", "fileCheck",
                                                                    new String[]{f.getName(), calculateHash(digest, f.getAbsoluteFile())}, false, Global.GENERIC_STANDARD);
              opHash = (String) jsonObject.get("result");
            }
            Boolean opHashIncluded = wallet.waitForAndCheckResult(opHash, 4);
            System.out.println("File: " +f.getName() +" - Verified: " +opHashIncluded);
        }
    }
 }

   public static void uploadFileHash(String path, TezosWallet wallet) throws NoSuchAlgorithmException, IOException, Exception{

      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      File root = new File( path );
      File[] list = root.listFiles();

      if (list == null) return;
      for ( File f : list ) {
          if ( f.isDirectory() ) {
              System.out.println( "Dir:" + f.getAbsoluteFile() );
              uploadFileHash( f.getAbsolutePath() , wallet);
          }
          else {
              //System.out.println( "File:" + f.getName() );

              BigDecimal amount = new BigDecimal("0");
              BigDecimal fee = new BigDecimal("0.1");
              // This Entry Point just needs the right Contract Address
              JSONObject jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), "KT1BaBwc7XnLULgK9VndxFmKrLsTfes9oPki", amount,
                                                                      fee, "", "", "certify",
                                                                      new String[]{f.getName(), calculateHash(digest, f.getAbsoluteFile())}, 
                                                                      false, Global.GENERIC_STANDARD);
              String opHash = (String) jsonObject.get("result");
              while(opHash.length() != 54){
                jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), "KT1BaBwc7XnLULgK9VndxFmKrLsTfes9oPki", amount,
                                                                      fee, "", "", "certify",
                                                                      new String[]{f.getName(), calculateHash(digest, f.getAbsoluteFile())},
                                                                       false, Global.GENERIC_STANDARD);
                opHash = (String) jsonObject.get("result");
              }
              Boolean opHashIncluded = wallet.waitForAndCheckResult(opHash, 4);
              System.out.println("File: " +f.getName() +" - Upload Status: " +opHashIncluded);
          }
      }
   }

   public static void main(String[] args) throws Exception
   {
    // This is Wallet 1 on the Ghostnet Testnet
    // TezosWallet wallet1 = new TezosWallet("cube security region mouse wash holiday rural pass pretty assist anxiety movie stay success zebra", 
    //                         "CorboPassphrase");
    // wallet1.setProvider("https://rpc.ghostnet.teztnets.xyz");
    // String pathString = "G:\\FutureSenseCode\\fileintegrityartifactid\\FilesToHash";

    //uploadFileHash(pathString, wallet1); // Goes through every file in File Directory pathString. Doesn't return anything
    //verifyFileHash(pathString, wallet1);
   }
}