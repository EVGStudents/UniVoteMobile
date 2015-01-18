package ch.bfh.univotemobile.classes;

import java.math.BigInteger;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import ch.bfh.unicrypt.crypto.proofsystem.challengegenerator.classes.FiatShamirSigmaChallengeGenerator;
import ch.bfh.unicrypt.crypto.proofsystem.classes.PreimageProofSystem;
import ch.bfh.unicrypt.helper.Alphabet;
import ch.bfh.unicrypt.helper.converter.classes.ConvertMethod;
import ch.bfh.unicrypt.helper.converter.classes.biginteger.FiniteByteArrayToBigInteger;
import ch.bfh.unicrypt.helper.converter.classes.bytearray.BigIntegerToByteArray;
import ch.bfh.unicrypt.helper.converter.classes.bytearray.StringToByteArray;
import ch.bfh.unicrypt.helper.hash.HashAlgorithm;
import ch.bfh.unicrypt.helper.hash.HashMethod;
import ch.bfh.unicrypt.math.algebra.concatenative.classes.StringElement;
import ch.bfh.unicrypt.math.algebra.concatenative.classes.StringMonoid;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import ch.bfh.unicrypt.math.algebra.general.classes.Triple;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.multiplicative.classes.GStarModPrime;
import ch.bfh.unicrypt.math.function.classes.GeneratorFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;

/**
 * Factory for Discrete Log Setup. 
 * The Discrete Log Setup is needed for the creation of the user certificate.
 * @author Raphael Hänni
 */
public class DLogSetupFactory {

	private static final String SEPARATOR = "|";
	private int setupSize;
	private BigInteger p, q, generator;
	private BigInteger privateKey;
	private BigInteger publicKey;
	private Element<BigInteger> privateKeyElement, publicKeyElement, generatorElement;
	private GStarModPrime G_q;
	private ZMod Z_q;
	
	/**
	 * Creates new DLogSetupFactory.
	 * @param setupSize The setup size of the DiscreteLog.
	 * @param p prime value
	 * @param q prime value
	 * @param generator generator of the cyclic group
	 */
	public DLogSetupFactory(int setupSize, BigInteger p, BigInteger q, BigInteger generator) {
		this.setupSize = setupSize;
		this.p = p;
		this.q = q;
		this.generator = generator;
		
		this.G_q = GStarModPrime.getInstance(this.p, this.q);
		this.Z_q = G_q.getZModOrder();
		
		this.generatorElement = G_q.getElement(generator);
		this.privateKeyElement = Z_q.getRandomElement();
		this.publicKeyElement = this.generatorElement.selfApply(privateKeyElement);
	
		this.privateKey = privateKeyElement.getBigInteger();
		this.publicKey = publicKeyElement.getBigInteger();
	}
	
	
	/**
	 * Returns the Discrete Log Setup.
	 * @return The Discrete Log Setup.
	 */
	public DLogUnicertIssuerSetup getDLogSetup(UserData userData) {
		String originalMessage = userData.getIdentityProvider() + SEPARATOR + userData.getMail() + SEPARATOR
				+ userData.getUniqueIdentifier() + SEPARATOR + "DiscreteLog" + SEPARATOR + setupSize + SEPARATOR
				+ publicKey + SEPARATOR + p + SEPARATOR + q + SEPARATOR + generator + SEPARATOR
				+ userData.getIdentityFunction() + SEPARATOR + userData.getApplicationIdentifier() 
				+ SEPARATOR + userData.getRole();
		
		StringElement message = StringMonoid.getInstance(Alphabet.UNICODE_BMP).getElement(originalMessage);
		Function func = GeneratorFunction.getInstance(this.generatorElement);
		HashMethod hashMethod = HashMethod.getInstance(HashAlgorithm.SHA256, 
				ConvertMethod.getInstance(BigIntegerToByteArray.getInstance(ByteOrder.BIG_ENDIAN), StringToByteArray.getInstance(Charset.forName("UTF-8"))), HashMethod.Mode.RECURSIVE);
		FiniteByteArrayToBigInteger byteArrayConverter = FiniteByteArrayToBigInteger.getInstance(HashAlgorithm.SHA256.getHashLength());
		FiatShamirSigmaChallengeGenerator scg = FiatShamirSigmaChallengeGenerator.getInstance(G_q, G_q, Z_q, message, hashMethod, byteArrayConverter);
		PreimageProofSystem pips = PreimageProofSystem.getInstance(scg, func);
		
		Triple proof = pips.generate(this.privateKeyElement, this.publicKeyElement);
		return new DLogUnicertIssuerSetup(setupSize, generator, p, q, this.publicKey, proof.getFirst().getBigInteger(), proof.getSecond().getBigInteger(), proof.getThird().getBigInteger());
	}
	
	/**
	 * Returns the private key.
	 * @return The private key
	 */
	public BigInteger getPrivateKey() {
		return this.privateKey;
	}
}