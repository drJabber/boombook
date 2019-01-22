package rnk.bb.soup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/soup")
public class Soup extends HttpServlet {
    private static String HOTELS_SERVLET="https://tophotels.ru";

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse rsp) throws ServletException, IOException {
        try{
            rsp.setContentType("application/csv");
            rsp.setCharacterEncoding("UTF-8");

            rsp.getWriter().println("href;title;title_text;geography;desc;stars;country;town;address;phone;fax;site;vk;fb");
            for (Integer i=1;i<=10;i++){
                String url=HOTELS_SERVLET+"/hotels?page="+i.toString();
                Document document= Jsoup.connect(url).get();
                Elements list= document.select("div.catalogs-item");

                for (Element element:list) {
                    Element title_href = element.selectFirst("a.catalogs-ttl-a");
                    Element title = element.selectFirst("a.catalogs-ttl-a[title]");
                    Element title_text = element.selectFirst("a.catalogs-ttl-a");
                    Element title_geography = element.selectFirst("div.catalogs-desc > a");
                    Element title_desc = element.selectFirst("div.catalogs-info");

                    Element stars7 = element.selectFirst("div > div > div > div.stars7");
                    Element stars6 = element.selectFirst("div > div > div > div.stars6");
                    Element stars5 = element.selectFirst("div > div > div > div.stars5");
                    Element stars4 = element.selectFirst("div > div > div > div.stars4");
                    Element stars3 = element.selectFirst("div > div > div > div.stars3");
                    Element stars2 = element.selectFirst("div > div > div > div.stars2");
                    Element stars1 = element.selectFirst("div > div > div > div.stars1");

                    Integer stars = 0;
                    if (stars7 != null) {
                        stars = 7;
                    } else if (stars6 != null) {
                        stars = 6;
                    } else if (stars5 != null) {
                        stars = 5;
                    } else if (stars4 != null) {
                        stars = 4;
                    } else if (stars3 != null) {
                        stars = 3;
                    } else if (stars2 != null) {
                        stars = 2;
                    } else if (stars1 != null) {
                        stars = 1;
                    }


                    List<String> result = new ArrayList<>();
                    result.add(title_href.attr("href"));
                    result.add(title.text());
                    result.add(title_text.text());
                    result.add(title_geography.text());
                    result.add(title_desc.text());
                    result.add(stars.toString());

                    extract_info(result,title_href.attr("href"));
                    extract_long_descr(result,title_href.attr("href"));

                    rsp.getWriter().println(result.stream().collect(Collectors.joining(";")));

                }
            }

        }catch(Exception ex){
            throw new ServletException(ex);
        }
    }

    private void extract_long_descr(List<String> list, String href) throws IOException{
        Document document= Jsoup.connect(HOTELS_SERVLET+href+"/description").get();
        Element container = document.selectFirst("span.ckeditor-block-wrap");
        list.add(container.html()
                .replaceAll(";","%@%")
                .replaceAll("\r","")
                .replaceAll("\n",""));
    }

    private void extract_info(List<String> list, String href) throws IOException{
        Document document= Jsoup.connect(HOTELS_SERVLET+href+"/contact").get();
        Element container = document.selectFirst("div.hoter-contact--left");
        Elements contacts=container.select("div.bth__tbl");
        Map<String, String> map=new HashMap<>();
        for (Element contact:contacts){
            for (Element row:contact.select("div.bth__row")){
                Elements items=row.select("p.bth__cnt");
                if (items.size()>1){
                    Element item=items.get(0);
                    if (item.text().equals("Страна")){
                        map.put("Country",items.get(1).text());
                    }
                    if (item.text().equals("Город")){
                        map.put("Town",items.get(1).text());
                    }
                    if (item.text().equals("Адрес")){
                        map.put("Address",items.get(1).text());
                    }
                    if (item.text().equals("Телефон")){
                        map.put("Phone",items.get(1).text());
                    }
                    if (item.text().equals("Факс")){
                        map.put("Fax",items.get(1).text());
                    }
                    if (item.text().equals("Сайт")){
                        map.put("Site",items.get(1).text());
                    }
                    if (item.text().equals("VK")){
                        map.put("vk",items.get(1).text());
                    }
                    if (item.text().equals("FaceBook")){
                        map.put("fb",items.get(1).text());
                    }
                }
            }
        }

        list.add(map.getOrDefault("Country",""));
        list.add(map.getOrDefault("Town",""));
        list.add(map.getOrDefault("Address",""));
        list.add(map.getOrDefault("Phone",""));
        list.add(map.getOrDefault("Fax",""));
        list.add(map.getOrDefault("Site",""));
        list.add(map.getOrDefault("vk",""));
        list.add(map.getOrDefault("fb",""));
    }
}