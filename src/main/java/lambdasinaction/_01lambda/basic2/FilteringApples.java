package lambdasinaction._01lambda.basic2;

import java.util.*;
import java.util.function.Predicate;

public class FilteringApples{

    public static void main(String ... args){

        List<Apple> inventory = Arrays.asList(new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"));

        // 람다식 사용 [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        filterApples(inventory, apple -> apple.getColor().equals("green"));

        // Method Reference 사용 [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        filterApples(inventory, apple -> isGreenApple(apple));
        filterApples(inventory, FilteringApples::isGreenApple);

        // 람다식 사용[Apple{color='green', weight=155}]
        filterApples(inventory, apple -> apple.getWeight()> 150);
        // Method Reference 사용 [Apple{color='green', weight=155}]
        filterApples(inventory, FilteringApples::isHeavyApple);

        // []
        List<Apple> weirdApples = filterApples(inventory, a -> a.getWeight() < 80 ||
                "brown".equals(a.getColor()));
        System.out.println(weirdApples);
    }


    public static boolean isGreenApple(Apple apple) {
        return apple.getColor().equals("green");
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p){
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}
