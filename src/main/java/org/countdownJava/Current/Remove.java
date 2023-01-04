package org.countdownJava.Current;

public class Remove {
	public boolean removeCheck(byte[] postfix) {
		int minusDivideCheck = 0, plusCheck = 0, multiplyCheck = 0;

		for (byte token : postfix) {
			switch (token) {
				case -1 -> plusCheck++;
				case -3 -> multiplyCheck++;
				case -2, -4 -> minusDivideCheck++;
			}
		}

		return (plusCheck < 5 && minusDivideCheck < 5 && multiplyCheck < 5);
	}
}
