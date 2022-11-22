# TezosFileIntegrity

======== VERSION 1.0 ===========

This Github repo contains the java code necessary to invoke a tezos smart contract for file integrity. It also contains the smart contract that is being invoked.

## Requirements

- Java 8
- Maven 3.8.6
- Windows / Linux / Mac
- Visual Studio Code or Suitable Java IDE

## SmartPy Smart Contract

The SmartPy Smart Contract is linked under '\smartcontract\FileIntegrity.py'. This python script can be uploaded and deployed using SmartPy.io/ide. Alternatively you can use this bitly link to import the smart contract code: https://bit.ly/3Md4AHN.

## Test Methods

- **testUploadFileHash** - 
This test method calls the uploadFileHash method. Change the wallet, file path, & smart contract to match your deployed SmartPy smart contract. If done correctly you should see 'Upload Status: true' returned in the debug console for every file that is uploaded.  

- **testVerifyFileHash** - 
This test method calls the verifyFileHash method. Change the wallet, file path, & smart contract to match your deployed SmartPy smart contract. If done correctly you should see 'Verified: true' returned in the debug console for every file that is verified.  

- **createTezosWallet** - 
This test method creates a tezos wallet on the ghostnet testnet. It then displays the Mnemonic Words / Public Key Hash for the new wallet. The wallet is created with a Passphrase that can be changed to whatever the user wants. The Mnemonic Words and Passphrase must be saved if that wallet is needed to be used later. The default Passphrase is 'testWalletPass'.

## Complete Walkthrough

This operations document can be used as a walkthrough: https://docs.google.com/document/d/1QE5z_H8o5s8vDtnJTXsKfIWy0po-WxsDUhqk7nwe8Zk/edit?usp=sharing.
It shows how to create a wallet, deploy the smart contract, & call the contract using this github repo. 