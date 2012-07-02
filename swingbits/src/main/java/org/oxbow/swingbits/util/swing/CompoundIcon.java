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

import java.awt.*;
import javax.swing.*;

/**
 * The CompoundIcon will paint two, or more, Icons as a single Icon. The Icons
 * are painted in the order in which they are added.
 * 
 * The Icons can be laid out
 * <ul>
 * <li>Horizontally
 * <li>Vertically
 * <li>Stacked
 * </ul>
 * 
 */
public class CompoundIcon implements Icon {
	public enum Layout {
		HORIZONTAL, VERTICAL, STACKED;
	}

	public final static float TOP = 0.0f;
	public final static float LEFT = 0.0f;
	public final static float CENTER = 0.5f;
	public final static float BOTTOM = 1.0f;
	public final static float RIGHT = 1.0f;

	private Icon[] icons;

	private Layout layout;

	private int gap;

	private float alignmentX = CENTER;
	private float alignmentY = CENTER;

	/**
	 * Convenience constructor for creating a CompoundIcon where the icons are
	 * laid out horizontally, the gap is 0 and the X/Y alignments will default
	 * to CENTER.
	 * 
	 * @param icons
	 *            the Icons to be painted as part of the CompoundIcon
	 */
	public CompoundIcon(Icon... icons) {
		this(Layout.HORIZONTAL, icons);
	}

	/**
	 * Convenience constructor for creating a CompoundIcon where the gap is 0
	 * and the X/Y alignments will default to CENTER.
	 * 
	 * @param layout
	 *            the layout used to lay out the icons for painting.
	 * @param icons
	 *            the Icons to be painted as part of the CompoundIcon
	 */
	public CompoundIcon(Layout layout, Icon... icons) {
		this(layout, 0, icons);
	}

	/**
	 * Convenience constructor for creating a CompoundIcon where the X/Y
	 * alignments will default to CENTER.
	 * 
	 * @param layout
	 *            the layout used to lay out the icons for painting
	 * @param gap
	 *            the gap between the icons
	 * @param icons
	 *            the Icons to be painted as part of the CompoundIcon
	 */
	public CompoundIcon(Layout layout, int gap, Icon... icons) {
		this(layout, gap, CENTER, CENTER, icons);
	}

	/**
	 * Create a CompoundIcon specifying all the properties.
	 * 
	 * @param layout
	 *            the layout used to lay out the icons for painting
	 * @param gap
	 *            the gap between the icons
	 * @param alignmentX
	 *            the X alignment of the icons. Common values are LEFT, CENTER,
	 *            RIGHT. Can be any value between 0.0 and 1.0
	 * @param alignmentY
	 *            the Y alignment of the icons. Common values are TOP, CENTER,
	 *            BOTTOM. Can be any value between 0.0 and 1.0
	 * @param icons
	 *            the Icons to be painted as part of the CompoundIcon
	 */
	public CompoundIcon(Layout layout, int gap, float alignmentX,
			float alignmentY, Icon... icons) {
		this.layout = layout;
		this.gap = gap;
		this.alignmentX = alignmentX > 1.0f ? 1.0f : alignmentX < 0.0f ? 0.0f
				: alignmentX;
		this.alignmentY = alignmentY > 1.0f ? 1.0f : alignmentY < 0.0f ? 0.0f
				: alignmentY;

		for (int i = 0; i < icons.length; i++) {
			if (icons[i] == null) {
				throw new IllegalArgumentException("Icon (" + i
						+ ") cannot be null");
			}
		}

		this.icons = icons;
	}

	/**
	 * Get the layout along which each icon is painted.
	 * 
	 * @return the layout
	 */
	public Layout getLayout() {
		return layout;
	}

	/**
	 * Get the gap between each icon
	 * 
	 * @return the gap in pixels
	 */
	public int getGap() {
		return gap;
	}

	/**
	 * Get the alignment of the icon on the x-layout
	 * 
	 * @return the alignment
	 */
	public float getAlignmentX() {
		return alignmentX;
	}

	/**
	 * Get the alignment of the icon on the y-layout
	 * 
	 * @return the alignment
	 */
	public float getAlignmentY() {
		return alignmentY;
	}

	/**
	 * Get the number of Icons contained in this CompoundIcon.
	 * 
	 * @return the total number of Icons
	 */
	public int getIconCount() {
		return icons.length;
	}

	/**
	 * Get the Icon at the specified index.
	 * 
	 * @param index
	 *            the index of the Icon to be returned
	 * @return the Icon at the specified index
	 * @exception IndexOutOfBoundsException
	 *                if the index is out of range
	 */
	public Icon getIcon(int index) {
		return icons[index];
	}

	// ///// Icon Interface /////////////////////////////

	/**
	 * Gets the width of this icon.
	 * 
	 * @return the width of the icon in pixels.
	 */
	@Override
	public int getIconWidth() {
		int width = 0;

		// Add the width of all Icons while also including the gap
		if (layout == Layout.HORIZONTAL) {
			width += (icons.length - 1) * gap;
			for (Icon icon : icons)
				width += icon.getIconWidth();
		} else { // Just find the maximum width
			for (Icon icon : icons)
				width = Math.max(width, icon.getIconWidth());
		}

		return width;
	}

	/**
	 * Gets the height of this icon.
	 * 
	 * @return the height of the icon in pixels.
	 */
	@Override
	public int getIconHeight() {
		int height = 0;

		// Add the height of all Icons while also including the gap
		if (layout == Layout.VERTICAL) {
			height += (icons.length - 1) * gap;

			for (Icon icon : icons)
				height += icon.getIconHeight();
		} else // Just find the maximum height
		{
			for (Icon icon : icons)
				height = Math.max(height, icon.getIconHeight());
		}

		return height;
	}

	/**
	 * Paint the icons of this compound icon at the specified location
	 * 
	 * @param c The component on which the icon is painted 
	 *            
	 * @param g the graphics context
	 *            
	 * @param x the X coordinate of the icon's top-left corner
	 *            
	 * @param y the Y coordinate of the icon's top-left corner
	 *            
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (layout == Layout.HORIZONTAL) {
			int height = getIconHeight();

			for (Icon icon : icons) {
				int iconY = getOffset(height, icon.getIconHeight(), alignmentY);
				icon.paintIcon(c, g, x, y + iconY);
				x += icon.getIconWidth() + gap;
			}
		} else if (layout == Layout.VERTICAL) {
			int width = getIconWidth();

			for (Icon icon : icons) {
				int iconX = getOffset(width, icon.getIconWidth(), alignmentX);
				icon.paintIcon(c, g, x + iconX, y);
				y += icon.getIconHeight() + gap;
			}
		} else  {// must be Z_layout
		
			int width = getIconWidth();
			int height = getIconHeight();

			for (Icon icon : icons) {
				int iconX = getOffset(width, icon.getIconWidth(), alignmentX);
				int iconY = getOffset(height, icon.getIconHeight(), alignmentY);
				icon.paintIcon(c, g, x + iconX, y + iconY);
			}
		}
	}

	/*
	 * When the icon value is smaller than the maximum value of all icons the
	 * icon needs to be aligned appropriately. Calculate the offset to be used
	 * when painting the icon to achieve the proper alignment.
	 */
	private int getOffset(int maxValue, int iconValue, float alignment) {
		float offset = (maxValue - iconValue) * alignment;
		return Math.round(offset);
	}
}
