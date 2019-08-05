/*******************************************************************************
 * 2008-2019 Public Domain
 * Contributors
 * Marco Lopes (marcolopespt@gmail.com)
 *******************************************************************************/
package org.dma.java.cipher;

public class BlowfishCipher extends CryptoCipher {

	public BlowfishCipher(String key) {
		super(key.getBytes(), CIPHERS.BLOWFISH);
	}

}
