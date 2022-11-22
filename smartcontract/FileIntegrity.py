import smartpy as sp

class Verification(sp.Contract):
    def __init__(self, certifier):
        self.init(verifiedFiles=sp.map(tkey=sp.TString, tvalue=sp.TString), 
        certifier=sp.address("tz1VBbfY5HqdNL34PyjkC4aNhoGACB2qM8SV"))
        #this is ghostnet address 

    @sp.entry_point
    def certify(self, params):
        sp.verify(sp.sender==self.data.certifier)
        sp.if sp.len(params.hash) == 64:
            self.data.verifiedFiles[params.fileName]= params.hash
        sp.else:
            sp.failwith("Invalid Hash")

    @sp.entry_point
    def fileCheck(self, params):
        hash = self.data.verifiedFiles[params.fileName]
        sp.verify(params.hashx == hash, "something went wrong")
        
        

@sp.add_test(name = "Certify")
def test():
    #anil = sp.test_account("Anil")
    #ibo = sp.test_account("Ibo")
    
    contract= Verification(certifier=sp.address("tz1VBbfY5HqdNL34PyjkC4aNhoGACB2qM8SV"))

    scenario = sp.test_scenario()

    scenario+= contract

    #address = sp.address()
    
    scenario+= contract.certify(hash= "f6ad436afeh7bf17d0d876dbe70cbc2ccc7575429e958cf7a7983d106ed4bee1", fileName= "File1").run(sender=sp.address("tz1VBbfY5HqdNL34PyjkC4aNhoGACB2qM8SV"))
    scenario+= contract.certify(hash= "0368942eeb2415ac23c09636b4b7b2afc487a0b95065e6bb3af137261eb304d7", fileName= "File2").run(sender=sp.address("tz1VBbfY5HqdNL34PyjkC4aNhoGACB2qM8SV"))
    scenario+= contract.fileCheck(fileName = "File2", hashx = "0368942eeb2415ac23c09636b4b7b2afc487a0b95065e6bb3af137261eb304d7")
    #scenario+= contract.fileCheck(fileName = "file1", verificationAddress = sp.address("KT1TezoooozzSmartPyzzSTATiCzzzwwBFA1"), hash = "0368942eeb2415ac23c09636b4b7b2afc487a0b95065e6bb3af137261eb304d7")