package lambdasinaction._02stream.basic1;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class StreamBasic {

    public static void main(String...args){
        // Java 7
        getLowCaloricDishesNamesInJava7(Dish.menu).forEach(System.out::println);

        System.out.println("---");

        // Java 8
        getLowCaloricDishesNamesInJava16(Dish.menu).forEach(System.out::println);


        System.out.println(getGroupingMenu(Dish.menu));
        System.out.println(getMaxCaloryDish(Dish.menu));
        System.out.println(getMaxCaloryDishIntStream(Dish.menu));
    }

    public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes){
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish d: dishes){
            if(d.getCalories() <= 400){
                lowCaloricDishes.add(d);
            }
        }
        List<String> lowCaloricDishesName = new ArrayList<>();
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish d1, Dish d2){
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });
        for(Dish d: lowCaloricDishes){
            lowCaloricDishesName.add(d.getName());
        }
        List<String> lowCaloricLimit3DishesName = new ArrayList<>();
        lowCaloricLimit3DishesName = lowCaloricDishesName.subList(0,3);

        return lowCaloricLimit3DishesName;
    }

    //Java 8
    public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes){
        //중간연산 - filter(), sorted(), map()
        //최종연산 - collect()
        return dishes.stream()  //Stream<Dish>
                //filter(Predicate) Predicate의 추상메서드 T -> boolean
                .filter(dish -> dish.getCalories() <= 400)  //Stream<Dish>
                //sorted(Comparator) Comparator의 comparing(Function) Function T -> R
                .sorted(Comparator.comparing(dish -> dish.getCalories()))
                //map(Function) Function T -> R
                .map(dish -> dish.getName()) //Stream<String>
                //collect(Collector) Collector를 반환하는 메서드 Collectors.toList()
                .collect(Collectors.toList())
                .subList(0,3);  //List<String>
    }

    public static List<String> getLowCaloricDishesNamesInJava16(List<Dish> dishes){
        return dishes.stream()  //Stream<Dish>
                .filter(dish -> dish.getCalories() <= 400)  //Stream<Dish>
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName) //Stream<String>
                .toList()
                .subList(0,3);  //List<String>
    }


    //400칼로리 이하인 메뉴를 다이어트로, 아닐 경우 일반으로 그룹핑해라.
    public static Map<String, List<Dish>>  getGroupingMenu(List<Dish> dishes){
        return dishes.stream()
                //collect(Collector) Collector.groupingBy(Function<Dish,String>)
                .collect(groupingBy(dish -> {
                    if(dish.getCalories() <= 400) return "Diet";
                    else return "Normal";
                })
                );
    }


    //가장 칼로리가 높은 메뉴를 찾아라
    public static Dish getMaxCaloryDish (List<Dish> dishes) {
        Optional<Dish> optionalDish = dishes.stream()
                .max(comparingInt(Dish::getCalories));
//        Dish dish = null;
//        if(optionalDish.isPresent()){
//            dish = optionalDish.get();
//        }
//        return dish;
//        return optionalDish.orElse(new Dish());
//        return optionalDish.orElseGet(() -> new Dish());
        return optionalDish.orElseGet(Dish::new);
    }

    public static Integer getMaxCaloryDishIntStream(List<Dish> dishes) {
        return dishes.stream() //Stream<Dish>
                //mapToInt(ToIntFunction) ToIntFunction 의 추상메서드 int applyAsInt(T value)
                .mapToInt(Dish::getCalories) //IntStream
                .max()  //OptionalInt
                .orElse(0);
                //.getAsInt();
    }

}
