import adapters.GooseAdapter;
import adapters.PigeonAdapter;
import animals.*;
import composites.Flock;
import composites.LeaderFlock;
import decorated.QuackCounter;
import decorated.QuackEcho;
import factory.*;

public class DuckSimulator {
    public static void main(String[] args) {
        DuckSimulator duckSimulator = new DuckSimulator();

        duckSimulator.simulate();
        System.out.println("-----------");
        duckSimulator.simulateQuackCounter();
        System.out.println("-----------");
        duckSimulator.simulate(new DuckFactory(), new CountAndEchoDuckFactory());
        System.out.println("-----------");
        duckSimulator.simulate(new DuckFactory());
    }

    void simulate() {
        Quackable mallardDuck = new MallardDuck();
        Quackable redheadDuck = new RedheadDuck();
        Quackable rubberDuck = new RubberDuck();
        Quackable duckCall = new DuckCall();
        Quackable gooseDuck = new GooseAdapter(new Goose());

        System.out.println("\nDuck Simulator");

        simulate(mallardDuck);
        simulate(redheadDuck);
        simulate(duckCall);
        simulate(rubberDuck);
        simulate(gooseDuck);
    }

    private void simulateQuackCounter() {
        Quackable mallardDuck = new QuackCounter(new MallardDuck());
        Quackable redHeadDuck = new QuackCounter(new RedheadDuck());
        Quackable duckCall = new QuackCounter(new DuckCall());
        Quackable rubberDuck = new QuackCounter(new RubberDuck());
        Quackable gooseAdapter = new GooseAdapter(new Goose());
        Quackable pigeonAdapter = new PigeonAdapter(new Pigeon());

        Quackable mallardDecorator = new QuackCounter(new QuackEcho(new MallardDuck()));

        System.out.println("\nDuck Simulator: With Decorator");

        simulate(mallardDuck);
        simulate(redHeadDuck);
        simulate(duckCall);
        simulate(rubberDuck);
        simulate(gooseAdapter);
        simulate(pigeonAdapter);
        simulate(mallardDecorator);

        System.out.println("The ducks quacked " + QuackCounter.getQuacks() + " times.");
    }

    private void simulate(AbstractDuckFactory duckFactory, AbstractDuckFactory countAndEchoDuckFactory) {
        AbstractDuckFactory countingDuckFactory = new CountingDuckFactory();

        Quackable mallardDuck = duckFactory.createMallardDuck();
        Quackable redHeadDuck = countingDuckFactory.createRedheadDuck();
        Quackable duckCall = countAndEchoDuckFactory.createDuckCall();
        Quackable rubberDuck = countAndEchoDuckFactory.createRubberDuck();

        simulate(mallardDuck);
        simulate(redHeadDuck);
        simulate(duckCall);
        simulate(rubberDuck);

        System.out.println("The ducks quacked " + QuackCounter.getQuacks() + " times.");
    }

    void simulate(AbstractDuckFactory duckFactory) {
        AbstractDuckFactory countingDuckFactory = new CountingDuckFactory();

        Quackable mallardDuck = duckFactory.createMallardDuck();
        Quackable redHeadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = countingDuckFactory.createDuckCall();
        Quackable rubberDuck = countingDuckFactory.createRubberDuck();

        LeaderFlock flock = new LeaderFlock();
        flock.add(mallardDuck);
        flock.add(redHeadDuck);
        flock.add(duckCall);
        flock.add(rubberDuck);

        flock.quack();
    }

    void simulate(Quackable duck) {
        duck.quack();
    }
}
