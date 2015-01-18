package ch.bfh.univotemobile.classes;

import ch.bfh.unicrypt.helper.factorization.Prime;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import ch.bfh.unicrypt.math.algebra.multiplicative.classes.GStarModElement;
import ch.bfh.unicrypt.math.algebra.multiplicative.classes.GStarModPrime;
import ch.bfh.univotemobile.interfaces.CryptographicUnicertIssuerSetup;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;

public class DLogUnicertIssuerSetup implements CryptographicUnicertIssuerSetup {

    /**
     * Size of the keys
     */ 
    private final int size;
    
    /**
     * Cyclic modular group
     */
    private final GStarModPrime G_q;
    
    /**
     * The modular additive group of order q
     */
    private final ZMod Z_q;
    /**
     * Generator of the cyclic group
     */
    private final GStarModElement generator;
    /**
     * Safe prime
     */
    private final Prime p;
    private final Prime q;
    
    private final GStarModElement publicKey;
    private final BigInteger proofCommitment;
    private final BigInteger proofResponse;
    private String proofOtherInput;
    private final BigInteger proofChallenge;

    /**
     * Create an object representing an discrete log (DSA, Elgamal) setup
     * @param size size of the keys
     * @param g generator of the cyclic group
     * @param p prime value
     * @param q prime value
     * @param pk public key
     * @param proofCommitment commitment part of the proof of knowledge of the private key
     * @param proofChallenge challenge part of the proof of knowledge of the private key
     * @param proofResponse response part of the proof of knowledge of the private key
     */
    public DLogUnicertIssuerSetup(int size, BigInteger g, BigInteger p, BigInteger q, BigInteger pk, BigInteger proofCommitment, BigInteger proofChallenge, BigInteger proofResponse) {
        this.size = size;
        
        this.p = Prime.getInstance(p);
        this.q = Prime.getInstance(q);

        this.G_q = GStarModPrime.getInstance(p, q);
        this.Z_q = G_q.getZModOrder();
        this.generator = G_q.getElement(g);
        
        this.publicKey = G_q.getElement(pk);
        this.proofCommitment = proofCommitment;
        this.proofChallenge = proofChallenge;
        this.proofResponse = proofResponse;
    }

    /**
     * Get the size of the keys
     * @return the size of the keys
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the modular cyclic group
     * @return the modular cyclic group
     */
    public GStarModPrime getG_q() {
        return G_q;
    }

    /**
     * Get the modular additive group of order q
     * @return the modular additive group of order q
     */
    public ZMod getZ_q() {
        return Z_q;
    }

    /**
     * Get the generator of the cyclic group
     * @return the generator of the cyclic group
     */
    public GStarModElement getGenerator() {
        return generator;
    }
    
    /**
     * Get the safe prime
     * @return the safe prime
     */
    public Prime getP() {
        return p;
    }

    /**
     * Get the public key
     * @return the public key
     */
    public GStarModElement getPublicKey() {
        return publicKey;
    }

    /**
     * Get the commitment part of the proof of knowledge of the secret key
     * @return the commitment part of the proof
     */
    public BigInteger getProofCommitment() {
        return proofCommitment;
    }
    
    /**
     * Get the challenge part of the proof of knowledge of the secret key
     * @return the challenge part of the proof
     */
    public BigInteger getProofChallenge() {
        return proofChallenge;
    }
    
    /**
     * Get the response part of the proof of knowledge of the secret key
     * @return the response part of the proof
     */
    public BigInteger getProofResponse() {
        return proofResponse;
    }
    
    /**
     * Get the string containing other values that were linked in the proof
     * @return the string of values included in the proof
     */
    public String getProofOtherInput() {
        return proofOtherInput;
    }

    /**
     * Set the string containing other values that were linked in the proof
     * @param proofPublicInput the string of values included in the proof
     */
    @Override
    public void setSignatureOtherInput(String proofPublicInput) {
        this.proofOtherInput = proofPublicInput;
    }

	public Prime getQ() {
		return q;
	}

	@Override
	public String getBody(UserData userData) {
		String body = null;
		try {
			body = "crypto_setup_type=" + URLEncoder.encode( "DiscreteLog", "UTF-8" ) + "&" +
			"crypto_setup_size=" + URLEncoder.encode( Integer.toString(this.getSize()), "UTF-8" ) + "&" + 
			"dlog_p=" + URLEncoder.encode( this.getP().getValue().toString(), "UTF-8" ) + "&" + 
			"dlog_q=" + URLEncoder.encode( this.getQ().getValue().toString(), "UTF-8" ) + "&" + 
			"dlog_generator=" + URLEncoder.encode( this.getGenerator().getValue().toString(), "UTF-8" ) + "&" + 
			"identity_function=" + URLEncoder.encode( Integer.toString(userData.getIdentityFunction()), "UTF-8" ) + "&" + 
			"public_key=" + URLEncoder.encode( this.getPublicKey().getValue().toString(), "UTF-8" ) + "&" + 
			"dlog_proof_commitment=" + URLEncoder.encode( this.getProofCommitment().toString(), "UTF-8" ) + "&" + 
			"dlog_proof_challenge=" + URLEncoder.encode( this.getProofChallenge().toString(), "UTF-8" ) + "&" + 
			"dlog_proof_response=" + URLEncoder.encode( this.getProofResponse().toString(), "UTF-8" ) + "&" +
			"application_identifier=" + URLEncoder.encode(userData.getApplicationIdentifier(), "UTF-8" ) + "&" +
			"role=" + URLEncoder.encode(userData.getRole(), "UTF-8" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return body;
	}
}
