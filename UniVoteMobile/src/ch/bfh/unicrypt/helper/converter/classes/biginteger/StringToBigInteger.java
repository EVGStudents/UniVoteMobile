/*
 * UniCrypt
 *
 *  UniCrypt(tm) : Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
 *  Copyright (C) 2014 Bern University of Applied Sciences (BFH), Research Institute for
 *  Security in the Information Society (RISIS), E-Voting Group (EVG)
 *  Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *  Licensed under Dual License consisting of:
 *  1. GNU Affero General Public License (AGPL) v3
 *  and
 *  2. Commercial license
 *
 *
 *  1. This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *  2. Licensees holding valid commercial licenses for UniCrypt may use this file in
 *   accordance with the commercial license agreement provided with the
 *   Software or, alternatively, in accordance with the terms contained in
 *   a written agreement between you and Bern University of Applied Sciences (BFH), Research Institute for
 *   Security in the Information Society (RISIS), E-Voting Group (EVG)
 *   Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *   For further information contact <e-mail: unicrypt@bfh.ch>
 *
 *
 * Redistributions of files must retain the above copyright notice.
 */
package ch.bfh.unicrypt.helper.converter.classes.biginteger;

import ch.bfh.unicrypt.helper.Alphabet;
import ch.bfh.unicrypt.helper.converter.abstracts.AbstractBigIntegerConverter;
import java.math.BigInteger;

/**
 *
 * @author Rolf Haenni <rolf.haenni@bfh.ch>
 */
public class StringToBigInteger
	   extends AbstractBigIntegerConverter<String> {

	private final Alphabet alphabet;
	private final int blockLength;

	protected StringToBigInteger(Alphabet alphabet, int blockLength) {
		super(String.class);
		this.alphabet = alphabet;
		this.blockLength = blockLength;
	}

	@Override
	protected BigInteger abstractConvert(String value) {
		BigInteger value1 = BigInteger.ZERO;
		BigInteger alphabetSize = BigInteger.valueOf(this.alphabet.getSize());
		for (int i = 0; i < value.length(); i++) {
			int charIndex = this.alphabet.getIndex(value.charAt(i));
			value1 = value1.multiply(alphabetSize).add(BigInteger.valueOf(charIndex));
		}
		BigInteger value2 = BigInteger.ZERO;
		BigInteger blockSize = alphabetSize.pow(this.blockLength);
		for (int i = 0; i < value.length() / this.blockLength; i++) {
			value2 = value2.multiply(blockSize).add(BigInteger.ONE);
		}
		return value1.add(value2);
	}

	@Override
	protected String abstractReconvert(BigInteger value) {
		StringBuilder strBuilder = new StringBuilder();
		BigInteger alphabetSize = BigInteger.valueOf(this.alphabet.getSize());
		BigInteger blockSize = alphabetSize.pow(this.blockLength);
		while (!value.equals(BigInteger.ZERO)) {
			value = value.subtract(BigInteger.ONE);
			BigInteger remainder = value.mod(blockSize);
			for (int i = 0; i < this.blockLength; i++) {
				strBuilder.append(this.alphabet.getCharacter(remainder.mod(alphabetSize).intValue()));
				remainder = remainder.divide(alphabetSize);
			}
			value = value.divide(blockSize);
		}
		return strBuilder.reverse().toString();
	}

	public static StringToBigInteger getInstance(Alphabet alphabet, int blockLength) {
		return new StringToBigInteger(alphabet, blockLength);
	}

}
