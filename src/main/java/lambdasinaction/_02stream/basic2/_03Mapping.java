package lambdasinaction._02stream.basic2;

import lambdasinaction._02stream.basic1.*;

import java.util.*;
import static java.util.stream.Collectors.toList;
import static lambdasinaction._02stream.basic1.Dish.menu;

public class _03Mapping {

    public static void main(String...args){

        //1. map - Dish의 name 목록만 추출한 List<String>의 forEach() 사용
        List<String> nameList = menu.stream()  //Stream<Dish>
                .map(Dish::getName) //Stream<String>
                .toList();
        nameList.forEach(System.out::println);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                                         .map(String::length)
                                         .toList();
        System.out.println(wordLengths);

        //<R> Stream<R> map(Function<? super T,? extends R> mapper)
        //2. map - 중복된 문자 제거한 word 리스트
        words.stream()
                .map(word -> word.split("")) //Stream<String[]>
                .distinct()
                .forEach(System.out::println);

        //<R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
        //3.flatMap - 중복된 문자 제거가 word 리스트
        System.out.println("map() 과 flatMap() 호출");
        words.stream()
                .map(word -> word.split(""))
                //.flatMap(strArr -> Arrays.stream(strArr))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);

        System.out.println("flatMap() 만 호출");
        words.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .distinct()
                .forEach(System.out::println);

        // flatMap
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs =
                        numbers1.stream()
                                .flatMap((Integer i) -> numbers2.stream()
                                                       .map((Integer j) -> new int[]{i, j})
                                 )
                                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                                .toList();
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
    }
}
