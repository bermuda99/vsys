package actor;

import fpinjava.Result;

import java.util.concurrent.Semaphore;
/*
Was ist ein Semaphor?
ein semaphor ist ein synchronisationsmechanismus, der den zugriff von threads auf gemeinsame ressourcen steuert, um konflikte zu vermeiden.

warum verwendet das ping-pong-beispiel ein semaphor?
das ping-pong-beispiel verwendet einen semaphor, um sicherzustellen, dass die spieler abwechselnd agieren und keine race conditions auftreten.

was passiert wenn das ping-pong-beispiel keinen semaphor verwendet?
ohne semaphor könnte das programm chaotisch ablaufen, da die spieler möglicherweise nicht abwechselnd spielen, was zu unerwartetem verhalten führt.

warum terminiert das programm nicht, wenn man die userthreadfactory ohne semaphor verwendet?
das programm terminiert nicht, weil user-threads das programm am laufen halten, solange sie aktiv sind und endlos nachrichten senden.
 */
class Player extends AbstractActor<Integer> {
    private final String sound;
    private final Actor<Integer> referee;

    public Player(String id, String sound, Actor<Integer> referee) {
        super(id, Actor.Type.SERIAL);
        this.referee = referee;
        this.sound = sound;
    }

    @Override
    public void onReceive(Integer message, Result<Actor<Integer>> sender) {
        System.out.println(sound + "-" + message);
        if (message >= 10) {
            referee.tell(message, sender);
        } else {
            sender.forEachOrFail(actor -> actor.tell(message + 1, self()))
                    .forEach(ignore -> referee.tell(message, sender));
        }
    }



    private static final Semaphore semaphore = new Semaphore(1);

    public static void main(String... args) throws InterruptedException {
        Actor<Integer> referee =
                new AbstractActor<Integer>("Referee", Actor.Type.SERIAL) {
                    @Override
                    public ActorContext<Integer> getContext() {
                        return null;
                    }

                    @Override
                    public void tell(Integer message, Result<Actor<Integer>> sender) {

                    }
                    @Override
                    public void onReceive(Integer message, Result<Actor<Integer>> sender) {
                        System.out.println("Game ended after " + message + " shots");
                        //semaphore.release();
                    }
                };
        Actor<Integer> player1 = new Player("Player1", "Ping", referee);
        Actor<Integer> player2 = new Player("Player2", "Pong", referee);
        //semaphore.acquire();
        player1.tell(1, Result.success(player2));
        //Thread.sleep(2000);
        //semaphore.acquire();
        //player1.shutdown();
        //player2.shutdown();
        //referee.shutdown();
        }
    }

