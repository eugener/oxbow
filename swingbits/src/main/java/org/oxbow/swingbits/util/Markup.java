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

import java.awt.Color;
import java.awt.Font;

public final class Markup {

    private static final String HTML_START = "<html>";
    private static final String HTML_END   = "</html>";
    private static final String HTML_BREAK = "<br>";

    private Markup() {}

    public static final String toHex( Color color ) {
        color = color == null ? Color.BLACK: color;
        String rgb = Integer.toHexString(color.getRGB());
        return rgb.substring(2, rgb.length());
    }

    /**
     * Converts string to simple &lt;HTML&gt; presentation.<br>
     * Replaces EOLs with &lt;BR&gt; tags
     * @param s
     * @param finalize if true wraps text with &lt;HTML&gt; tags
     * @return
     */
    public final static String toHTML( String s, boolean finalize ) {

        s = s == null? "": s.replaceAll("\n", HTML_BREAK);
        String tmp = s.trim().toLowerCase();

        StringBuilder sb = new StringBuilder(s);

        if ( finalize ) {
            if ( !tmp.startsWith(HTML_START)) sb.insert(0, HTML_START);
            if ( !tmp.endsWith(HTML_END))     sb.append( HTML_END );
        }

        return sb.toString();
    }

    /**
     * Converts string to simple &lt;HTML&gt; presentation.<br>
     * Replaces EOLs with &lt;BR&gt; tags, wraps text with &lt;HTML&gt; tags
     * @param s
     * @return
     */
    public final static String toHTML( String s ) {
        return toHTML( s, true );
    }

    /**
     * Converts font to CSS style
     * @param font
     * @return CSS style as string
     */
    public final static String toCSS( Font font ) {

        return String.format( "font-family: \"%s\"; %s; %s;",
                  font.getFamily(),
                  toSizeCSS(font),
                  toStyleCSS(font) );

    }

    public final static String toSizeCSS( Font font ) {
        //TODO: use Toolkit.getDefaultToolkit().getScreenResolution() to calculate font size in pixels

        return String.format( "font-size: %fpx", font.getSize() * .75); // converts to pixels with standard DPI
    }

    public final static String toStyleCSS( Font font ) {

        switch ( font.getStyle() ) {
            case Font.ITALIC: return "font-style : italic";
            case Font.BOLD  : return "font-weight: bold";
            default         : return "font-weight: normal";

        }

    }

    /**
     * Converts color to CSS style
     * @param color
     * @return CSS style as string
     */
    public final static String toCSS( Color color ) {
        return  String.format( "color: #%s;", toHex(color));
    }

}
