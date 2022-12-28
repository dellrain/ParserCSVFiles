import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CSVParser {

    private static final int PAGE_SIZE = 1000;  // количество строк на каждой странице

    public static void main(String[] args) {
        String csvFile = "src/resources/csvfile2.csv";  // путь к файлу
        String line = "";
        String cvsSplitBy = ",";  // символ, используемый для разделения

        // мапа для хранения данных с номером строки в качестве ключа
        Map<Integer, String[]> data = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            boolean hasHeaders = false;  // флаг для отслеживания наличия заголовков в файле
            String[] headers = null;  // массив для хранения заголовков

            // читаем файл построчно
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);  // разбить строку на массив значений

                // если файл имеет заголовки, и мы их ещё не сохранили, то сохраняем их и пропускаем текущую строку
                if (!hasHeaders) {
                    headers = values;
                    hasHeaders = true;
                    continue;
                }

                // добавить данные в мапу
                data.put(data.size() + 1, values);
            }

            // вывод данных, пролистывая блоками строк PAGE_SIZE
            int page = 0;
            while (page * PAGE_SIZE < data.size()) {
                // печатать заголовки, если они имеются
                if (headers != null) {
                    System.out.println(ANSI_PURPLE_BG + ANSI_BLACK + String.join(",", headers) + ANSI_RESET + "\n");
                }

                // вывести данные для текущей страницы
                for (int i = page * PAGE_SIZE; i < (page + 1) * PAGE_SIZE && i < data.size(); i++) {
                    System.out.println(String.join(", ", data.get(i + 1)));
                }

                System.out.print("Введите 'n' для перехода на следующую страницу, 'p' для перехода на прошлую, 'q' чтобы выйти: \n");
                String input = getUserInput();
                if (input.equals("n")) {
                    page++;
                } else if (input.equals("p") && page != 0) {
                    page--;
                } else if (input.equals("q")) {
                    System.exit(0);
                } else {
                    System.out.println("Вы ввели неверный символ");
                    continue;
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    private static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static final String ANSI_PURPLE_BG = "\u001B[45m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RESET = "\u001B[0m";

}