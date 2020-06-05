package edu.pucmm.practica1;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String url12 = null;
        Scanner sc;
        boolean valor;
/*        while(valor == false) {



            if (sc.hasNextLine())
                url12 = sc.nextLine(); // if there is another number
            else
                url12 = null;*/ // nothing added in the input
        //url12 = "https://www.lawebdelprogramador.com/";


        System.out.println("Hola");
        Document pagina = null;
        Element link = null;
        String relHref = null;
        String absHref = null;
        System.out.println("Ingrese un url valido");
        sc = new Scanner(System.in);
        while(true) {
            try {

                if (urlValidator(url12 = devuelve(sc))) {

                    pagina = Jsoup.connect(url12).get();
                    //http://facebook.com
                    lineas(pagina);
                    CantParrafo(pagina);
                    CantImg(pagina);
                    Imprimir_cant_form(pagina);
                    show_form_input(pagina);
                    metodo_enviado_post(pagina);
                    //System.out.println("The URL " + url12 + " es valido");
                    //link = pagina.select("a").first();
                   // relHref = link.attr("href"); // == "/"
                  //  absHref = link.attr("abs:href"); // "http://jsoup.org/"
                    //System.out.println(pagina);
                    //  System.out.println(link);
                     // System.out.println(relHref);
                    //   System.out.println(absHref);
                }else{
                    System.out.println("Url no valido");
                }

            } catch (UnknownHostException e) {
                System.out.println("The URL " + url12 + " NO es valido");
                System.out.println("Ingrese un url valido");
            } catch (MalformedURLException e){
                System.out.println("The URL " + url12 + " NO es valido");
                System.out.println("Ingrese un url valido");
            } catch (IllegalArgumentException e) {

            } catch (IOException e) {
                System.out.println("The URL " + url12 + " NO es valido");
                System.out.println("Ingrese un url valido");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }


    }
    private static void lineas(Document docu) {
        int count = (int) docu.html().lines().count();
        System.out.println("Cantidad de lineas: " + count);
    }
    private static void CantParrafo(Document docu) {
        System.out.println("Hay un total de Parrafo <p>: " + docu.getElementsByTag("p").size());
    }
    private static void CantImg(Document docu) {

        int pp = 1;
        int cuenta = 0;
        for (Element link:docu.getElementsByTag("p")) {
            int count = link.getElementsByTag("img").size();
            if(count != 0) {
                System.out.println("En EL <P>#" + pp + " Hay un total de Img:" + count);
                cuenta+=count;
            }
            pp++;

        }
        System.out.println("Hay un total de img:"+cuenta);

    }
    private static void Imprimir_cant_form(Document docu){
        int count_post = 0;
        int count_get = 0;
        for (Element link:docu.getElementsByTag("form")) {
            if (link.attr("method").equalsIgnoreCase("post")){
                count_post++;
            }else if(link.attr("method").equalsIgnoreCase("GET")){
                count_get++;
            }
        }
        System.out.println("La cantidad de form es: Post: "+count_post+" Get: "+count_get+" Total :"+(count_get+count_post));
    }


    private static void show_form_input(Document docu){
        int count = 1;
        int inp = 0;
        for (Element link:docu.getElementsByTag("form")) {
            System.out.println("Form #"+count);
            for (Element link_input :link.getElementsByTag("input")) {
                System.out.println("<input type='"+link_input.attr("type")+"'");
                inp++;
            }

            System.out.println("totaal de input: "+inp+" del Form #"+count);
            count++;
            inp = 0;
        }
    }
    public static void metodo_enviado_post(Document docu) throws URISyntaxException, IOException {
        for(Element element: docu.getElementsByTag("form")) {
            if (element.attr("method").equalsIgnoreCase("post")) {
                Connection con = ((FormElement) element).submit();

                con.header("Matricula", "2016-0435");
                con.data("Asignatura","Practica1");
                Connection.Response r = con.execute();
               System.out.println(r.statusMessage() + "\n" + r.body());
                //System.out.println(r.body());
            }
        }
    }


    private static final String URL_REGEX =
            "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))" +
                    "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                    "([).!';/?:,][[:blank:]])?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);


    public static boolean urlValidator(String url) {

        if (url == null) {
            return false;
        }

        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }
    public static String devuelve(Scanner entra){
        String link = null;
        if (entra.hasNextLine()){
            link = entra.nextLine();
        }
        return link;
    }

}
