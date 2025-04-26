
public class MySecondDataStructure {
    /*
     * You may add any fields that you wish to add.
     * Remember that the use of built-in Java classes is not allowed,
     * the only variables types you can use are:
     * 	-	the given classes in the assignment
     * 	-	basic arrays
     * 	-	primitive variables
     */

    private final int numOfQualities = 6;
    private int[] totalRaiseEachQuality;
    private int[] amountOfEachQuality;
    private Product[] mostExpensiveOfEachQuality;
    private int size;
    private int sumOfQuality; //for averge
    private MyArray<Product> main;

    /***
     * This function is the Init function.
     * @param N The maximum number of elements in the data structure at each time.
     */
    public MySecondDataStructure(int N) {
        totalRaiseEachQuality = new int[numOfQualities];
        amountOfEachQuality = new int[numOfQualities];
        mostExpensiveOfEachQuality = new Product[numOfQualities];
        size = 0;
        sumOfQuality = 0;
        main = new MyArray<Product>(N);

        for (int i = 0; i < numOfQualities; i++) {
            totalRaiseEachQuality[i] = 0;
            amountOfEachQuality[i] = 0;
            mostExpensiveOfEachQuality[i] = null;
        }
    }

    public void insert(Product product) {

        // Setting the price raised so far
        int prodQuality = product.quality();
        int amountRaisedSoFar = totalRaiseEachQuality[prodQuality];
        product.setPricePrevRaise(amountRaisedSoFar);

        // Adding the product to the array
        Element<Product> elemToAdd = new Element<>(product.id(), product);
        main.insert(new ArrayElement<>(elemToAdd));//insert

        // Setting most expensive
        if (mostExpensiveOfEachQuality[prodQuality] == null) {
            mostExpensiveOfEachQuality[prodQuality] = product;
        }
        // If it is not the first from this quality
        else {
            Product mostExpensive = mostExpensiveOfEachQuality[prodQuality];
            int price = mostExpensive.price() + (totalRaiseEachQuality[prodQuality] - mostExpensive.getPricePrevRaise());
            if (product.price() > price)
                mostExpensiveOfEachQuality[prodQuality] = product;
        }

        // Update size sum and amount from quality
        amountOfEachQuality[prodQuality]++;
        size++;
        sumOfQuality += prodQuality;
    }

    private Product findMaximum(int quality) {
        int maxPrice = -1;
        Product maxPriceProduct = null;
        for (int i = 0; i < main.size(); i++) {
            Product currentProduct = main.get(i).satelliteData();
            if (currentProduct.quality() == quality) {
                int currentPrice = currentProduct.price() + totalRaiseEachQuality[currentProduct.quality()] - currentProduct.getPricePrevRaise();
                if (currentPrice > maxPrice) {
                    maxPrice = currentPrice;
                    maxPriceProduct = currentProduct;
                }
            }
        }

        return maxPriceProduct;
    }

    public void findAndRemove(int id) {
        ArrayElement<Product> delete = main.search(id);

        if (delete != null) {
            main.delete(delete);
            size--;
            sumOfQuality = sumOfQuality - delete.satelliteData().quality();
            amountOfEachQuality[delete.satelliteData().quality()]--;

            if (delete.satelliteData().equals(mostExpensiveOfEachQuality[delete.satelliteData().quality()])) {
                if (amountOfEachQuality[delete.satelliteData().quality()] == 0)
                    mostExpensiveOfEachQuality[delete.satelliteData().quality()] = null;
                else
                    mostExpensiveOfEachQuality[delete.satelliteData().quality()] = findMaximum(delete.satelliteData().quality());
            }
        }
    }

    public int medianQuality() {
        if (size == 0)
            return -1;

        int median = size / 2;
        int i = 0;

        // Rounding up the middle
        if (size % 2 != 0)
            median++;

        while (median > 0) {
            median = median - amountOfEachQuality[i];
            i++;
        }
        return i - 1;
    }

    public double avgQuality() {
        if (size == 0)
            return -1;
        return (double) sumOfQuality / size;
    }

    public void raisePrice(int raise, int quality) {
        totalRaiseEachQuality[quality] += raise;
    }

    public Product mostExpensive() {
        if (size == 0)
            return null;

        int maxPrice = -1;
        Product maxProduct = null;

        for (int i = 0; i < numOfQualities; i++) {
            if (mostExpensiveOfEachQuality[i] != null) {
                Product temp = mostExpensiveOfEachQuality[i];
                int price = temp.price() + (totalRaiseEachQuality[i] - temp.getPricePrevRaise());
                if (maxPrice < price) {
                    maxPrice = price;
                    maxProduct = temp;
                }
            }
        }
        return maxProduct;
    }

}
