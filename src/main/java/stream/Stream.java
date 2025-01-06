package stream;

import fpinjava.*;
import tuple.Tuple;

/*
Was war eigentlich nochmal Endrekursion?

Bei der Endrukursion wird der rekursive Aufruf als letzte rechnung(operation) ausgefuehrt
 */

public abstract class Stream<A> {
    private static Stream EMPTY = new Empty();
    public abstract A head();
    public abstract Stream<A> tail();
    public abstract Boolean isEmpty();
    public abstract void forEach(Effect<A> e);
    private Stream() {}

    public Result<A> headOption() {
        return isEmpty()
                ? Result.empty()
                : Result.success(head());
    }

    private static class Empty<A> extends Stream<A> {
        @Override
        public Stream<A> tail() {
            throw new IllegalStateException("tail called on empty");
        }
        @Override
        public A head() {
            throw new IllegalStateException("head called on empty");
        }
        @Override
        public Boolean isEmpty() {
            return true;
        }

        @Override
        public void forEach(Effect<A> e) {

        }
    }
    private static class Cons<A> extends Stream<A> {
        private final Supplier<A> head;
        private A h;
        private final Supplier<Stream<A>> tail;
        private Stream<A> t;
        private Cons(Supplier<A> h, Supplier<Stream<A>> t) {
            head = h;
            tail = t;
        }
        @Override
        public A head() {
            if (h == null) {
                h = head.get();
            }
            return h;
        }
        @Override
        public Stream<A> tail() {
            if (t == null) {
                t = tail.get();
            }
            return t;
        }
        @Override
        public Boolean isEmpty() {
            return false;
        }

        public void forEach(Effect<A> ef) {
            if (!isEmpty()) {
                ef.apply(head());
                tail().forEach(ef);
            }
        }

    }



    /*
    Foreach rekursiv lösen, geht einfacher.
     */
    public static <A> TailCall<Stream<A>> forEach(Stream<A> stream, Effect<A> ef) {
        return stream.isEmpty()

                ? TailCall.ret(stream)
                : TailCall.sus(() -> {
            ef.apply(stream.head());
            return forEach(stream.tail(), ef);
        });
    }

    public static <A, S> Stream<A> unfold(S z,
                                          Function<S, Result<Tuple<A, S>>> f) {
        return f.apply(z).map(x -> cons(() -> x.fst,
                () -> unfold(x.snd, f))).getOrElse(empty());
    }
    static <A> Stream<A> cons(Supplier<A> hd, Supplier<Stream<A>> tl) {
        return new Cons<>(hd, tl);
    }
    static <A> Stream<A> cons(Supplier<A> hd, Stream<A> tl) {
        return new Cons<>(hd, () -> tl);
    }
    @SuppressWarnings("unchecked")
    public static <A> Stream<A> empty() {
        return EMPTY;
    }
    public static Stream<Integer> from(int i) {
        return cons(() -> i, () -> from(i + 1));
    }
}
