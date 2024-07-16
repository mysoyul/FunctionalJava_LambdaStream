package lambdasinaction.dateapi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.*;

public class MartClosedDay {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println("현재날짜 = " + today);

        //today = LocalDate.of(2024,6,24);

        //default Temporal with(TemporalAdjuster adjuster)
        //TemporalAdjuster 함수형인터페이스의 추상메서드 Temporal adjustInto(Temporal temporal)
        LocalDate closedDay = today.with(temporal -> {
            //1. 기준이 되는 날짜 구하기
            LocalDate theDay = LocalDate.from(temporal);
            //2. 두번째,네번째 일요일의 날짜 구하기
            LocalDate secondDay = theDay.with(dayOfWeekInMonth(2, DayOfWeek.SUNDAY));
            LocalDate fourthDay = theDay.with(dayOfWeekInMonth(4, DayOfWeek.SUNDAY));
            //3. 기준날짜와 비교하기
            if(theDay.isBefore(secondDay)){
                return secondDay;
            }else if(theDay.isBefore(fourthDay)){
                return fourthDay;
            }else {
                return theDay.plusMonths(1).with(dayOfWeekInMonth(2, DayOfWeek.SUNDAY));
            }
        });

        System.out.println("휴무일 = " + closedDay);
    }
}