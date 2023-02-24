import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BookMyShow {
    ArrayList<Theatre> theaters;
    static HashMap<String,ArrayList<Show>> movieMap;

    private void generateMovieMap(){
        for (Theatre theater :theaters) {
            ArrayList<Show> showArray = theater.getShows();
            for(Show show : showArray) {
                if (show != null) {
                    if (movieMap.containsKey(show.getMovie().getName())) {
                        movieMap.get(show.getMovie().getName()).add(show);
                    } else {
                        ArrayList<Show> movieShowList = new ArrayList<>();
                        movieShowList.add(show);
                        movieMap.put(show.getMovie().getName(), movieShowList);
                    }
                }
            }
        }
    }
    public BookMyShow(ArrayList<Theatre> theaters) {
        this.theaters = theaters;
        this.movieMap = new HashMap<>();
        generateMovieMap();
        System.out.println(movieMap);
    }
    public static ArrayList<Show> searchShows(String movieName){
        if (movieMap.containsKey(movieName)){
            return movieMap.get(movieName);
        }
        else
            return null;
    }

    public static void main(String[] args) {
        /* --------Data generation code ----------------- */


        GuestUser piyush = new GuestUser("Piyush");

        RegisteredUser ayush = new RegisteredUser("Ayush");

        RegisteredUser saurabh = new RegisteredUser("Saurabh");

        // Creating Movie object 1
        Movie ironMan = new Movie("Iron Man", Language.ENGLISH,Genre.ACTION);

        // Creating Movie object2
        Movie avengers = new Movie("Avengers: End Game", Language.ENGLISH,Genre.ACTION);

        // Creating Movie object3
        Movie walkToRemember = new Movie("The Walk To Remember", Language.ENGLISH,Genre.ROMANCE);

        // Creating Movie object4
        Movie housefull = new Movie("HouseFull 2", Language.HINDI,Genre.COMEDY);

        // Creating Theater1
        Theatre pvr_gip = new Theatre("PVR","GIP Noida",30);

        // Creating Another Theater2
        Theatre big_cinema = new Theatre("Big Cinema","Sector 137 Noida",40);




        // Creating  shows for movies
        Show show1=null, show2=null, show3=null, show4=null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");

        try {
            // Creating Show for Movie
            String dateInString = "Friday, Mar 4, 2020 09:00:00 AM";
            Date date = formatter.parse(dateInString);
            show1 = new Show(date,ironMan,pvr_gip);

            // Creating Show for Movie 2
            dateInString = "Friday, Mar 4, 2023 12:00:00 PM";
            date = formatter.parse(dateInString);
            show2 = new Show(date,housefull,pvr_gip);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        /* --------Data generation code ---- END ----------------- */

        // adding theaters
        ArrayList<Theatre> theaterArrayList= new ArrayList<>();
        theaterArrayList.add(pvr_gip);
        theaterArrayList.add(big_cinema);
        BookMyShow bookMyShow = new BookMyShow(theaterArrayList);

        // Searching Book My Show for all the shows of a movie
        ArrayList<Show> searchedShow = BookMyShow.searchShows("The Walk To Remember");

        Show bookingShow = searchedShow.get(0);

        // Ticket Booking Thread 1
        TicketBookingThread t1 = new TicketBookingThread(bookingShow,ayush,10);

        // Ticket Booking Thread 2
        TicketBookingThread t2 = new TicketBookingThread(bookingShow,saurabh,10);

        t1.start();
        t2.start();

        // Waiting
        try {
            t1.join();
            t2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Ticket ayush_ticket = t1.getTicket();
        Ticket saurabh_ticket = t2.getTicket();

        System.out.println(t1.getTicket());
        System.out.println(t2.getTicket());

        TicketBookingThread t3 = new TicketBookingThread(bookingShow,ayush,15);

        TicketBookingThread t4 = new TicketBookingThread(bookingShow,saurabh,10);

        t3.start();
        t4.start();

        try {

            t4.join();
            t3.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Ticket ayushNewTicket = t3.getTicket();
        Ticket saurabhNewTicket = t4.getTicket();

        System.out.println(ayushNewTicket);
        System.out.println(saurabhNewTicket);

    }
}
