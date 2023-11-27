import java.util.Scanner;

public class Main {
    static char[][] grid_char;
    static int[][] grid_int;
    static int max_grid_size = 50;
    static int grid_size = 0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input_data = 0;
        System.out.println("Введите размер grid_size игрового поля: \n" + "Будет создано поле с размерами grid_size X grid_size, максимальный размер: " + max_grid_size);
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
        System.out.println("Введите индексы 'живых клеток' через пробел в виде: 'номер_строки номер_столбца' и нажмите Enter \n" +
                "После окончания ввода расположения всех 'живых' клеток введите 'end'");
        // Получаем от пользователя расположение живых клеток
        get_living_cells(scanner);
        // выводим стартовое игровое поле с заполненными "живыми клетками"
        System.out.println("Стартовая позиция игрового поля: \n");
        print_grid();
        System.out.println("Чтобы начать генерацию введите команду 'start' и нажмите Enter\n");

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
                        System.out.println("Введеный индекс выходит за пределы поля: максимальный индекс строки = " + grid_size);
                        System.out.println("Введеный индекс выходит за пределы поля: максимальный индекс столбца = " + grid_size);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    if (!input_data.equals("end")) {
                        System.out.println("Введен неверный формат");
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
        // если соседей меньше двух, то умирает
        // если соседей 2 или 3 то продолжает жить
        // если соседей больше 3, то тоже умирает
        // если у мертвой клетки 3 живых соседа, то она оживает
    }

}
