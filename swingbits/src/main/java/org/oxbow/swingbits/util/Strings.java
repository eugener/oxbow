/*
 * Copyright (c) 2009-2011, EzWare
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.Redistributions
 * in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.Neither the name of the
 * EzWare nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior
 * written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.oxbow.swingbits.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Strings {

	private Strings(){};

	/**
	 * Check if string is empty
	 * @param s
	 * @return true if string is null or empty (white spaces are not counted)
	 */
	public final static boolean isEmpty( String s ) {
		return s == null || s.trim().length() == 0;
	}


	/**
	 * Returns safe string - never null
	 * @param s
	 * @return
	 */
	public final static String safe( String s ) {
		return isEmpty(s)? "": s;
	}

	/**
	 * Capitalize every word in the string
	 * @param s
	 * @return
	 */
	public final static String capitalize( String s ) {

		if ( isEmpty(s) ) return s;

		StringBuilder sb = new StringBuilder();
		for( String w: s.split(" ") ) {
			sb.append( capitalizeWord(w) );
			sb.append(' ');
		}
		return sb.toString().trim();
	}

	/**
	 * Capitalizes given word
	 * @param word
	 * @return
	 */
	private final static String capitalizeWord( String word ) {

		if ( isEmpty(word) ) return word;
		String capital = word.substring(0, 1).toUpperCase();
		return ( word.length() == 1 )? capital: capital + word.substring(1).toLowerCase();

	}


	/**
	 * Converts exception stack trace as string
	 * @param ex
	 * @return
	 */
	public static final String stackStraceAsString( Throwable ex ) {
		StringWriter sw = new StringWriter();
	    ex.printStackTrace(new PrintWriter(sw));
	    return sw.toString();
	}

}
