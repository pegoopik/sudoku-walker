package com.pegoopik.sudoku.web.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class ExternalSolver {

    private static final ObjectMapper mapper = new ObjectMapper();

    private ExternalSolver() {}

    @SneakyThrows
    public static String evaluate(String task) {
        StringBuilder responseString = new StringBuilder();
        Document post = Jsoup.connect("https://sudoku-solver.in.ua/index.php?option=com_sudoku&task=getAjax&action=solver")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .data("uslovie", task.trim().replaceAll("\n", ""))
                .post();
        // Грубовато, но для конкретного сайта с его делишками приходится подстраиваться
        String resultNode = post.childNode(0).childNode(1).childNode(0).toString();
        String resultString = mapper.readTree(resultNode).get("reshenie").toString().replaceAll("\"", "");
        for (int i = 0; i < 9; i++) {
            responseString.append("\n");
            responseString.append(resultString.substring(9 * i, 9 * i + 9));
        }
        return responseString.toString().trim();
    }

}
