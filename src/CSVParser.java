import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class CSVParser {
    private static final int PAGE_SIZE = 10;
    private final CSVFile csvFile;

    public CSVParser(String filePath, String splitBy) throws FileNotFoundException {
        this.csvFile = new CSVFile(filePath, splitBy);
    }

    public void parse() throws IOException {
        csvFile.read();
        String[] headers = csvFile.getHeaders();
        Map<Integer, String[]> data = csvFile.getData();
        int page = 0;
        while (page * PAGE_SIZE < data.size()) {
            if (headers != null) {
                System.out.println(ANSI_PURPLE_BG + ANSI_BLACK + String.join(",", headers) + ANSI_RESET + "\n");
            }
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
            }
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