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

package org.oxbow.swingbits.list;

import java.util.Collection;

import javax.swing.ListModel;

import org.oxbow.swingbits.util.IObjectToStringTranslator;

public interface ICheckListModel<T> extends ListModel{

    /**
     * Returns the check state of the element at specified position
     * @param index element index
     * @return true if element at specified position is checked
     * @throws IndexOutOfBoundsException if index is out of range
     */
    boolean isCheckedIndex(int index);

    /**
     * Sets the check state of the element at specified position
     * @param index element index
     * @param value 
     * @throws IndexOutOfBoundsException if index is out of range
     */
    void setCheckedIndex(int index, boolean value);

    /**
     * Returns a collections of checked items
     * @return
     */
    Collection<T> getCheckedItems();

    /**
     * Sets checked items
     * @param items
     */
    void setCheckedItems(Collection<T> items);
    
    /**
     * Allows filtered view. Setting empty or null filter will clear filter and show all items
     * @param filter filter string
     * @param translator object to string translator to aid the search
     */
    void filter( String filter, IObjectToStringTranslator translator, CheckListFilterType filterType );

}