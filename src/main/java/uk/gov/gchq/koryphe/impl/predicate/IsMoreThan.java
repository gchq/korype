/*
 * Copyright 2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.gov.gchq.koryphe.impl.predicate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.gov.gchq.koryphe.predicate.KoryphePredicate;

/**
 * An <code>IsMoreThan</code> is a {@link java.util.function.Predicate} that checks that the input
 * {@link Comparable} is more than a control value. There is also an orEqualTo flag that can be set to allow
 * the input value to be more than or equal to the control value.
 */
public class IsMoreThan extends KoryphePredicate<Comparable> {
    private Comparable controlValue;
    private boolean orEqualTo;

    public IsMoreThan() {
        // Required for serialisation
    }

    public IsMoreThan(final Comparable<?> controlValue) {
        this(controlValue, false);
    }

    public IsMoreThan(final Comparable controlValue, final boolean orEqualTo) {
        this.controlValue = controlValue;
        this.orEqualTo = orEqualTo;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    @JsonProperty("value")
    public Comparable getControlValue() {
        return controlValue;
    }

    public void setControlValue(final Comparable controlValue) {
        this.controlValue = controlValue;
    }

    public boolean getOrEqualTo() {
        return orEqualTo;
    }

    public void setOrEqualTo(final boolean orEqualTo) {
        this.orEqualTo = orEqualTo;
    }

    @Override
    public boolean test(final Comparable input) {
        if (null == input
                || !controlValue.getClass().isAssignableFrom(input.getClass())) {
            return false;
        }

        final int compareVal = controlValue.compareTo(input);
        if (orEqualTo) {
            return compareVal <= 0;
        }

        return compareVal < 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (null == o || !getClass().equals(o.getClass())) {
            return false;
        }

        final IsMoreThan that = (IsMoreThan) o;
        return new EqualsBuilder()
                .append(orEqualTo, that.orEqualTo)
                .append(controlValue, that.controlValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(controlValue)
                .append(orEqualTo)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("controlValue", controlValue)
                .append("orEqualTo", orEqualTo)
                .toString();
    }
}