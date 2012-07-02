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

/**
 * Utility class to allow OS determination
 *
 * Created on Mar 11, 2010
 * @author Eugene Ryzhikov
 *
 */
public enum OperatingSystem {

	WINDOWS( "windows" ),
	MACOS( "mac" ),
	LINUX( "linux" ),
	UNIX( "nix" ),
	SOLARIS( "solaris" ),
	
	UNKNOWN( "unknown" ) {
		@Override protected boolean isReal() { return false; }
	};


	private String tag;

	OperatingSystem( String tag ) {
		this.tag = tag;
	}

	public boolean isCurrent() {
		return isReal() && getName().toLowerCase().indexOf(tag) >=0;
	}

	public static final String getName() {
		return System.getProperty("os.name");
	}

	public static final String getVersion() {
		return System.getProperty("os.version");
	}

	public static final String getArchitecture() {
		return System.getProperty("os.arch");
	}

	@Override
	public final String toString() {
		return String.format("%s v%s (%s)", getName(), getVersion(), getArchitecture() );
	}
	
	protected boolean isReal() {
		return true;
	}

	/**
	 * Returns current operating system
	 * @return current operating system or UNKNOWN if not found
	 */
	public static final OperatingSystem getCurrent() {

		for( OperatingSystem os: OperatingSystem.values() ) {
			if ( os.isCurrent() ) return os;
		}
		return UNKNOWN;
	}

}