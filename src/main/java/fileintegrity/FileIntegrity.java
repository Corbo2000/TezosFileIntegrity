package fileintegrity;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.math.BigDecimal;
import org.json.JSONObject;

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

   public static void verifyFileHash(String path, TezosWallet wallet, String SmartContractAddr) throws NoSuchAlgorithmException, IOException, Exception{

    MessageDigest digest = MessageDigest.getInstance("SHA-256");

    // Get root of Path & Create list of files
    File root = new File( path );
    File[] list = root.listFiles();

    if (list == null) return;
    // For every file in list
    for ( File f : list ) {
        // If there is folder in directory, recursively call verifyFileHash
        if ( f.isDirectory() ) {
            System.out.println( "Dir:" + f.getAbsoluteFile() );
            verifyFileHash( f.getAbsolutePath() , wallet, SmartContractAddr);
        }
        // Otherwise verify files on Tezos blockchain using fileCheck entry point
        else {
            BigDecimal amount = new BigDecimal("0");
            BigDecimal fee = new BigDecimal("0.1");
            // This Entry Point just needs the right Contract Address
            JSONObject jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), SmartContractAddr, amount,
                                                                    fee, "", "", "fileCheck",
                                                                    new String[]{f.getName(), calculateHash(digest, f.getAbsoluteFile())},
                                                                    false, Global.GENERIC_STANDARD);
            String opHash = (String) jsonObject.get("result");
            while(opHash.length() != 54){
              jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), SmartContractAddr, amount,
                                                                    fee, "", "", "fileCheck",
                                                                    new String[]{f.getName(), calculateHash(digest, f.getAbsoluteFile())}, false, Global.GENERIC_STANDARD);
              opHash = (String) jsonObject.get("result");
            }
            Boolean opHashIncluded = wallet.waitForAndCheckResult(opHash, 4);
            System.out.println("File: " +f.getName() +" - Verified: " +opHashIncluded);
        }
    }
 }

   public static void uploadFileHash(String path, TezosWallet wallet, String SmartContractAddr) throws NoSuchAlgorithmException, IOException, Exception{

      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      File root = new File( path );
      File[] list = root.listFiles();

      if (list == null) return;
      for ( File f : list ) {
          if ( f.isDirectory() ) {
              System.out.println( "Dir:" + f.getAbsoluteFile() );
              uploadFileHash( f.getAbsolutePath() , wallet, SmartContractAddr);
          }
          else {
              BigDecimal amount = new BigDecimal("0");
              BigDecimal fee = new BigDecimal("0.1");
              // This Entry Point just needs the right Contract Address
              JSONObject jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), SmartContractAddr, amount,
                                                                      fee, "", "", "certify",
                                                                      new String[]{f.getName(), calculateHash(digest, f.getAbsoluteFile())}, 
                                                                      false, Global.GENERIC_STANDARD);
              String opHash = (String) jsonObject.get("result");
              while(opHash.length() != 54){
                jsonObject = wallet.callContractEntryPoint(wallet.getPublicKeyHash(), SmartContractAddr, amount,
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
    // TezosWallet wallet1 = new TezosWallet("salon such fruit occur forest run cram fuel employ whisper danger decorate spend latin journey", 
    // "testWalletPass");
    // wallet1.setProvider("https://rpc.ghostnet.teztnets.xyz");
    // String rootDir = System.getProperty("user.dir");
    // String pathString = rootDir + "\\src\\test\\resources\\FilesToHash";
    // String smartContractAddr = "KT1W8oxKcTdzpEQQHQodnDifta1jfHBq5eSH";

    //uploadFileHash(pathString, wallet1, smartContractAddr); // Upload All Files Contained in FilesToHash
    //verifyFileHash(pathString, wallet1, smartContractAddr); // Verify All Files Contained in FilesToHash
   }
}