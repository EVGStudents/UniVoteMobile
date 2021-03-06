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
package ch.bfh.unicrypt.math.function.classes;

import ch.bfh.unicrypt.math.algebra.dualistic.classes.N;
import ch.bfh.unicrypt.math.algebra.general.classes.Pair;
import ch.bfh.unicrypt.math.algebra.general.classes.ProductSet;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Group;
import ch.bfh.unicrypt.math.algebra.general.interfaces.SemiGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.math.function.abstracts.AbstractFunction;
import ch.bfh.unicrypt.random.interfaces.RandomByteSequence;
import java.math.BigInteger;

/**
 * This class represents the the concept of a function f:XxZ->Y, where Z is an atomic group. The second input element
 * can thus be transformed into an integer value z, which determines the number of times the group operation is applied
 * to the first input element.
 * <p/>
 * @see Group#selfApply(Element, Element)
 * @see Element#selfApply(Element)
 * <p/>
 * @author R. Haenni
 * @author R. E. Koenig
 * @version 1.0
 */
public class SelfApplyFunction
	   extends AbstractFunction<SelfApplyFunction, ProductSet, Pair, SemiGroup, Element> {

	private SelfApplyFunction(final ProductSet domain, final SemiGroup coDomain) {
		super(domain, coDomain);
	}

	//
	// The following protected method implements the abstract method from {@code AbstractFunction}
	//
	@Override
	protected Element abstractApply(final Pair element, final RandomByteSequence randomByteSequence) {
		Element element1 = element.getFirst();
		Element<BigInteger> element2 = (Element<BigInteger>) element.getSecond();
		return element1.selfApply(element2.getValue());
	}

	//
	// STATIC FACTORY METHODS
	//
	/**
	 * This is a special constructor, where the group of the second parameter is selected automatically from the given
	 * group.
	 * <p/>
	 * @param semiGroup The underlying group
	 * @return
	 * @throws IllegalArgumentException if {@literal group} is null
	 */
	public static SelfApplyFunction getInstance(final SemiGroup semiGroup) {
		if (semiGroup == null) {
			throw new IllegalArgumentException();
		}
		if (semiGroup.isFinite() && semiGroup.hasKnownOrder()) {
			return SelfApplyFunction.getInstance(semiGroup, semiGroup.getZModOrder());
		}
		return SelfApplyFunction.getInstance(semiGroup, N.getInstance());
	}

	/**
	 * This is the general constructor of this class. The first parameter is the group on which it operates, and the
	 * second parameter is the atomic group, from which an element is needed to determine the number of times the group
	 * operation is applied.
	 * <p/>
	 * @param semiGroup The underlying group
	 * @param amountSet
	 * @return
	 * @throws IllegalArgumentException if {@literal group} is null
	 * @throws IllegalArgumentException if {@literal amountGroup} is negative
	 */
	public static SelfApplyFunction getInstance(final SemiGroup semiGroup, final Set<BigInteger> amountSet) {
		if (semiGroup == null || amountSet == null) {
			throw new IllegalArgumentException();
		}
		return new SelfApplyFunction(ProductSet.getInstance(semiGroup, amountSet), semiGroup);
	}

}
