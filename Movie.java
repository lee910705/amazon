package amazon;

import java.util.*;

// Implemented by Jae Joon Lee for amazon practice problem - 2/10/17
public class Movie {
	
    private String movieName;
    private float rating;
    private List<Movie> network;
 
    // Movie object constructor
    public Movie(String movieName, float rating) {
        this.movieName = movieName;
        this.rating = rating;
        network = new ArrayList<Movie>();
    }
 
    // getter functions
    public String getName() {
        return movieName;
    }
 
    public float getRating() {
        return rating;
    }

    public List<Movie> getMovieNetwork() {
        return network;
    }
 
    // add other movies to the network(bidirectional)
    public void addMovieNetwork(Movie movie) {
        network.add(movie);
        movie.network.add(this);
    }

    // Using BFS to traverse through all linked movie nodes. Time complexity for this is O(E+V)
    public static List<Movie> getMovieRecommendations(Movie movie, int num) {
    	if (num <= 0){
    		System.out.println("Num has to be more than 1");
    		return null;
    	}
        if (movie == null){
        	return null;
        }
        
        // Array for all the Movie Nodes that we're traversing 
        // Chose LinkedList over other data structures since insertion and deletion is O(1)
        LinkedList <Movie> bfsTraverse = new LinkedList<Movie>();
        // Using HashSet to track what we've visited(without visiting the same node more than once)
        HashSet<Movie> hashset = new HashSet<Movie>();
        
        // New class to give TreeSet priority by movie rating
    	class MovieComparator implements Comparator<Movie> {
    		public int compare(Movie o1, Movie o2) {
    			if (o1.rating > o2.rating) {
    				return -1;
    			} else {
    				return 1;
    			}
    		}
    	}
    	
        // Using TreeSet as a queue of top rated movies(up to the number that we have from the param)
    	// Chose TreeSet because the time complexity of getting the last element is O(1) 
        TreeSet<Movie> topRated = new TreeSet<Movie>(new MovieComparator());
        
        bfsTraverse.addAll(movie.network);
        hashset.addAll(movie.network);
        topRated.addAll(movie.network);
        while (topRated.size() > num)
        	topRated.pollLast();
        
        // Traversing through the Movie nodes
        while (!bfsTraverse.isEmpty()) {
        	Movie movieNode = bfsTraverse.poll();
        	// Traversing through all the network in a visited Movie node
        	for (Movie connectedMovieNode : movieNode.network) {
        		if (!hashset.contains(connectedMovieNode)) { // Checks if the movie in the network is not already visited
        			if(connectedMovieNode.movieName != movie.movieName){ // Checks if the movie in the network is the starting Movie Node
        				bfsTraverse.add(connectedMovieNode);
        				hashset.add(connectedMovieNode);
        				topRated.add(connectedMovieNode);
        			}
        			// Popping the last element in a TreeSet
        	        while (topRated.size() > num)
        	        	topRated.pollLast();
        		}
        	}
        }
        
        // TreeSet to ArrayList time complexity for this is O(n)
        List<Movie> list = new ArrayList<Movie>(num);
        
        for (Movie topRatedMovie : topRated){
        	list.add(topRatedMovie);
        }
            
        return list;
    }
    
    // Testing function
    public static void printList(List<Movie> list){
    	String returnString = "";
    	for(Movie i : list){
    		returnString = returnString + " " + i.movieName;
    	}
    	System.out.println(returnString.substring(1));
    }
    
    
    // Testing code for the example given
    public static void main(String args[]) {
    	
    	// For the given example from the problem
    	Movie a = new Movie("A", (float) 1.2);
    	Movie b = new Movie("B", (float) 3.6);
    	Movie c = new Movie("C", (float) 2.4);
    	Movie d = new Movie("D", (float) 4.8);
    	
    	a.addMovieNetwork(b);
    	a.addMovieNetwork(c);
    	b.addMovieNetwork(d);
    	c.addMovieNetwork(d);
    	
    	List<Movie> test1 = Movie.getMovieRecommendations(a, 2); 
    	List<Movie> test2 = Movie.getMovieRecommendations(a, 4);
    	List<Movie> test3 = Movie.getMovieRecommendations(a, 1);
    	
    	printList(test1);
    	printList(test2);
    	printList(test3);
    	
    	// Testing with other dataset
    	Movie aa = new Movie("A", (float) 1.2);
    	Movie bb = new Movie("B", (float) 3.6);
    	Movie cc = new Movie("C", (float) 2.4);
    	Movie dd = new Movie("D", (float) 4.8);
    	Movie ee = new Movie("E", (float) 1.6);
    	Movie ff = new Movie("F", (float) 2.2);
    	Movie gg = new Movie("G", (float) 0.8);
    	Movie hh = new Movie("H", (float) 5.6);
    	Movie ii = new Movie("I", (float) 1.1);
    	Movie jj = new Movie("J", (float) 2.2);
    	
    	aa.addMovieNetwork(bb);
    	aa.addMovieNetwork(cc);
    	cc.addMovieNetwork(dd);
    	cc.addMovieNetwork(gg);
    	cc.addMovieNetwork(ee);
    	ee.addMovieNetwork(ff);
    	ff.addMovieNetwork(gg);
    	gg.addMovieNetwork(hh);
    	ii.addMovieNetwork(jj);
    	
    	List<Movie> test4 = Movie.getMovieRecommendations(aa, 2);
    	List<Movie> test5 = Movie.getMovieRecommendations(aa, 4);
    	List<Movie> test6 = Movie.getMovieRecommendations(aa, 1);
    	List<Movie> test7 = Movie.getMovieRecommendations(gg, 10);
    	List<Movie> test8 = Movie.getMovieRecommendations(ff, 3);
    	List<Movie> test9 = Movie.getMovieRecommendations(ii, 2);
    	List<Movie> test10 = Movie.getMovieRecommendations(ii, 1);
    	List<Movie> test11 = Movie.getMovieRecommendations(jj, 1);
    	List<Movie> test12 = Movie.getMovieRecommendations(cc, 0);
    	
    	printList(test4);
    	printList(test5);
    	printList(test6);
    	printList(test7);
    	printList(test8);
    	printList(test9);
    	printList(test10);
    	printList(test11);
    	System.out.println(test12);
    }
}