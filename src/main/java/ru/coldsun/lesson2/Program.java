package ru.coldsun.lesson2;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final int WIN_COUNT = 4; // Выигрышная комбинация
    private static final char DOT_HUMAN = 'X'; // Фишка игрока - человек
    private static final char DOT_AI = '0'; // Фишка игрока - компьютер
    private static final char DOT_EMPTY = '*'; // Признак пустого поля

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static char[][] field; // Двумерный массив хранит текущее состояние игрового поля

    private static int fieldSizeX; // Размерность игрового поля
    private static int fieldSizeY; // Размерность игрового поля


    public static void main(String[] args) {
        field = new char[3][];

        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (checkGameState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkGameState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация объектов игры
     */
    private static void initialize(){

        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                field[x][y] = DOT_EMPTY;
            }
        }

    }

    /**
     * Отрисовка игрового поля
     *
     *     +-1-2-3-4-5
     *     1|*|X|0|0|0|
     *     2|*|*|0|0|0|
     *     3|*|*|0|0|0|
     *     --------
     */
    private static void printField(){
        System.out.print("+");
        for (int x = 0; x < fieldSizeX * 2 + 1; x++){
            System.out.print((x % 2 == 0) ? "-" : x / 2 + 1);
        }
        System.out.println();

        for (int x = 0; x < fieldSizeX; x++){
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++){
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        for (int x = 0; x < fieldSizeX * 2 + 2; x++){
            System.out.print("-");
        }
        System.out.println();

    }

    /**
     * Обработка хода игрока (человек)
     */
    private static void humanTurn(){
        int x, y;

        do {

            while (true){
                System.out.print("Введите координату хода X (от 1 до 5): ");
                if (scanner.hasNextInt()){
                    x = scanner.nextInt() - 1;
                    scanner.nextLine();
                    break;
                }
                else {
                    System.out.println("Некорректное число, повторите попытку ввода.");
                    scanner.nextLine();
                }
            }

            while (true){
                System.out.print("Введите координату хода Y (от 1 до 5): ");
                if (scanner.hasNextInt()){
                    y = scanner.nextInt() - 1;
                    scanner.nextLine();
                    break;
                }
                else {
                    System.out.println("Некорректное число, повторите попытку ввода.");
                    scanner.nextLine();
                }
            }
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }
    /**
     * Проверка, ячейка является пустой (DOT_EMPTY)
     * @param x
     * @param y
     * @return
     */
    private static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }
    /**
     * Проверка корректности ввода
     * (координаты хода не должны превышать размерность игрового поля)
     * @param x
     * @param y
     * @return
     */
    private static boolean isCellValid(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Обработка хода компьютера
     */
    private static void aiTurn(){
        int x, y;

        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка состояния игры
     * @param c фишка игрока
     * @param s победный слоган
     * @return
     */
    private static boolean checkGameState(char c, String s){
        //if (checkWin(c)) {
        if (checkWinV2(c)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }

        return false; // Игра продолжается
    }

    /**
     * Проверка победы
     * @param c
     * @return
     */
//    private static boolean checkWin(char c){
//
//        // Проверка по трем горизонталям
//        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
//        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
//        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;
//
//        // Проверка по трем вертикалям
//        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
//        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
//        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;
//
//        // Проверка по диагоналям
//        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
//        if (field[0][2] == c && field[1][1] == c && field[2][0] == c) return true;
//
//        return false;
//    }

    private static boolean checkWinV2(char c){


        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                if (y + WIN_COUNT < fieldSizeY){
                    if (checkHorizontal(x,y,WIN_COUNT, c))return true;
                }
                if (x + WIN_COUNT < fieldSizeX){
                    if (checkVertical(x,y,WIN_COUNT, c))return true;
                }
                if ((x + WIN_COUNT < fieldSizeX) && (y + WIN_COUNT < fieldSizeY)){
                    if (checkDiagonalDown(x,y,WIN_COUNT, c))return true;
                }
                if ((x - WIN_COUNT > 0) && (y + WIN_COUNT < fieldSizeY)){
                    if (checkDiagonalUp(x,y,WIN_COUNT, c))return true;
                }

            }
        }

        return false;
    }

    /**
     * Проверка по горизонтали
     * @return
     */
    static boolean checkHorizontal (int x, int y, int win, char c){
        for (int i = y; i < y + win; i++) {
            if (field[x][i] != c) return false;
        }
        return true;
    }
    /**
     * Проверка по вертикали
     * @return
     */
    static boolean checkVertical  (int x, int y, int win, char c){
        for (int i = x; i < x + win; i++) {
            if (field[i][y] != c) return false;
        }
        return true;
    }
    /**
     * Проверка по диагонали вверх
     * @return
     */
    static boolean checkDiagonalDown (int x, int y, int win, char c){
        for (int i = y; i < y + win; i++) {
            if (field[x][i] != c) return false;
            x++;
        }
        return true;
    }
    /**
     * Проверка по диагонали вниз
     * @return
     */
    static boolean checkDiagonalUp (int x, int y, int win, char c){
        for (int i = y; i < y + win; i++) {
            if (field[x][i] != c) return false;
            x--;
        }
        return true;
    }

    /**
     * Проверка на ничью
     * @return
     */
    private static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

}
