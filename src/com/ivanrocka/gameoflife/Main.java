package com.ivanrocka.gameoflife;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {
    static char[][] grid_char;
    static int[][] grid_int;
    static int max_grid_size = 50;
    static int grid_size = 0;
    static boolean show_must_go_on = true;
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        int input_data = 0;
        System.out.println("Please, enter the grid size of the playing field: \n" + "A field will be created with the dimensions 'input_size X input_size', the maximum grid size: " + max_grid_size);
//                "Введите кол-во строк и нажмите Enter: \n" +
//                "Введите кол-во столбцов и нажмите Enter: \n");
        input_data = scanner.nextInt();
        if ((input_data > 0)&&(input_data < max_grid_size)) {
            grid_size = input_data;
        }
        // Создаем поле размером grid_size и заполняем его 0 - мертвыми клетками
        grid_char = new char[grid_size][grid_size];
        grid_int = new int[grid_size][grid_size];
        // начальное игровое поле
        set_Default_settings_grid_int();
        print_grid();
        System.out.println("Please, enter the indexes of 'living cells' separated by a space, for example: '4 6', and press Enter \n" +
                "After you finish entering the location of all 'live' cells, enter 'end' and press Enter");
        // Получаем от пользователя расположение живых клеток
        get_living_cells(scanner);
        // выводим стартовое игровое поле с заполненными "живыми клетками"
        System.out.println("Starting position of the playing field: \n");
        print_grid();
        System.out.println("To start the game, type the command 'start' and press Enter\n ");
        while (!scanner.nextLine().equals("start")) {
            // Как только пользователь введет start прекратим выполнять бесконечный цикл ожидания и перейдем к игре
        }
        System.out.println("During the game, enter 'n' to get a new generation or enter 'q' to exit \n");
        while(show_must_go_on) {
            String cur_command = scanner.nextLine();
            if (cur_command.equals("n")) {
                start_generation();
            } else if (cur_command.equals("q")) {
                show_must_go_on = false;
            }
        }

    }
    public static void print_grid() {
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if (grid_int[i][j] == 0) {
                    grid_char[i][j] = '-';
                } else if (grid_int[i][j] == 1) {
                    grid_char[i][j] = '*';
                }
            }
            System.out.println(grid_char[i]);
        }
    }

    public static void set_Default_settings_grid_int() {
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                grid_int[i][j] = 0;
            }
        }
    }
    public static void get_living_cells(Scanner scanner) {
//        StringBuilder input_data = new StringBuilder(" ");
        String input_data = " ";
        int count_living_cell = 0;
        int row = 0, col = 0;
        int max_count_cells = grid_size*grid_size;
        int [][] input_cells = new int[max_count_cells][2];
        while ((!input_data.equals("end"))&&(count_living_cell < max_count_cells )) {
            input_data = scanner.nextLine().toLowerCase();
            //new StringBuilder(scanner.nextLine().toLowerCase());
            if (!input_data.equals("")) {
                try {
                    row = Integer.parseInt(input_data.toString().split(" ")[0]);
                    col = Integer.parseInt(input_data.toString().split(" ")[1]);
                    if ((row <= grid_size)&&(col <= grid_size)) {
                        input_cells[count_living_cell][0] = row - 1;
                        input_cells[count_living_cell][1] = col - 1;
                        count_living_cell++;
                    } else {
                        System.out.println("The entered index goes beyond the field: maximum row index = " + grid_size);
                        System.out.println("The entered index goes beyond the field: maximum column index = " + grid_size);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    if (!input_data.equals("end")) {
                        System.out.println("Invalid format entered!");
                    }
                }
            }
        }
        // выводим стартовое игровое поле с заполненными "живыми клетками"
        print_start_grid(input_cells, count_living_cell);
    }
    public static void print_start_grid(int [][] input_cells, int count) {

        for (int i = 0; i < count; i++) {
            grid_int[input_cells[i][0]][input_cells[i][1]] = 1;
        }
    }

    public static void start_generation() {
        char up_line;
        int alive_neighborhood = 0;
        // если соседей меньше двух, то умирает
        // если соседей 2 или 3 то продолжает жить
        // если соседей больше 3, то тоже умирает
        // если у мертвой клетки 3 живых соседа, то она оживает
        for (int i = 0; i < grid_size; i++) {
            for (int j = 0; j < grid_size; j++) {
                if ((i != 0)&&(j!=0)) {
                    alive_neighborhood += grid_int[i-1][j-1];
                    }
                if (i > 0) {
                    alive_neighborhood += grid_int[i-1][j];
                    if(j < grid_size-1) {
                        alive_neighborhood += grid_int[i-1][j+1];
                    }
                }
                if (j > 0) {
                    alive_neighborhood += grid_int[i][j-1];
                    if(i < grid_size-1) {
                        alive_neighborhood += grid_int[i+1][j-1];
                    }
                }
                if (i < grid_size-1) {
                    alive_neighborhood += grid_int[i+1][j];
                }
                if (j < grid_size-1) {
                    alive_neighborhood += grid_int[i][j+1];
                    if (i < grid_size-1) {
                        alive_neighborhood += grid_int[i+1][j+1];
                    }
                }
                if (grid_int[i][j] == 0) {
                    grid_int[i][j] = (alive_neighborhood == 3) ? 1 : 0;
                }
                else {
                    grid_int[i][j] = (((alive_neighborhood == 2) || (alive_neighborhood == 3))) ? 1 : 0;
                }
                alive_neighborhood = 0;
            }
        }
        print_grid();

    }

}
