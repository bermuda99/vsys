package inout;
import fpinjava.Result;
import stream.Stream;
import tuple.Tuple;

public interface Input {

    Result<Tuple<String, Input>> readLine();

    default Result<Tuple<Integer, Input>> readInt(){
        // Ihre Aufgabe
        return readLine().flatMap(t->readIntMaybe(t.fst).map(i->new Tuple<>(i,t.snd)));
    }
    //Die Methode readIntMaybe wandelt ein String in ein Integer um falls möglich
    static Result<Integer> readIntMaybe(String s){
        return Result.of(()->Integer.parseInt(s));
    }

    default Stream<String> readLines() {
        return Stream.<String,Input>unfold(this, Input::readLine);
    }

    default Stream<Integer> readInts() {
        return Stream.<Integer,Input>unfold(this, Input::readInt);
    }

    void shutdownInput();

}
