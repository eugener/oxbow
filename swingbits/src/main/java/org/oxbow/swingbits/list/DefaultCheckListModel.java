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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;

import org.oxbow.swingbits.util.DefaultObjectToStringTranslator;
import org.oxbow.swingbits.util.IObjectToStringTranslator;
import org.oxbow.swingbits.util.IValueWrapper;

/**
 * Default model for check list. It is based on the list of items
 * Implementation of checks is based on HashSet of checked items
 *
 * @author Eugene Ryzhikov
 *
 * @param <T> list element type
 */
public class DefaultCheckListModel<T> extends AbstractListModel implements ICheckListModel<T> {

    private static final long serialVersionUID = 1L;

    public static IObjectToStringTranslator DEFAULT_TRANSLATOR = new DefaultObjectToStringTranslator();

    private Set<T> checks = new HashSet<T>();
    private final List<T> dataList = new ArrayList<T>();
    private final Set<T> dataSet = new HashSet<T>();
    private List<T> filteredDataList = null;
    private Set<T> filteredDataSet = null;

    public DefaultCheckListModel( Collection<? extends T> data ) {

        if ( data == null ) return;
        for (T object : data) {
            dataList.add(object);
            dataSet.add(object);
        }
    }

    public DefaultCheckListModel( T... data ) {
        this( Arrays.asList( data ));
    }

    /* (non-Javadoc)
     * @see org.oxbow.swingbits.list.ICheckListModel#getSize()
     */
    @Override
    public int getSize() {
        return dataList().size();
    }

    private List<T> dataList() {
        return filteredDataList == null ? dataList : filteredDataList;
    }

    private Set<T> dataSet() {
        return filteredDataSet == null ? dataSet : filteredDataSet;
    }

    /* (non-Javadoc)
     * @see org.oxbow.swingbits.list.ICheckListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index) {
        return dataList().get(index);
    }

    /* (non-Javadoc)
     * @see org.oxbow.swingbits.list.ICheckListModel#isChecked(int)
     */
    @Override
    public boolean isCheckedIndex( int index ) {
        return checks.contains(dataList().get(index));
    }

    /* (non-Javadoc)
     * @see org.oxbow.swingbits.list.ICheckListModel#setChecked(int, boolean)
     */
    @Override
    public void setCheckedIndex( int index, boolean value ) {
        T o = dataList().get(index);
        if ( value ) checks.add(o); else checks.remove(o);
        fireContentsChanged(this, index, index);
    }

    /* (non-Javadoc)
     * @see org.oxbow.swingbits.list.ICheckListModel#getChecked()
     */
    @Override
    public Collection<T> getCheckedItems() {
        List<T> items = new ArrayList<T>(checks);
        items.retainAll(dataSet());
        return Collections.unmodifiableList( items );
    }

    /* (non-Javadoc)
     * @see org.oxbow.swingbits.list.ICheckListModel#setChecked(java.util.Collection)
     */
    @Override
    public void setCheckedItems( Collection<T> items ) {
        Set<T> correctedItems = new HashSet<T>(items);
        correctedItems.retainAll(dataSet());
        checks = correctedItems;
        fireContentsChanged(this, 0, checks.size()-1);
    }

    public void filter( String pattern, IObjectToStringTranslator translator, IListFilter listFilter ) {

        if ( pattern == null || pattern.trim().length() == 0 ) {
            filteredDataList = null;
            filteredDataSet = null;
        } else {

            IListFilter filter = listFilter == null? CheckListFilterType.CONTAINS: listFilter;

            IObjectToStringTranslator t = translator == null? DEFAULT_TRANSLATOR: translator;
            String p = pattern.toLowerCase();

            List<T> fDataList = new ArrayList<T>();
            Set<T> fDataSet = new HashSet<T>();

            Object value;
            for (T o : dataList) {
                //if ( t.translate(o).startsWith(f)) {
                value = o instanceof IValueWrapper? ((IValueWrapper<?>)o).getValue(): o;
                if ( filter.include(t.translate(value), p)) {
                    fDataList.add(o);
                    fDataSet.add(o);
                }
            }
            filteredDataList = fDataList;
            filteredDataSet = fDataSet;
        }

        fireContentsChanged(this, 0, dataList.size() - 1);

    }


}
