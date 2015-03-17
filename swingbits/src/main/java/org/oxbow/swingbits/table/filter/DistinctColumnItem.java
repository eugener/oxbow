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

package org.oxbow.swingbits.table.filter;

import java.math.BigDecimal;

import org.oxbow.swingbits.util.IValueWrapper;

public class DistinctColumnItem implements Comparable<DistinctColumnItem>, IValueWrapper<Object> {

    private final Object value;
    private final int row;

    public DistinctColumnItem( Object value, int row) {
        this.value = value;
        this.row = row;
    }

    public Object getValue() {
        return this.value;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return value == null? "":  value.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DistinctColumnItem other = (DistinctColumnItem) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(DistinctColumnItem o) {

        if ( value == null ) {
            return ( o == null || o.value == null )? 0:  -1;
        }
        if ( o == null || o.value == null ) return 1;

        if ( value.getClass() == o.value.getClass() ) {
            if ( value instanceof Comparable) {
                return ((Comparable<Object>)value).compareTo(o.value);
            } else {
                return value.toString().compareTo(o.value.toString());
            }
        } else {

            if ( value instanceof Number && o.value instanceof Number) {
                BigDecimal a = new BigDecimal(value.toString());
                BigDecimal b = new BigDecimal(o.value.toString());
                return a.compareTo(b);
            }

            return value.getClass().getName().compareTo(o.getClass().getName());
        }


    }

}
