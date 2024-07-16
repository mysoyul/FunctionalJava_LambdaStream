package lambdasinaction._02stream.collect;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static lambdasinaction._02stream.collect.Dish.menu;

public class _04GroupingDishes {

    enum CaloricLevel { DIET, NORMAL, FAT }

    public static void main(String ... args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOptionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }

    //1. type별 그룹핑
    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream()
                .collect(groupingBy(DishFunctions.getDishTypeFunction()));
    }

    //2. 칼로리별 그룹핑
    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream()
                .collect(groupingBy(DishFunctions.getCaloricLevelFunction()));
    }

    //3. type별로 그룹핑 후에 다시 칼로리별로 그룹핑
    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        return menu.stream()
                .collect(groupingBy(
                        DishFunctions.getDishTypeFunction(),
                            groupingBy(DishFunctions.getCaloricLevelFunction())
                        ));
    }
    //4. type별 갯수 카운팅
    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream()
                .collect(groupingBy(DishFunctions.getDishTypeFunction(), counting()));
    }
    //5. type별 그룹에서 가장 칼로리가 높은 Dish 찾기
    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        return menu.stream()
                .collect(groupingBy(
                        DishFunctions.getDishTypeFunction(),
                            maxBy(DishFunctions.getDishComparator())
                        ));
    }
    //5.1 type별 그룹에서 가장 칼로리가 높은 Dish 찾기 - collectingAndThen() 사용
    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
        return menu.stream()
//                .collect(groupingBy(
//                        getDishTypeFunction(),
//                        collectingAndThen(maxBy(getDishComparator()), Optional::get)
//                        ));
    /*
        Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper,
                                    BinaryOperator<U> mergeFunction)
     */
                .collect(toMap(DishFunctions.getDishTypeFunction(),
                               Function.identity(),
                               BinaryOperator.maxBy(DishFunctions.getDishComparator())
                              ));
    }

    //6. type별로 그룹핑하여 칼로리의 합계 내기
    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        return menu.stream()
                .collect(groupingBy(
                        DishFunctions.getDishTypeFunction(),
                            summingInt(Dish::getCalories)
                        ));
    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
                groupingBy(DishFunctions.getDishTypeFunction(),
                           mapping(
                                   DishFunctions.getCaloricLevelFunction(),
                                   toSet()
                                  )
                          )
                );
    }
}
