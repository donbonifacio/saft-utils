package code.donbonifacio.saft;

import java.util.function.Function;

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
    static <A, B, C> Function<A, C> compose(Function<A, B> f1, Function<B, C> f2) {
        return f1.andThen(f2);
    }

}
