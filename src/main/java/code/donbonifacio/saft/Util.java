package code.donbonifacio.saft;

import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Handy utilities
 */
public final class Util {

    private Util() {
    }

    /**
     * This utility composes two fns. This is a helper because JAva
     * doesn't allow things like Header::getCompanyAddress.andThen(Address::getCity)
     */
    public static <A, B, C> Function<A, C> compose(Function<A, B> f1, Function<B, C> f2) {
        return checkNotNull(f1)
                .andThen(checkNotNull(f2));
    }

    /**
     * This utility composes three fns. This is a helper because JAva
     * doesn't allow things like Header::getCompanyAddress.andThen(Address::getCity)
     */
    public static <A, B, C, D> Function<A, D> compose(Function<A, B> f1, Function<B, C> f2, Function<C, D> f3) {
        return checkNotNull(f1)
                .andThen(checkNotNull(f2))
                .andThen(checkNotNull(f3));
    }

}
