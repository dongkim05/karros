package com.karros.vn.utils;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base32;

public final class UuidUtils {

	/**
	 * Constructor (invisible).
	 */
	private UuidUtils() {

	}

	/**
	 * Static factory to retrieve a type 4 (pseudo randomly generated) UUID.
	 *
	 * The {@code UUID} is generated using a cryptographically strong pseudo
	 * random number generator.
	 *
	 * @return A randomly generated {@code UUID} in lower case
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().toLowerCase().replace("-", "");
	}

	/**
	 * Static factory to retrieve a type 4 (pseudo randomly generated) UUID, encoded by Base32.
	 *
	 * @return A randomly generated UUID, in lower case
	 */
	public static String randomUUIDBase32() {
		UUID uuid = UUID.randomUUID();
		ByteBuffer uuidBuffer = ByteBuffer.allocate(16);
		LongBuffer longBuffer = uuidBuffer.asLongBuffer();
		longBuffer.put(uuid.getMostSignificantBits());
		longBuffer.put(uuid.getLeastSignificantBits());

		// removes the '==' padding
		return new Base32().encodeAsString(uuidBuffer.array()).substring(0, 26).toLowerCase();
	}

}
