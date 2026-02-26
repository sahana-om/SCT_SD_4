import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;

public class WebScraper {
    private static final String TARGET_URL = "https://books.toscrape.com/";
    public static void main(String[] args) {
        try {
            System.out.println("Java Web Scraper (JSoup)\n");
            System.out.println("Connecting to: " + TARGET_URL);

            Document doc = Jsoup.connect(TARGET_URL).get();

            System.out.println("Connection successful.\n");
            System.out.println("Fetching product details...\n");
            Elements books = doc.select(".product_pod");
            FileWriter writer = new FileWriter("books.csv");
            writer.append("Title,Price,Rating\n");
            for (Element book : books) {
                String title = book.select("h3 a").attr("title");
                String price = book.select(".price_color").text();
                String rating = book.select(".star-rating")
                        .attr("class")
                        .replace("star-rating ", "");
                writer.append(title.replace(",", "") + "," + price + "," + rating + "\n");
            }
            writer.flush();
            writer.close();
            System.out.println("Scraping completed successfully.");
            System.out.println("Data has been saved to 'books.csv'.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while scraping.");
            System.out.println("Details: " + e.getMessage());
        }
    }
}