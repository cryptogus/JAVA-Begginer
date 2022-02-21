/**
 * 환영 메시지를 출력하고, 주석을 이용하여 API 문서를 생성하는 예제 프로그램
 * @author 이현호
 */

public class Hello2 {
    /**
     * 프로그램 시작 포인트
     * @param args 이 파라미터는 현재는 사용되고 있지 않음
     */
    public static void main(String[] args){
        int year = 2020;

        printGreeting(year);
    }

    /**
     * 연도를 입력 받아 해당 연도 Java Class로의 환영 메시지 출력
     * @param year 연도
     */
    public static void printGreeting(int year){
        System.out.print("Welcome to ");
        System.out.print(year);
        System.out.print(" Java Class");
    }
}
