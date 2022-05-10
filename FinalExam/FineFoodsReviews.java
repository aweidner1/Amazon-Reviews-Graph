    package FinalExam;

    import java.util.*;
    import java.nio.charset.*;
    import java.io.*;
    
    public class FineFoodsReviews
    {
        
        String fileName="newfinefoods.txt";
        GraphType<String> foodReviews;

        HashSet<String> productID;
        HashSet<String> userID;
        HashSet<String> reviewRating;
        
        int counter;
        
        public FineFoodsReviews() {
            foodReviews= new GraphType<>();
            // you may need to create/initialize the extra data structures you declared above
            productID = new HashSet<>();
            userID = new HashSet<>();
            reviewRating = new HashSet<>();
            counter=0;
            
            
        }
        
        public int numReviews() {
            return counter;
            
            
        }
        
        public int numProducts() {
           return productID.size();
            
        }
        
        public int numUsers() {
            return userID.size();
          
            
        }
        
        public void setupFoodGraph() {
            Scanner fileScanner;
             String line="";
                   try
                   {
                       fileScanner = new Scanner (new File (fileName),"UTF-8");
                       //fileScanner = new Scanner (new File (fileName));
                       //BufferedReader inStream = new BufferedReader(new FileReader(fileName,StandardCharsets.UTF_8));
                       //String pr="";
                       String id = "";
                       String user = "";
                       while (fileScanner.hasNextLine()) {
                        line= fileScanner.nextLine();
                                
                                if (line.trim().isEmpty()){
                                    continue;
                                }
                                
                                String[] information = line.split(":");
                                if (information.length != 2){
                                    continue;
                                }
                                String type = information[0].trim();
                                String info = information[1].trim();
                                
                                
                                switch(type){
                                    case "product/productId":
                                        if (!productID.contains(info)){
                                            productID.add(info);
                                            foodReviews.addVertex(info);
                                        }
                                        id = info;
                                        counter++; //counter up
                                        break;
                                    case "review/userId":
                                        if (!userID.contains(info)){
                                            userID.add(info);
                                            foodReviews.addVertex(info);
                                        }
                                        foodReviews.addEdge(id, info);
                                        user = info;
                                        break;
                                    case "review/score":
                                        if (!reviewRating.contains(info)){
                                           reviewRating.add(info);
                                           foodReviews.addVertex(info);
                                        }
                                        foodReviews.addEdge(user,info);
                                        foodReviews.addEdge(id, info);
                                        break;     
                                        //adding edges between userID, productID, and review/rating all in same review
                            }
                        //}
                    }
                
                }
            
               catch (IOException e)
               {
                   System.out.println(e);
               }
    }
    
    public int minProdReviews(int k) {
        int counter = 0;
       for (String id: productID){
           if (foodReviews.getAdjacency(id).size() >= k*2){
               counter++;
            }
        }
       return counter;
        
    }
    
    public HashSet<String> coReviewedProducts(String prod) {
        HashSet<String> uniqueProd = new HashSet<>();
        for (String neighbor: foodReviews.getAdjacency(prod)){
            if (userID.contains(neighbor)){
                for (String nextNeighbor: foodReviews.getAdjacency(neighbor)){
                    if (productID.contains(nextNeighbor)){
                        uniqueProd.add(nextNeighbor);
                    }
                }
            }
        }
        return uniqueProd;
    }
    
    public HashMap<String,Double> prodAvgScore() {
        HashMap<String, Double> idReviewMapping = new HashMap<>();
        int sum = 0;
        int numberReviews = 0;
        double avg;
        for (String product: productID){
            for (String reviewOrUser: foodReviews.getAdjacency(product)){
                if (reviewRating.contains(reviewOrUser)){
                    sum += Double.parseDouble(reviewOrUser);
                }
            }
            numberReviews = foodReviews.getAdjacency(product).size() / 2;
            avg = sum / numberReviews;
            idReviewMapping.put(product, avg);
            sum = 0; //resets back to zero after product 
            numberReviews = 0;
        }
        return idReviewMapping;
    }
    
    public int maxMultipleReviews(String userId) {
        HashMap<String, Integer> numReviews = new HashMap<>();
        int temp = 0;
        for (String neighbor: foodReviews.getAdjacency(userId)){
            if (productID.contains(neighbor)){
                if (!numReviews.containsKey(neighbor)){
                    numReviews.put(neighbor, 1);
                }
                if (numReviews.containsKey(neighbor)){
                    int counter = numReviews.get(neighbor);
                    numReviews.put(neighbor, counter++);
                }
        }
    }
     for (int value: numReviews.values()){
            if (value >= temp){
                temp = value;
            }
        }
        return temp;
}
    
    public static void main(String[] args) {
        
        FineFoodsReviews ffr = new FineFoodsReviews();
        ffr.setupFoodGraph();
        System.out.println(ffr.numReviews());
        System.out.println(ffr.numProducts());
        System.out.println(ffr.numUsers());
        System.out.println(ffr.minProdReviews(60));
        
        System.out.println(ffr.coReviewedProducts("B0009XLVG0"));
        System.out.println("prodAvgScore method: didn't print out because HashMap is very long, however it works when printed (method print commented out in main).");
        //System.out.println(ffr.prodAvgScore());
        System.out.print("maxMultipleReviews method outcome for specific userID: ");
        System.out.println(ffr.maxMultipleReviews("A1CZX3CP8IKQIJ"));
        //B0009XLVG0
        
    }
    /*question 12:
     * Considering fake reviews are put out with minimal effort and time, maybe we could consider the time stamps in which 
     * reviews are put out? If a review is put out seconds possibly even minutes within another, we could consider this
     * to possibly be a fake review with further inspection.
     * Implmenting a verification system to a userID could be a way of detecting fake news, as this now requires more effort
     * in order to make comments and reviews on a topic. Automated reviews could be cut down due to the needs of verification.
     * Lastly, the consideration of user-names could be important, but as the last of the ideas I have considered here, as 
     * it is difficult to tell by just a user-name alone. However if these other traits seem suspicious, the consideration 
     * of a user-name could be incredibly important.
     
     */
    
}