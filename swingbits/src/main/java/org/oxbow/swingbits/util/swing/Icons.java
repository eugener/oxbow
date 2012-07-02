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

package org.oxbow.swingbits.util.swing;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Utility class for icon manipulation
 *
 * Created on Mar 1, 2010
 * @author Eugene Ryzhikov
 *
 */
public final class Icons  {

	private Icons() {}

	/**
	 * Converts any icon to image
	 * @param icon icon to convert
	 * @return resulted image
	 */
	public static final Image asImage( Icon icon ) {

		if ( icon == null ) throw new IllegalArgumentException("The icon should not be null");

		if ( icon instanceof ImageIcon ) return ((ImageIcon)icon).getImage();
		
		int w = icon.getIconWidth() == 0? 1: icon.getIconWidth();
		int h = icon.getIconHeight() == 0? 1: icon.getIconHeight();

		Image image = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon( null, image.getGraphics(), 0, 0 );
		return image;

	}


	/**
	 * Converts any icon to an image with specified width and height
	 * @param icon icon to convert
	 * @param newWidth new image width
	 * @param newHeight new image height
	 * @return resulted image
	 */
	public static final Image asImage( Icon icon, int newWidth,  int newHeight ) {

		return asImage( icon ).getScaledInstance( newWidth, newHeight, Image.SCALE_SMOOTH );
	}

	/**
	 * Scales any icon to specified width and height
	 * @param icon icon to scale
	 * @param newWidth new icon width
	 * @param newHeight new icon height
	 * @return resulting icon
	 */
	public static final Icon scale( Icon icon, int newWidth, int newHeight ) {

		return new ImageIcon( asImage( icon, newWidth, newHeight ));

	}


}
