package uk.gov.gchq.koryphe.adapted;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import uk.gov.gchq.koryphe.Since;
import uk.gov.gchq.koryphe.Summary;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@code StatelessOutputAdapter} is a {@link BiFunction} which ignores the first argument (the state)
 * and applies a provided function to the output. By default the output is returned.
 * @param <T> The type of the unused state
 * @param <U> The type of the input
 * @param <R> The type of the output
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
@Since("1.11.0")
@Summary("Adapts an output without considering the state")
public class StatelessOutputAdapter<T, U, R> implements BiFunction<T, U, R> {
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
    private Function<U, R> adapter;

    public StatelessOutputAdapter() {
        // required for Json Serialisation
    }

    public StatelessOutputAdapter(Function<U, R> adapter) {
        this.adapter = adapter;
    }

    @Override
    public R apply(final T ignoredState, final U output) {
        if (adapter == null) {
            return (R) output;
        }
        return adapter.apply(output);
    }

    public Function<U, R> getAdapter() {
        return adapter;
    }

    public void setAdapter(final Function<U, R> adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final StatelessOutputAdapter that = (StatelessOutputAdapter) o;

        return new EqualsBuilder()
                .append(adapter, that.adapter)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getClass())
                .append(adapter)
                .toHashCode();
    }
}
