package list;


import fpinjava.Function;
import fpinjava.Result;

import java.util.function.Predicate;

public abstract class List<A> {

    public abstract A head();

    public abstract List<A> tail();

    public abstract boolean isEmpty();

    public abstract List<A> setHead(A h);

    public abstract boolean isEqualTo(List<A> xs);

    public abstract Result<A> headOption();

    public abstract Result<A> find(Function<A, Boolean> f);

    public List<A> cons(A a) {
        return new Cons<>(a, this);
    }

    public abstract int length();

    public abstract boolean elem(A x);

    public abstract boolean any(Function<A, Boolean> p);

    public abstract boolean all(Function<A, Boolean> p);

    public abstract <B> List<B> map(Function<A, B> f);

    public abstract List<A> filter(Function<A, Boolean> f);

    public abstract A finde(Function<A, Boolean> f);

    public abstract List<A> init();

    public abstract A last();

    public abstract List<A> take(int n);

    public abstract List<A> drop(int n);

    public abstract List<A> takeWhile(Function<A, Boolean> p);

    public abstract List<A> dropWhile(Function<A, Boolean> p);

    public abstract List<A> delete(A x);

    public abstract List<A> reverse();

    public abstract <B> B foldr(Function<A, Function<B, B>> f, B s);

    public abstract <B> B foldl(Function<B, Function<A, B>> f, B s);

    public abstract <B> List<B> mapR(Function<A, B> f);

    public abstract List<A> filterR(Predicate<A> p);

    public abstract boolean allR(Predicate<A> p);

    public abstract boolean anyR(Predicate<A> p);

    public abstract int lengthR();

    public abstract boolean elemR(A a);

    public abstract List<A> takeWhileR(Predicate<A> p);

    public abstract String toStringR();

    public abstract boolean allL(Predicate<A> p);

    public abstract int lengthL();

    public abstract boolean anyL(Predicate<A> p);

    public abstract boolean elemL(A a);

    public abstract A lastL();

    public abstract List<A> reverseL();

    @SuppressWarnings("rawtypes")
    public static final List NIL = new Nil();

    private List() {
    }

    private static class Nil<A> extends List<A> {

        private Nil() {
        }

        public A head() {
            throw new IllegalStateException("head called en empty list");
        }

        public List<A> tail() {
            throw new IllegalStateException("tail called en empty list");
        }

        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<A> setHead(A h) {
            return new Cons<>(h, new Nil<>());
        }

        @Override
        public boolean isEqualTo(List<A> xs) {
            return xs.isEmpty();
        }

        public Result<A> headOption() {
            return Result.empty();
        }

        public Result<A> find(Function<A, Boolean> f) {
            return Result.empty();
        }

        public String toString() {
            return "[]";
        }

        public int length() {
            return 0;
        }

        public boolean elem(A x) {
            return false;
        }

        public boolean any(Function<A, Boolean> p) {
            return false;
        }

        public boolean all(Function<A, Boolean> p) {
            return true;
        }

        public <B> List<B> map(Function<A, B> f) {
            return list();
        }


        public List<A> filter(Function<A, Boolean> f) {
            return this;
        }

        public A finde(Function<A, Boolean> f) {
            return null;
        }

        public List<A> init() {
            throw new UnsupportedOperationException("init of an empty list");
        }

        public A last() {
            throw new UnsupportedOperationException("last of an empty list");
        }

        @Override
        public List<A> take(int n) {
            return this;
        }

        @Override
        public List<A> drop(int n) {
            return this;
        }

        @Override
        public List<A> takeWhile(Function<A, Boolean> p) {
            return this;
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> p) {
            return this;
        }

        public List<A> delete(A x) {
            return this;
        }

        public List<A> reverse() {
            return this;
        }

        public <B> B foldr(Function<A, Function<B, B>> f, B s) {
            return s;
        }

        public <B> List<B> mapR(Function<A, B> f) {
            return list();
        }

        public List<A> filterR(Predicate<A> p) {
            return list();
        }

        public boolean allR(Predicate<A> p) {
            return true;
        }

        public boolean anyR(Predicate<A> p) {
            return false;
        }

        public int lengthR() {
            return 0;
        }

        public boolean elemR(A a) {
            return false;
        }

        public List<A> takeWhileR(Predicate<A> p) {
            return list();
        }

        public String toStringR() {
            return "[]";
        }

        public <B> B foldl(Function<B, Function<A, B>> f, B s) {
            return s;
        }

        public boolean allL(Predicate<A> p) {
            return true;
        }

        public int lengthL() {
            return 0;
        }

        public boolean anyL(Predicate<A> p) {
            return false;
        }

        public boolean elemL(A a) {
            return false;
        }

        public A lastL() {
            throw new UnsupportedOperationException("lastL() called on empty list");
        }

        public List<A> reverseL() {
            return this;
        }
    }

    private static class Cons<A> extends List<A> {

        private final A head;
        private final List<A> tail;

