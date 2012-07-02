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

import java.awt.AWTException;
import java.awt.AWTPermission;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Screenshot {

	private final BufferedImage image;


	/**
     * Creates an image containing pixels read from the screen.  This image does
     * not include the mouse cursor.
	 * @param screenRect Rect to capture in screen coordinates
	 * @return captured screenshot
     * @throws 	IllegalArgumentException if <code>screenRect</code> width and height are not greater than zero
     * @throws 	SecurityException if <code>readDisplayPixels</code> permission is not granted
	 * @throws AWTException
     * @see     SecurityManager#checkPermission
     * @see 	AWTPermission
	 */
	public static final Screenshot capture( Rectangle screenRect ) throws AWTException {
		return new Screenshot( getRobot().createScreenCapture( screenRect ));
	}

	public static final Screenshot capture( int width, int height ) throws AWTException {
		return capture( new Rectangle( width, height ));
	}

	
	private static Robot robot;

	private synchronized static Robot getRobot() throws AWTException {

		if ( robot == null ) robot = new Robot();
		return robot;

	}

	private Screenshot( BufferedImage image ) {
		this.image = image;
	}

	/**
	 * Returns the image screenshot is based on
	 */
	public Image getImage() {
		return image;
	}


	/**
     * Writes an image using an arbitrary <code>ImageWriter</code>
     * that supports the given format to a <code>File</code>.  If
     * there is already a <code>File</code> present, its contents are
     * discarded.
	 * @param formatName a <code>String</code> containg the informal name of the format.
	 * @param output a <code>File</code> to be written to
	 *
	 * if an error occurs during writing.
	 *
     * @exception IllegalArgumentException if any parameter is <code>null</code>.
	 * @throws IOException if an error occurs during writing.
	 */
	public boolean store( String formatName, File output ) throws IOException {
		return ImageIO.write( image, formatName, output );
	}
	
	/**
	 * Retrieves screen information as a list of display modes, 
	 * which includes screen dimensions, bit depth and refresh rate
	 * @return
	 */
	public static final List<DisplayMode> getDisplayInfo() {
		
		List<DisplayMode> result = new ArrayList<DisplayMode>();
		for (GraphicsDevice gs:  GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
			result.add( gs.getDisplayMode());
		}
		return result;
		
	}


//	public static void main(String[] args) {
//
//		try {
//			System.out.println("taking screenshot...");
//			
//			DisplayMode dm = getDisplayInfo().get(0);
//			Screenshot.capture( dm.getWidth(), dm.getHeight()).store( "jpg", new File("C:/home/imageTest.jpg") );
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//
//	}


}
