import java.time.LocalDate;
import java.util.*;

class Klient implements Cloneable, Comparable<Klient>{

    private String nazwa;
    private LocalDate dataZakupy;
    private double rachunek;

    public Klient(String nazwa, LocalDate dataZakupy, double rachunek) {
        this.nazwa = nazwa;
        this.dataZakupy = dataZakupy;
        this.rachunek = rachunek;
    }

    public String getNazwa() {
        return nazwa;
    }

    public LocalDate getDataZakupy() {
        return dataZakupy;
    }

    public double getRachunek() {
        return rachunek;
    }

    @Override
    public int compareTo(Klient o) {
        var comparator = Comparator.comparing(Klient::getDataZakupy)
                .thenComparing(Klient::getNazwa)
                .thenComparing(Klient::getRachunek);

        return comparator.compare(this, o);
    }

    @Override
    public String toString() {
        return "Klient{" +
                "nazwa='" + nazwa + '\'' +
                ", dataZakupy=" + dataZakupy +
                ", rachunek=" + rachunek +
                '}';
    }
}

class ObslugaKlienta {
    private static double procentRabatu;
    private ArrayList<Klient> klienci;

    public ObslugaKlienta(ArrayList<Klient> klienci) {
        this.klienci = klienci;
    }

    public void show() {
        klienci.forEach(klient -> System.out.println(klient));
    }

    public void showAndSort() {
        show();

        // Data, nazwa, rachunek
        klienci.sort(Klient::compareTo);
        System.out.println("Po sortowaniu");

        show();
    }

    public static double discountAmount(Klient k) {
        var rachunek = k.getRachunek();
        if(rachunek <= 300) return 0;
        return rachunek * procentRabatu;
    }

    public static void setProcentRabatu(double procentRabatu) {
        ObslugaKlienta.procentRabatu = procentRabatu;
    }

    public HashMap<Double, String> DiscountMap(ArrayList<Klient> klist){
        var dict = new HashMap<Double, String>();
         klist.stream()
            .map(x -> Map.entry(ObslugaKlienta.discountAmount(x), x.getNazwa()))
            .filter(x -> x.getKey() != 0)
            .forEach(x -> dict.put(x.getKey(), x.getValue()));
         return dict;
    }
}


public class Main {
    public static void main(String[] args)
    {
        // TODO: Sprawdzic czy sie przypadki zgadzaja
        var klienci = new ArrayList<Klient>();
        klienci.add(new Klient("klient1", LocalDate.now(), 2137));
        klienci.add(new Klient("klient2", LocalDate.now(), 2137));
        klienci.add(new Klient("klient3", LocalDate.now().minusDays(1), 2138));
        klienci.add(new Klient("klient3", LocalDate.now().minusDays(2), 2139));

        ObslugaKlienta.setProcentRabatu(.05);

        var obsluga = new ObslugaKlienta(klienci);
        obsluga.showAndSort();

        var discountThatShouldBe0 = ObslugaKlienta.discountAmount(new Klient("any", LocalDate.now(), 200));
        System.out.println("Discount1 = " + discountThatShouldBe0);

        var discountThatShouldNotBe0 = ObslugaKlienta.discountAmount(new Klient("any", LocalDate.now(), 2137));
        System.out.println("Discount2 = " + discountThatShouldNotBe0);


        klienci.add(new Klient("klient3", LocalDate.now().minusDays(2), 100));
        klienci.add(new Klient("klient3", LocalDate.now().minusDays(2), 200));

        var map = obsluga.DiscountMap(klienci);
        map.entrySet().forEach(x -> System.out.println("[" + x.getKey() + ", " + x.getValue() + "]"));
    }
}