        private Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public List<A> tail() {
            return tail;
        }

        public boolean isEmpty() {
            return false;
        }

        public List<A> setHead(A h) {
            if (isEmpty()) {
                return list(h);
            } else {
                return new Cons<>(h, tail());
            }
        }

        public Result<A> headOption() {
            return Result.success(head);
        }

        public Result<A> find(Function<A, Boolean> f) {
            if (f.apply(head)) {
                return Result.success(head);
            } else {
                return tail.find(f);
            }
        }

        @Override
        public boolean isEqualTo(List<A> xs) {
            if (xs.isEmpty()) {
                return false;
            } else {
                return head.equals(xs.head()) && tail.isEqualTo(xs.tail());
            }
        }

        public String toString() {
            return "[" + toStringRecursive(this) + "]";
        }

        private String toStringRecursive(List<A> list) {
            if (list.isEmpty()) {
                return "";
            } else if (list.tail().isEmpty()) {
                return list.head().toString();
            } else {
                return list.head().toString() + ", " + toStringRecursive(list.tail());
            }
        }

        public int length() {
            return 1 + tail.length();
        }

        /*
        public boolean elem(A x) {
            return head.equals(x) || tail.elem(x);
        }
         */
        public boolean elem(A x) {
            return tail.anyL(t -> t.equals(x)) || head.equals(x);
        }

        public boolean any(Function<A, Boolean> p) {
            return p.apply(head) || tail.any(p);
        }

        /*
        public boolean all(Function<A, Boolean> p) {
            return p.apply(head) && tail.all(p);
        }
         */
        public boolean all(Function<A, Boolean> p) {
            return !tail.anyL(x -> !p.apply(x)) && p.apply(head);
        }

        public <B> List<B> map(Function<A, B> f) {
            return new Cons<>(f.apply(head), tail.map(f));
        }

        public List<A> filter(Function<A, Boolean> f) {
            if (f.apply(head)) {
                return new Cons<>(head, tail.filter(f));
            } else {
                return tail.filter(f);
            }
        }

        public A finde(Function<A, Boolean> f) {
            if (f.apply(head)) {
                return head;
            } else {
                return tail.finde(f);
            }
        }

        public List<A> init() {
            if (tail.isEmpty()) {
                return List.list();
            } else {
                return new Cons<>(head, tail.init());
            }
        }

        public A last() {
            if (tail.isEmpty()) {
                return head;
            } else {
                return tail.last();
            }
        }

        public List<A> take(int n) {
            if (n <= 0) {
                return new Nil<>();
            } else {
                return new Cons<>(head, tail.take(n - 1));
            }
        }

        public List<A> drop(int n) {
            if (n == 0) {
                return this;
            } else {
                return tail.drop(n - 1);
            }
        }

        public List<A> takeWhile(Function<A, Boolean> p) {
            if (p.apply(head)) {
                return new Cons<>(head, tail.takeWhile(p));
            } else {
                return new Nil<>();
            }
        }

        public List<A> dropWhile(Function<A, Boolean> p) {
            if (p.apply(head)) {
                return tail.dropWhile(p);
            } else {
                return this;
            }
        }

        public List<A> delete(A x) {
            if (head.equals(x)) {
                return tail;
            } else {
                return new Cons<>(head, tail.delete(x));
            }
        }

        public List<A> reverse() {
            return append(tail().reverse(), list(head()));
        }

        public <B> B foldr(Function<A, Function<B, B>> f, B s) {
            return f.apply(head()).apply(tail().foldr(f, s));
        }

        public <B> List<B> mapR(Function<A, B> f) {
            return foldr(x -> xs -> new Cons<>(f.apply(x), xs), list());
        }

        public List<A> filterR(Predicate<A> p) {
            return foldr(x -> xs -> p.test(x) ? new Cons<>(x, xs) : xs, list());
        }

        public boolean allR(Predicate<A> p) {
            return foldr(x -> acc -> p.test(x) && acc, true);
        }

        public boolean anyR(Predicate<A> p) {
            return foldr(x -> acc -> p.test(x) || acc, false);
        }

        public int lengthR() {
            return foldr(x -> n -> n + 1, 0);
        }

        public boolean elemR(A a) {
            return foldr(x -> b -> b || x.equals(a), false);
        }

        public List<A> takeWhileR(Predicate<A> p) {
            return foldr(x -> xs -> p.test(x) ? new Cons<>(x, xs) : list(), list());
        }

        public String toStringR() {
            return foldr(x -> xs -> x.toString() + "," + xs, "[]");
        }

        public <B> B foldl(Function<B, Function<A, B>> f, B s) {
            return tail.foldl(f, f.apply(s).apply(head));
        }

        public int lengthL() {
            return foldl(x -> y -> x + 1, 0);
        }

        public boolean allL(Predicate<A> p) {
            return foldl(b -> a -> p.test(a) && b, true);
        }

        public List<A> reverseL() {
            return foldl(x -> y -> new Cons<>(y, x), list());
        }

