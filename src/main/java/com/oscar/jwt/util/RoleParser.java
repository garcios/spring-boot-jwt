package com.oscar.jwt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author oscargarcia
 *
 */
public class RoleParser {

	/**
	 * Parse a string of the form "Role [name=USER, privileges=null]"
	 * 
	 * @param txt
	 * @return
	 */
	public static String getRole(String txt){
		
		Pattern p = Pattern.compile("(.*)name=(.*), (.*)");
		
		Matcher m = p.matcher(txt);
		
		if (m.find()){
			return m.group(2);
		}
		
		return null;
		
	}
	
}
