package com.hydro4ge.mycircle.client;

import junit.framework.Assert;
import junit.framework.TestCase;


public class TestColorConversion extends TestCase {

	public void testRGBToHexConversion() {
		int number = 191;
		String hex = Integer.toHexString( 0x100 | number).substring(1);
		System.out.println(hex);
		Assert.assertEquals("bf", hex);
		
		number = 255;
		hex = Integer.toHexString( 0x100 | number).substring(1);
		System.out.println(hex);
		Assert.assertEquals("ff", hex);
		
		number = 0;
		hex = Integer.toHexString( 0x100 | number).substring(1);
		System.out.println(hex);
		Assert.assertEquals("00", hex);
		
		number = 1;
		hex = Integer.toHexString( 0x100 | number).substring(1);
		System.out.println(hex);
		Assert.assertEquals("01", hex);
		
		number = 10;
		hex = Integer.toHexString( 0x100 | number).substring(1);
		System.out.println(hex);
		Assert.assertEquals("0a", hex);
	}
	
}