        public boolean elemL(A a) {
            return foldl(b -> x -> x.equals(a) || b, false);
        }

        public A lastL() {
            return foldl(x -> y -> y, head());
        }

        public boolean anyL(Predicate<A> p) {
            return foldl(x -> y -> p.test(y) || x, false);
        }
    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> list() {
        return NIL;
    }

    @SafeVarargs
    public static <A> List<A> list(A... a) {
        List<A> n = list();
        for (int i = a.length - 1; i >= 0; i--) {
            n = new Cons<>(a[i], n);
        }
        return n;
    }

    public static Integer sum(List<Integer> xs) {
        return xs.isEmpty() ? 0
                : xs.head() + sum(xs.tail());
    }

    public static Double prod(List<Double> xs) {
        return xs.isEmpty() ? 1
                : xs.head() * prod(xs.tail());
    }

    public static <A> List<A> append(List<A> xs, List<A> ys) {
        return xs.isEmpty() ? ys
                : new Cons<>(xs.head(), append(xs.tail(), ys));
    }

    public static <A> List<A> concat(List<List<A>> xs) {
        return xs.isEmpty() ? list()
                : append(xs.head(), concat(xs.tail()));
    }

    public static boolean and(List<Boolean> xs) {
        return xs.isEmpty() ? true
                : xs.head() && and(xs.tail());
    }

    public static boolean or(List<Boolean> xs) {
        return xs.isEmpty() ? false
                : xs.head() || or(xs.tail());
    }

    public static <A extends Comparable<A>> A minimum(List<A> list) {
        if (list.isEmpty()) {
            throw new IllegalStateException("minimum called on empty list");
        } else if (list.tail().isEmpty()) {
            return list.head();
        } else {
            A minTail = minimum(list.tail());
            return list.head().compareTo(minTail) < 0 ? list.head() : minTail;
        }
    }

    public static <A extends Comparable<A>> A maximum(List<A> list) {
        if (list.isEmpty()) {
            throw new IllegalStateException("maximum called on empty list");
        } else if (list.tail().isEmpty()) {
            return list.head();
        } else {
            A maxTail = maximum(list.tail());
            return list.head().compareTo(maxTail) > 0 ? list.head() : maxTail;
        }
    }

    public static <A, B> B foldr(Function<A, Function<B, B>> f, B s, List<A> xs) {
        if (xs.isEmpty()) {
            return s;
        } else {
            return f.apply(xs.head()).apply(foldr(f, s, xs.tail()));
        }
    }

    public static Integer sumR(List<Integer> xs) {
        return foldr(x -> y -> x + y, 0, xs);
    }

    public static Double prodR(List<Double> xs) {
        return foldr(x -> y -> x * y, 1.0, xs);
    }

    public static <A> List<A> appendR(List<A> xs, List<A> ys) {
        return foldr(x -> l -> new Cons<>(x, l), ys, xs);
    }

    static <A> List <A> concatR(List<List<A>> list) {
        if(list.isEmpty()) {
            return list();
        } else {
            return foldr(x -> y -> appendR(x, y), list.head(), list.tail());
        }
    }

    public static boolean andR(List<Boolean> xs) {
        return xs.foldr(x -> y -> x && y, true);
    }

    public static boolean orR(List<Boolean> xs) {
        return xs.foldr(x -> y -> x || y, false);
    }

    public static <A, B> B foldl(Function<B, Function<A, B>> f, B s, List<A> xs) {
        return
                xs.isEmpty() ? s
                        : foldl(f, f.apply(s).apply(xs.head()), xs.tail());
    }

    public static Integer sumL(List<Integer> xs) {
        return foldl(x -> y -> x + y, 0, xs);
    }

    public static Double prodL(List<Double> xs) {
        return foldl(x -> y -> x * y, 1.0, xs);
    }

    public static boolean orL(List<Boolean> xs) {
        return foldl(x -> y -> x || y, false, xs);
    }

    public static boolean andL(List<Boolean> xs) {
        return foldl(x -> y -> x && y, true, xs);
    }

    public <B> List<B> flatMap(Function<A, List<B>> f) {
        return foldr(h -> t -> append(f.apply(h), t), list());
    }

    public static List<Integer> range(int start, int end) {
        return start > end
                ? list()
                : new Cons<>(start, range(start + 1, end));
    }

    public static List<String> words(String input) {
        if (input.trim().isEmpty()) {
            return list();
        } else {
            final String[] splits = input.trim().split("\\s+");
            final String h = splits[0];
            final String t = input.substring(h.length()).trim();
            return new Cons<>(h, words(t));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof List)) {
            return false;
        } else {
            List<?> other = (List<?>) obj;
            return isEqualTo((List<A>) other);
        }
    }

    static Integer Euler1() {
        return range(1, 999).filter(x -> x % 3 == 0 || x % 5 == 0).foldr(x -> y -> x + y, 0);
    }
}
