package util;

import java.util.ArrayList;
import java.security.SecureRandom;

public class RandomName {
    static ArrayList<String> firstNames = new ArrayList<>();
    static ArrayList<String> lastNames = new ArrayList<>();

    public static String generateRandomFirstName() {
        SecureRandom r = new SecureRandom();

        if (firstNames.isEmpty()) {
            initFirstNames();
        }

        return firstNames.get(r.nextInt(0, firstNames.size()));
    }

    public static String generateRandomLastName() {
        SecureRandom r = new SecureRandom();

        if (lastNames.isEmpty()) {
            initLastNames();
        }

        return lastNames.get(r.nextInt(0, lastNames.size()));
    }

    public static void initFirstNames() {
        firstNames.add("Jimmy");
        firstNames.add("Billy");
        firstNames.add("Hunter");
        firstNames.add("Thomas");
        firstNames.add("Johnny");
        firstNames.add("Frill");
        firstNames.add("Michael");
        firstNames.add("John");
        firstNames.add("Killian");
        firstNames.add("Dedrick");
        firstNames.add("Vince");
        firstNames.add("Mark");
        firstNames.add("Lamar");
        firstNames.add("Jackson");
        firstNames.add("Pope");
        firstNames.add("Fridge");
        firstNames.add("Racks");
        firstNames.add("Baxter");
        firstNames.add("Leroy");
        firstNames.add("Ned");
        firstNames.add("Jenkins");
        firstNames.add("DeAndre");
        firstNames.add("Cedric");
        firstNames.add("Austin");
        firstNames.add("George");
        firstNames.add("Jose");
        firstNames.add("Xongli");
        firstNames.add("Filbo");
        firstNames.add("Marcelinho");
        firstNames.add("Opalick");
        firstNames.add("Vick");
        firstNames.add("Zeron");
        firstNames.add("Zilliam");
        firstNames.add("Wallace");
        firstNames.add("Frank");
        firstNames.add("Bill");
        firstNames.add("Trill");
        firstNames.add("LaJames");
        firstNames.add("Kobe");
        firstNames.add("Samsung");
        firstNames.add("Taylor");
        firstNames.add("Rick");
        firstNames.add("Lafilbonics");
        firstNames.add("LaSpillage");
        firstNames.add("Yussuf");
        firstNames.add("Pillof");
        firstNames.add("Quentin");
        firstNames.add("Tatatouille");
        firstNames.add("Broom");
        firstNames.add("First");
        firstNames.add("Name");
        firstNames.add("The Last");
        firstNames.add("Gheorge");
        firstNames.add("Mason");
        firstNames.add("Maskoff");
        firstNames.add("Harold");
        firstNames.add("Kumar");
        firstNames.add("Poke");
        firstNames.add("Mon");
        firstNames.add("Yugi");
        firstNames.add("Oh");
        firstNames.add("Facebo");
        firstNames.add("Ok");
        firstNames.add("Oakland");
        firstNames.add("Oklou");
        firstNames.add("Fred");
        firstNames.add("Sebastian");
        firstNames.add("Lange");
        firstNames.add("Bryce");
        firstNames.add("Hopper");
        firstNames.add("Filibuster");
        firstNames.add("Phineas");
        firstNames.add("Ferb");
        firstNames.add("Narco");
        firstNames.add("Xerox");
        firstNames.add("Cryptofish");
        firstNames.add("Erren");
        firstNames.add("Elefa");
        firstNames.add("Jhonny");
        firstNames.add("Onthespo");
        firstNames.add("Tart");
        firstNames.add("Desmond");
        firstNames.add("Jaren");
        firstNames.add("Giannis");
        firstNames.add("Justin");
        firstNames.add("Ocean");
        firstNames.add("Caleb");
        firstNames.add("Toby");
        firstNames.add("Edward");
        firstNames.add("Eddie");
        firstNames.add("Juice");
        firstNames.add("LaBroom");
        firstNames.add("LaBroomSpillage");
        firstNames.add("LaBaxter");
        firstNames.add("Phil");
        firstNames.add("Phuckoff");
    }

    public static void initLastNames() {
        lastNames.add("Williams");
        lastNames.add("Madoff");
        lastNames.add("Hunter");
        lastNames.add("Thomas");
        lastNames.add("Fructose");
        lastNames.add("Davis");
        lastNames.add("Michael");
        lastNames.add("Pulk");
        lastNames.add("Pulonowski");
        lastNames.add("Luther");
        lastNames.add("Henry");
        lastNames.add("Wallace");
        lastNames.add("Zippo");
        lastNames.add("Jackson");
        lastNames.add("Pope");
        lastNames.add("Fork");
        lastNames.add("Utoff");
        lastNames.add("Baxter");
        lastNames.add("Wendover");
        lastNames.add("Allen");
        lastNames.add("Jenkins");
        lastNames.add("James");
        lastNames.add("Tonie");
        lastNames.add("Banko");
        lastNames.add("Bridges");
        lastNames.add("Riviera");
        lastNames.add("Su");
        lastNames.add("Dongbo");
        lastNames.add("");
        lastNames.add("Socute");
        lastNames.add("Sisters");
        lastNames.add("Brothers");
        lastNames.add("Zillo");
        lastNames.add("Ranks");
        lastNames.add("Banks");
        lastNames.add("Callums");
        lastNames.add("Upton");
        lastNames.add("Yinker");
        lastNames.add("Alexander");
        lastNames.add("Feathers");
        lastNames.add("Taylor");
        lastNames.add("Wafer");
        lastNames.add("Sims");
        lastNames.add("Constance");
        lastNames.add("Exeter");
        lastNames.add("Outtie");
        lastNames.add("Sketcha");
        lastNames.add("Skedaddle");
        lastNames.add("Errock");
        lastNames.add("Projzneski");
        lastNames.add("Name");
        lastNames.add("Forgone");
        lastNames.add("Gheorge");
        lastNames.add("Mason");
        lastNames.add("Orgasmo");
        lastNames.add("Nicks");
        lastNames.add("Kumar");
        lastNames.add("Pokusevski");
        lastNames.add("Mondo");
        lastNames.add("Kajisaki");
        lastNames.add("Oh");
        lastNames.add("Lemons");
        lastNames.add("Apples");
        lastNames.add("California");
        lastNames.add("Cardiac");
        lastNames.add("Peppers");
        lastNames.add("Eggplants");
        lastNames.add("Towers");
        lastNames.add("Knight");
        lastNames.add("Ologist");
        lastNames.add("Vibrations");
        lastNames.add("Fedrickson");
        lastNames.add("Cameron");
        lastNames.add("Narcos");
        lastNames.add("Cuddlefish");
        lastNames.add("Beckers");
        lastNames.add("Beuckers");
        lastNames.add("Elefante");
        lastNames.add("Pringalo");
        lastNames.add("Reuters");
        lastNames.add("Tiffany");
        lastNames.add("Desmond");
        lastNames.add("Krillian");
        lastNames.add("Krillops");
        lastNames.add("Killbofofem");
        lastNames.add("Kylmusef");
        lastNames.add("Juerkoff");
        lastNames.add("Sun");
        lastNames.add("On-Jung");
        lastNames.add("Jong-Un");
        lastNames.add("Kim");
        lastNames.add("KaBronJames");
        lastNames.add("Cyprian");
        lastNames.add("Egyptine");
        lastNames.add("Ford");
        lastNames.add("Sharkdoodoo");
        lastNames.add("Socrates");
        lastNames.add("Papadoupoulos");
        lastNames.add("Inspector");
    }
}
